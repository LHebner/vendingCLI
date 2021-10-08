package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine
{
    public LinkedHashMap<String,VendingItem> inventor = new LinkedHashMap<String, VendingItem>();
    private Scanner scanner = new Scanner(System.in);
    public BigDecimal userBalance;


    public VendingMachine()
    {
            // Gets stock of items from Inventory File
            File inventoryTemp = new File("vendingmachine.csv");
            try(Scanner reader = new Scanner(inventoryTemp))
            {
//                BufferedReader reader = new BufferedReader(new FileReader(inventoryTemp));

                while (reader.hasNextLine())
                {
                    String line = reader.nextLine();
                    String[] invSegments = line.split("\\|");
                    VendingItem newItem = new VendingItem(invSegments[0],invSegments[1],new BigDecimal(invSegments[2]),invSegments[3]);
                    inventor.put(invSegments[0] ,newItem);
                }
            } catch (FileNotFoundException e)
            {
                System.out.println("Couldnt find the file!");
        }
    }

    public void getCustomerMoney()
    {
        boolean validResponse = false;
        while (!validResponse) {
            System.out.print("Please enter your money: ");
            String moneyInputString = scanner.nextLine();
            if (isNumeric(moneyInputString)) {
                userBalance = new BigDecimal(moneyInputString);
                userBalance.setScale(2, RoundingMode.HALF_UP);
                break;
            }
        }
        System.out.println("$" + userBalance + ", Great!\nLet's get some snacks!");
    }

    public void purchaseProcess()
    {
        System.out.println("Which item do you want");
        String userInput = scanner.nextLine();
        if(inventor.containsKey(userInput))
        {
            if(userBalance.doubleValue() - inventor.get(userInput).getPrice().doubleValue()  > 0.00)
            {
                if(inventor.get(userInput).getInStockAmount() >= 0)
                {
                    BigDecimal startingBal = inventor.get(userInput).getPrice();
                    BigDecimal itemPrice = inventor.get(userInput).getPrice();
                    userBalance = userBalance.subtract(itemPrice);
                    inventor.get(userInput).itemIsPurchased();
                    System.out.println("You choose " + inventor.get(userInput).getItemName());
                    System.out.println("Your change is $" + userBalance);
                    logSale(startingBal);
                } else
                {
                    System.out.println("Item is SOLD OUT!");
                }
            }
            else
            {
                System.out.println("Not Enough Money");
            }
        }
        else
        {
            System.out.println("Not A Valid Location");
        }
    }

    public void logSale(BigDecimal startBalance)
    {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime presentTime = LocalDateTime.now();
        try {

            FileWriter newPrint = new FileWriter("Log.txt", true);
            newPrint.write("\n" + timeFormat.format(presentTime) + " " + startBalance  + " " + userBalance);

            newPrint.close();
        } catch (IOException e)
        {
        }
    }

    public boolean isNumeric(String moneyInput) {
        try {
            if(Integer.parseInt(moneyInput) > 0)
            {
                return true;
            }
        } catch (NumberFormatException e) {}
        System.out.println(moneyInput + " is not a proper value.");
        return false;
    }

    public String printVendingContents()
    {
        char previousId = inventor.get("A1").getLocationId().charAt(0);
        int longestItemLength = 0;
        int longestItemTypeLength = 0;
        for(Map.Entry<String, VendingItem> item : inventor.entrySet())
        {
            if(item.getValue().getItemName().length() > longestItemLength)
            {
                longestItemLength = item.getValue().getItemName().length();
            }
            if(item.getValue().getItemType().length() > longestItemTypeLength)
            {
                longestItemTypeLength = item.getValue().getItemType().length();
            }
        }

        for(Map.Entry<String, VendingItem> item : inventor.entrySet())
        {
            String itemName = item.getValue().getItemName() + (" ").repeat(longestItemLength - item.getValue().getItemName().length());
            String itemType = item.getValue().getItemType() + (" ").repeat(longestItemTypeLength - item.getValue().getItemType().length());
            if(item.getValue().getLocationId().charAt(0) == previousId)
            {
                System.out.print("|"+ item.getValue().getLocationId() + " " + itemName + " " + itemType + " $" + item.getValue().getPrice()  + "  InStock: " + item.getValue().getInStockAmount() + " " );
            }
            else
            {
                System.out.print("\n|"+ item.getValue().getLocationId() + " " + itemName  + " " + itemType  + " $" + item.getValue().getPrice()  + "  InStock: " + item.getValue().getInStockAmount() + " " );
            }
            previousId = item.getValue().getLocationId().charAt(0);
            System.out.println(previousId);
        }

        return String.valueOf(previousId);
    }
}
