package com.techelevator;

public class Chips implements Products {

    private String name = "";
    private String slot = "";
    private double price = 0.00;
    private int quantity = 5;
    private String message = "Crunch Crunch, Yum!";

    public Chips(String name, String slot, double price) {
        this.name = name;
        this.slot = slot;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSlot() {
        return slot;
    }

    @Override
    public void setSlot(String slot) {
        this.slot = slot;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int getQuantity() {
        if (quantity == 0) {
            System.out.println("This item is sold out");
        }
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void decrementQuantity() {
        quantity--;

    }



}