package com.example.fisli.basketbuddy;

/**
 * Created by rshellborn on 2016-11-05.
 */
public class Trip {
    private Store[] stores;
    private double totalSpent;
    //private Date date; maybe add this later?

    public Trip() {

    }

    public Trip(Store[] stores, double totalSpent) {
        this.stores = stores;
        this.totalSpent = totalSpent;
    }
}
