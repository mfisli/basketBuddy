package com.example.fisli.basketbuddy;

import android.location.Address;

/**
 * Created by rshellborn on 2016-11-05.
 */
public class Store extends Trip {
    private Address address;
    private int hours; //need to plan how this will be stored
    private String name;
    private Item[] items;

    public Store() {

    }

    public Store(Address address, int hours, String name, Item[] items) {
        this.address = address;
        this.hours = hours;
        this.name = name;
        this.items = items;
    }

}
