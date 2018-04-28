<%@ page import="controller.WarehouseController" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="exceptions.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="warehouse.dao.*" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<body>

      <form action = "references.jsp" method = "POST">
         <select name="averageTransactionsAndTurnover">
            <option value="1">Average Transactions (enter number of days)</option>
            <option value="2">Turnover by price (enter number of days)</option>
            <option value="3">Turnover by weight (enter number of days)</option>
         </select>
         <input type="text" name="days"/>
         <input type = "submit" value = "Submit" />
      </form>

            <form action = "references.jsp" method = "POST">
               <select name="lot_id">
                  <option value="1">Enter Lot Number</option>
               </select>
               <input type="text" name="lotNo"/>
               <input type = "submit" value = "Submit" />
            </form>

            <form action = "references.jsp" method = "POST">
                           <select name="commodity_id">
                              <option value="1">Enter Commodity Number (commodities by lots)</option>
                           </select>
                           <input type="text" name="coNo"/>
                           <input type = "submit" value = "Submit" />
                        </form>

      <form action = "references.jsp" method = "POST">
                       <select name="comm_id">
                              <option value="1">Enter Commodity Number (total number of commodities)</option>
                           </select>
                           <input type="text" name="commNo"/>
                           <input type = "submit" value = "Submit" />
      </form>

<%

    Integer freeVol = new ReferencesDAO().totalFreeVolume();
    out.println("Total Free Volume " + freeVol);
    Integer freeCap = new ReferencesDAO().totalFreeCapacity();
    out.println("Total Free Capacity " + freeCap);
    Integer takenVol = new ReferencesDAO().totalTakenVolume();
    out.println("Total Taken Volume " + takenVol);
    Integer takenCap = new ReferencesDAO().totalTakenCapacity();
    out.println("Total Taken Capacity " + takenCap);
    Integer val = new ReferencesDAO().totalValue();
    out.println("Total Value " + val);

    String avTr =  request.getParameter("averageTransactionsAndTurnover");
    String days =  request.getParameter("days");
    if(avTr != null && days != null) {
        if (avTr.equals("1")) {
        double avg = new ReferencesDAO().averageTransactions(LocalDateTime.now().minusDays(Integer.parseInt(days)), LocalDateTime.now());
        out.println("Average transactions since " + days + " days " + avg);
        }
        if (avTr.equals("2")) {
                Integer pr = new ReferencesDAO().turnoverPrice(LocalDateTime.now().minusDays(Integer.parseInt(days)), LocalDateTime.now());
                out.println("Turnover in price " + pr);

                }
        if (avTr.equals("3")) {
                 Integer we = new ReferencesDAO().turnoverWeight(LocalDateTime.now().minusDays(Integer.parseInt(days)), LocalDateTime.now());
                                 out.println("Turnover in weight " + we);
                }

    }



    String coId =  request.getParameter("coNo");
    if(coId != null) {
        HashMap<Integer, Integer> commodityRef = new ReferencesDAO().commodityReference(Integer.parseInt(coId));

                        if (!commodityRef.isEmpty()){
                        for(Map.Entry<Integer, Integer> entry : commodityRef.entrySet()) {
                            Integer lotId = entry.getKey();
                            Integer q = entry.getValue();
                            out.println("Commodity in lot " + lotId + " -> " + q);
                            }
                            }
                            else {
                            out.println("No such comodity in lots!");
                            }
    }

    String lotId =  request.getParameter("lotNo");
        if(lotId != null) {
            HashMap<Integer, Integer> inLot = new ReferencesDAO().commoditiesInLot(Integer.parseInt(lotId));

                            if (!inLot.isEmpty()){
                            out.println("In lot:");
                            for(Map.Entry<Integer, Integer> entry : inLot.entrySet()) {
                                Integer comId = entry.getKey();
                                Integer q = entry.getValue();
                                out.println("Lot " + comId + " -> " + q);
                                }
                                }
                                else {
                                out.println("Lot is empty or doesn't exist!");
                                }
        }


    String commNo =  request.getParameter("commNo");
    if(commNo != null) {
    Integer totalComm = new ReferencesDAO().totalCommodityQuantity(Integer.parseInt(commNo));
                out.println("Total of commodity " + totalComm);
                }

%>
</body>
</html>
