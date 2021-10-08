package com.techelevator;

public interface Products {

    public String getName();

    public void setName(String name);

    public String getSlot();

    public void setSlot(String slot);

    public double getPrice();

    public void setPrice(double price);

    public int getQuantity();

    public String getMessage();

    public void decrementQuantity();

}