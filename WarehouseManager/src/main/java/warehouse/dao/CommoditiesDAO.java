package warehouse.dao;

import model.Commodity;
import exceptions.CommodityNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommoditiesDAO extends AbstractDAO {

    public Commodity findById(int commodity_id) throws SQLException, CommodityNotFoundException {

        try (Connection conn = getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select commodities_id, volume, weight, price from commodities where commodities_id =?");
            ResultSet rs = setCommodityIdAndExec(ps, commodity_id);

            if (rs.next()) {
                return new Commodity(rs.getInt("commodities_id")
                        , rs.getInt("volume")
                        , rs.getInt("weight")
                        , rs.getInt("price"));
            } else {
                throw new CommodityNotFoundException("Commodity not found!");
            }
        }
    }

    private ResultSet setCommodityIdAndExec(PreparedStatement ps, int commodity_id) throws SQLException {
        ps.setInt(1, commodity_id);
        return ps.executeQuery();
    }

}
