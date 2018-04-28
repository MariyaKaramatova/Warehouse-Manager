package controller;

import exceptions.*;
import model.Commodity;
import model.Lot;
import warehouse.dao.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class WarehouseController {

    public HashMap<Integer, Integer> moveIn(int commodity_id, int quantity) throws SQLException, CommodityNotFoundException, NotEnoughSpaceException {
        Commodity commodity = new CommoditiesDAO().findById(commodity_id);

        return new MovingsDAO().moveIn(commodity, quantity, LocalDateTime.now());
    }

    public HashMap<Integer, Integer> moveOut(int commodity_id, int quantity) throws SQLException, CommodityNotFoundException, NotEnoughCommoditiesException {
        Commodity commodity = new CommoditiesDAO().findById(commodity_id);

        return new MovingsDAO().moveOut(commodity, quantity, LocalDateTime.now());
    }
/*
    public static void main(String[] args) {
        try {
            HashMap<Integer, Integer> res = moveIn(1, 2);
            System.out.println("Added:");
            for(Map.Entry<Integer, Integer> entry : res.entrySet()) {
                Integer lotId = entry.getKey();
                Integer q = entry.getValue();
                System.out.printf("Lot %d -> q %d \n", lotId, q);
            }
        } catch (CommodityNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NotEnoughSpaceException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error!");
        }
        try {
            HashMap<Integer, Integer> res = moveOut(1, 2);
            System.out.println("Removed:");
            for(Map.Entry<Integer, Integer> entry : res.entrySet()) {
                Integer lotId = entry.getKey();
                Integer q = entry.getValue();
                System.out.printf("Lot %d -> q %d \n", lotId, q);
            }
        } catch (CommodityNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NotEnoughCommoditiesException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error!");
        }

        try {
            /*int vol = new ReferencesDAO().totalFreeVolume();
            System.out.println(vol);
            int cap = new ReferencesDAO().totalFreeCapacity();
            System.out.println(cap);
            int volT = new ReferencesDAO().totalTakenVolume();
            System.out.println(volT);
            int capT = new ReferencesDAO().totalTakenCapacity();
            System.out.println(capT);
            double avg = new ReferencesDAO().averageTransactions(LocalDateTime.now().minusDays(2), LocalDateTime.now());
            System.out.println(avg);
            int pr = new ReferencesDAO().turnoverPrice(LocalDateTime.now().minusDays(1), LocalDateTime.now());
            System.out.println(pr);
            int we = new ReferencesDAO().turnoverWeight(LocalDateTime.now().minusDays(1), LocalDateTime.now());
            System.out.println(we);
            int totalComm = new ReferencesDAO().totalCommodityQuantity(1);
            System.out.println(totalComm);

            try {
                HashMap<Integer, Integer> spravka = new ReferencesDAO().commoditiesInLot(1);
                System.out.println("In lot:");
                for(Map.Entry<Integer, Integer> entry : spravka.entrySet()) {
                    Integer comId = entry.getKey();
                    Integer q = entry.getValue();
                    System.out.printf("Commodity %d -> q %d \n", comId, q);
                }
            } catch (SQLException e) {
                System.out.println("Error!");
            }

        } catch (SQLException e) {
            System.out.println("Error!");
        }


    }*/
}