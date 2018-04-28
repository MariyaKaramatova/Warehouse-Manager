package warehouse.dao;

import model.Commodity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import exceptions.*;

public class MovingsDAO extends AbstractDAO{

    public  HashMap<Integer, Integer> moveIn (Commodity commodity, int quantity, LocalDateTime date) throws SQLException, NotEnoughSpaceException {

        try (Connection conn = getConnection();) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select lots.lots_id, lots.volume - coalesce(sum(supply.quantity * commodities.volume), 0)  as free_volume, " +
                    "lots.load_capacity - coalesce(sum(supply.quantity * commodities.weight), 0) as free_capacity " +
                    "from lots left join supply on lots.lots_id=supply.lot_id " +
                    "left join commodities on supply.commodity_id=commodities.commodities_id " +
                    "group by lots.lots_id");

            HashMap<Integer, Integer> lotsToInsert = new HashMap<>();

            int availableTotal = 0;
            while (rs.next()) {
                int freeVol = rs.getInt("free_volume");
                int freeCap = rs.getInt("free_capacity");

                int availableInLot = Math.min(freeVol / commodity.getVolume(), freeCap / commodity.getWeight());

                if (availableInLot > 0) {
                    if (availableInLot > (quantity - availableTotal)) {
                        availableInLot = quantity - availableTotal;
                    }
                    lotsToInsert.put(rs.getInt("lots_id"), availableInLot);
                    availableTotal += availableInLot;
                }

                if (availableTotal == quantity){
                    break;
                }
            }
            if (availableTotal < quantity) {
                throw new NotEnoughSpaceException("Not enough space!");
            }

            for(Map.Entry<Integer, Integer> entry : lotsToInsert.entrySet()) {
                Integer lotId = entry.getKey();
                Integer q = entry.getValue();

                PreparedStatement ps = conn.prepareStatement("select supply_id from supply where lot_id = ? and commodity_id = ?");
                ps.setInt(1, lotId);
                ps.setInt(2, commodity.getId());
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    int supId = rs2.getInt("supply_id");
                    PreparedStatement update = conn.prepareStatement("update supply set quantity = quantity + ? where supply_id = ?");
                    update.setInt(1, q);
                    update.setInt(2, supId);
                    update.executeUpdate();
                } else {
                    PreparedStatement insert = conn.prepareStatement("insert into supply(commodity_id, quantity, lot_id) values(?,?,?)");
                    insert.setInt(1, commodity.getId());
                    insert.setInt(2, q);
                    insert.setInt(3, lotId);
                    insert.executeUpdate();
                }

            }

            PreparedStatement insert = conn.prepareStatement("insert into movings(commodity_id, quantity, date, type) values(?,?,?,'move_in')");
            insert.setInt(1, commodity.getId());
            insert.setInt(2, quantity);
            insert.setTimestamp(3, Timestamp.valueOf(date));
            insert.executeUpdate();

            return lotsToInsert;
        }
    }

    public  HashMap<Integer, Integer> moveOut (Commodity commodity, int quantity, LocalDateTime date) throws SQLException, NotEnoughCommoditiesException {

        try (Connection conn = getConnection();) {
            PreparedStatement st = conn.prepareStatement("select quantity, lot_id from supply where commodity_id = ? and quantity > 0");
            st.setInt(1, commodity.getId());
            ResultSet rs = st.executeQuery();

            HashMap<Integer, Integer> lotsToMoveFrom = new HashMap<>();

            int availableTotal = 0;
            while (rs.next()) {
                int availableInLot = rs.getInt("quantity");
                int lotId = rs.getInt("lot_id");

                if (availableInLot + availableTotal > quantity) {
                    availableInLot = quantity - availableTotal;
                }

                lotsToMoveFrom.put(rs.getInt("lot_id"), availableInLot);
                availableTotal += availableInLot;

                if (availableTotal == quantity){
                    break;
                }
            }
            if (availableTotal < quantity) {
                throw new NotEnoughCommoditiesException("Not enough commodities!");
            }

            for(Map.Entry<Integer, Integer> entry : lotsToMoveFrom.entrySet()) {
                Integer lotId = entry.getKey();
                Integer q = entry.getValue();

                PreparedStatement update = conn.prepareStatement("update supply set quantity = quantity - ? where lot_id = ? and commodity_id = ?");
                update.setInt(1, q);
                update.setInt(2, lotId);
                update.setInt(3, commodity.getId());
                update.executeUpdate();

            }

            PreparedStatement insert = conn.prepareStatement("insert into movings(commodity_id, quantity, date, type) values(?,?,?,'move_out')");
            insert.setInt(1, commodity.getId());
            insert.setInt(2, quantity);
            insert.setTimestamp(3, Timestamp.valueOf(date));
            insert.executeUpdate();

            return lotsToMoveFrom;
        }
    }
}