package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseMenuTests {

    VendingMachine vendingMachine;

    @Before
    public void getCustomerMoneyTest() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void enteredRock() {
        boolean isItCorrect = vendingMachine.isNumeric("Rock");
        Assert.assertEquals(false, isItCorrect);
    }

    @Test
    public void entered3454() {
        boolean isItCorrect = vendingMachine.isNumeric("34.54");
        Assert.assertEquals(false, isItCorrect);
    }

    @Test
    public void entered0() {
        boolean isItCorrect = vendingMachine.isNumeric("0");
        Assert.assertEquals(false, isItCorrect);
    }

    @Test
    public void entered564337() {
        boolean isItCorrect = vendingMachine.isNumeric("564337");
        Assert.assertEquals(true, isItCorrect);
    }

    @Test
    public void entered50Cent() {
        boolean isItCorrect = vendingMachine.isNumeric(".50");
        Assert.assertEquals(false, isItCorrect);
    }



}
