<%@ page import="controller.WarehouseController" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="exceptions.*" %>
<%@ page import="java.sql.SQLException" %>
<html>
<body>
      <form action = "moveout.jsp" method = "POST">
         <select name="commodity_id">
            <option value="1">Potatoes</option>
            <option value="2">Carrots</option>
            <option value="3">Bread</option>
         </select>
         <input type="text" name="quantity"/>
         <input type = "submit" value = "Move out" />
      </form>

<%

String cid =  request.getParameter("commodity_id");
String quantity =  request.getParameter("quantity");
if(cid != null) {
    WarehouseController c = new WarehouseController();
    try {
        HashMap<Integer, Integer> moveOutRes = c.moveOut(Integer.parseInt(cid), Integer.parseInt(quantity));
        out.println("Removed:");
        for(Map.Entry<Integer, Integer> entry : moveOutRes.entrySet()) {
            Integer lotId = entry.getKey();
            Integer q = entry.getValue();
            out.println("Lot " + lotId + "-> q " + q);
        }
    } catch (CommodityNotFoundException e) {
        out.println(e.getMessage());
    } catch (NotEnoughCommoditiesException e) {
        out.println(e.getMessage());
    } catch (SQLException e) {
        out.println("Error!");
        out.println(e.getMessage());
    }
}
%>
</body>
</html>
