package com.example.fisli.basketbuddy;

/**
 * Created by rshellborn on 2016-11-05.
 */
public class Item extends Store {
    private String name;
    private int quantity;
    private int aisle;

    public Item(String name, int quantity, int aisle) {
        this.name = name;
        this.quantity = quantity;
        this.aisle = aisle;
    }
}
