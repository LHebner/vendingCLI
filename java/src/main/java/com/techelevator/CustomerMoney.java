package com.techelevator;

public class CustomerMoney {

    private double currentBal = 0.0;

    public double getCurrentBal() {
        return currentBal;
    }

    public void addToCurrent(double addMoney) {
        this.currentBal += addMoney;
    }

}