package warehouse.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ReferencesDAO extends AbstractDAO {

    public int totalFreeVolume() throws SQLException {
        try (Connection conn = getConnection();) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select sum(volume)-(select sum(supply.quantity * commodities.volume) " +
                    "   from supply  " +
                    "   left join commodities on supply.commodity_id=commodities.commodities_id ) " +
                    "from lots  ");

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        }
    }

    public int totalFreeCapacity() throws SQLException {
        try (Connection conn = getConnection();) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select sum(load_capacity)-(select sum(supply.quantity * commodities.weight) " +
                    "   from supply  " +
                    "   left join commodities on supply.commodity_id=commodities.commodities_id ) " +
                    "from lots  ");

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        }
    }

    public int totalTakenVolume() throws SQLException {
        try (Connection conn = getConnection();) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select sum(supply.quantity * commodities.volume) as taken_volume " +
                    "from supply  " +
                    "left join commodities on supply.commodity_id=commodities.commodities_id  ");


            if (rs.next()) {
                return rs.getInt("taken_volume");
            }

            return 0;
        }
    }

    public int totalTakenCapacity() throws SQLException {
        try (Connection conn = getConnection();) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select sum(supply.quantity * commodities.weight) as taken_weight  " +
                    "from supply  " +
                    "left join commodities on supply.commodity_id=commodities.commodities_id  ");

            if (rs.next()) {
                return rs.getInt("taken_weight");
            }

            return 0;
        }
    }

    public int totalValue() throws SQLException {
        try (Connection conn = getConnection();) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select sum(supply.quantity * commodities.price) as total_price " +
                    "from supply  " +
                    "left join commodities on supply.commodity_id=commodities.commodities_id  ");


            if (rs.next()) {
                return rs.getInt("total_price");
            }

            return 0;
        }
    }

    public double averageTransactions (LocalDateTime from, LocalDateTime to) throws SQLException {
        try (Connection conn = getConnection();) {
            PreparedStatement st = conn.prepareStatement("select count(*) / datediff(?, ?) from movings " +
                    "where movings.date between ? and ?");
            st.setTimestamp(1, Timestamp.valueOf(to));
            st.setTimestamp(2, Timestamp.valueOf(from));
            st.setTimestamp(3, Timestamp.valueOf(from));
            st.setTimestamp(4, Timestamp.valueOf(to));
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }

            return 0;

        }
    }

    public int turnoverPrice (LocalDateTime from, LocalDateTime to) throws SQLException {
        try (Connection conn = getConnection();) {
            PreparedStatement st = conn.prepareStatement("select \n" +
                    "sum(movings.quantity * commodities.price) as turnover_price \n" +
                    "from movings\n" +
                    "join commodities on movings.commodity_id = commodities.commodities_id\n" +
                    "where movings.date between ? and ?");
            st.setTimestamp(1, Timestamp.valueOf(from));
            st.setTimestamp(2, Timestamp.valueOf(to));

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        }
    }

    public int turnoverWeight (LocalDateTime from, LocalDateTime to) throws SQLException {
        try (Connection conn = getConnection();) {
            PreparedStatement st = conn.prepareStatement("select \n" +
                    "sum(movings.quantity * commodities.weight) as turnover_weight\n" +
                    "from movings\n" +
                    "join commodities on movings.commodity_id = commodities.commodities_id\n" +
                    "where movings.date between ? and ?");
            st.setTimestamp(1, Timestamp.valueOf(from));
            st.setTimestamp(2, Timestamp.valueOf(to));

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        }
    }

    public HashMap<Integer,Integer> commoditiesInLot (int lotId) throws SQLException{
        try (Connection conn = getConnection();) {
            PreparedStatement st = conn.prepareStatement("select commodity_id, quantity from supply " +
                    "where lot_id = ? and quantity > 0");
            st.setInt(1, lotId);
            ResultSet rs = st.executeQuery();

            HashMap<Integer, Integer> commodities = new HashMap<>();

            while (rs.next()) {
                int comm = rs.getInt("commodity_id");
                int quantity = rs.getInt("quantity");

                commodities.put(comm, quantity);
            }
            return commodities;
        }
    }

    public HashMap<Integer,Integer> commodityReference (int comId) throws SQLException{
        try (Connection conn = getConnection();) {
            PreparedStatement st = conn.prepareStatement("select lot_id, quantity from supply " +
                    "where commodity_id = ? and quantity > 0");
            st.setInt(1, comId);
            ResultSet rs = st.executeQuery();

            HashMap<Integer, Integer> lots = new HashMap<>();

            while (rs.next()) {
                int lot = rs.getInt("lot_id");
                int quantity = rs.getInt("quantity");

                lots.put(lot, quantity);
            }
            return lots;
        }
    }

    public int totalCommodityQuantity (int comId) throws SQLException{
        try (Connection conn = getConnection();) {
            PreparedStatement st = conn.prepareStatement("select sum(supply.quantity) from supply\n" +
                    "where commodity_id = ?");
            st.setInt(1, comId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        }
    }
}
