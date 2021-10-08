package com.techelevator;

import java.util.HashMap;
import java.util.Map;

public class MakeChange {

    public String makeChange(double currentBalance) {

        currentBalance *= 100;
        int intBal = (int) currentBalance;

        int quarters = intBal / 25;
        int dimes = (intBal - (quarters * 25)) / 10;
        int nickels = (intBal - (quarters * 25 + dimes * 10)) / 5;

        String quarterAmount = "";
        String dimeAmount = "";
        String nickelAmount = "";

        if (quarters == 1) {
            quarterAmount = quarters + " quarter";
        } else if (quarters > 1) {
            quarterAmount = quarters + " quarters";
        }

        if (dimes == 1) {
            dimeAmount = quarters > 0 ? " and " + dimes + " dime" : dimes + " dime";
        } else if (dimes > 1) {
            dimeAmount = quarters > 0 ? " and " + dimes + " dimes" : dimes + " dimes";
        }

        if (nickels == 1) {
            nickelAmount = (quarters > 0 || dimes > 0) ? " and " + nickels + " nickel" : nickels + " nickel";
        } else if (nickels > 1) {
            nickelAmount = (quarters > 0 || dimes > 0) ? " and " + nickels + " nickels" : nickels + " nickels";
        }

        if ((quarterAmount + dimeAmount + nickelAmount).equals("")) {
            return "You have spent all your money or have not added money yet. Thanks for shopping!";
        }

        currentBalance = 0;
        return "Your change is " + quarterAmount + dimeAmount + nickelAmount;
    }

}