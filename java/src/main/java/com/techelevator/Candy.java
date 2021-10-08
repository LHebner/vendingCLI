package com.techelevator;

public class Candy implements Products {

    private String name;
    private String slot;
    private double price;
    private int quantity = 5;
    private String message = "Munch Munch, Yum!";

    public Candy(String name, String slot, double price) {
        this.name = name;
        this.slot = slot;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        if (quantity == 0) {
            System.out.println("This item is sold out");
        }
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

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