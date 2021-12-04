package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {
    public LinkedHashMap<String,VendingItem> inventory = new LinkedHashMap<String, VendingItem>();
    private Scanner scanner = new Scanner(System.in);
    public BigDecimal userBalance = BigDecimal.valueOf(0);


    public VendingMachine() {
            // Gets stock of items from Inventory File
            File inventoryTemp = new File("vendingmachine.csv");
            try(Scanner reader = new Scanner(inventoryTemp)) {
//                BufferedReader reader = new BufferedReader(new FileReader(inventoryTemp));

                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] invSegments = line.split("\\|");
                    VendingItem newItem = new VendingItem(invSegments[0],invSegments[1],new BigDecimal(invSegments[2]),invSegments[3]);
                    inventory.put(invSegments[0], newItem);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Couldnt find the file!");
        }
    }

//    public void getCustomerMoney() {
//        boolean validResponse = false;
//        while (!validResponse) {
//            System.out.print("Please enter your money: ");
//            String moneyInputString = scanner.nextLine();
//            if (isNumeric(moneyInputString)) {
//                userBalance = new BigDecimal(moneyInputString);
//                userBalance.setScale(2, RoundingMode.HALF_UP);
//                break;
//            }
//        }
//        System.out.println("$" + userBalance + ", Great!\nLet's get some snacks!\n");
//    }

    public void purchaseProcess() {
     boolean validResponse = false;
        while (!validResponse) {
            if (userBalance.equals(BigDecimal.valueOf(0))) {
                System.out.print("Please enter your money: ");
                String moneyInputString = scanner.nextLine();

                if (isNumeric(moneyInputString)) {
                    BigDecimal userMoney = new BigDecimal(moneyInputString);
                    userMoney.setScale(2, RoundingMode.HALF_UP);
                    userBalance = userBalance.add(userMoney);
                    System.out.println("$" + userBalance + ", Great!\nLet's get some snacks!\n");
                    break;
                }
            } else {
                System.out.println("Your remaining balance is " + userBalance);
                break;
            }
            }
            //System.out.println("$" + userBalance + ", Great!\nLet's get some snacks!\n");
            System.out.println("---------------------------------------------------------");
            System.out.println(printVendingContents());

            System.out.println("Please select an item to purchase:");
            String userInput = scanner.nextLine().toUpperCase();

            if (!inventory.containsKey(userInput)) {
                System.out.println("Not A Valid Location");
            } else if (userBalance.doubleValue() - inventory.get(userInput).getPrice().doubleValue() <= 0.00) {
                System.out.println("Not Enough Money");
            } else if (inventory.get(userInput).getInStockAmount() <= 0) {
                System.out.println("Item is SOLD OUT!");
            } else {
                BigDecimal startingBal = inventory.get(userInput).getPrice();
                BigDecimal itemPrice = inventory.get(userInput).getPrice();
                userBalance = userBalance.subtract(itemPrice);
                inventory.get(userInput).itemIsPurchased();
                System.out.println("You choose " + inventory.get(userInput).getItemName()
                                    + " for $" + inventory.get(userInput).getPrice());
                System.out.println("Your change is $" + userBalance);
                if (inventory.get(userInput).getItemType().equals("Chip")) {
                    System.out.println("Crunch Crunch, Yum!");
                } else if (inventory.get(userInput).getItemType().equals("Candy")) {
                    System.out.println("Munch Munch, Yum!");
                } else if (inventory.get(userInput).getItemType().equals("Drink")) {
                    System.out.println("Glug Glug, Yum!");
                } else {
                    System.out.println("Chew Chew, Yum!");
                }
                logSale(startingBal);
            }
//        if(inventory.containsKey(userInput))
//        {
//            if(userBalance.doubleValue() - inventory.get(userInput).getPrice().doubleValue()  > 0.00)
//            {
//                if(inventory.get(userInput).getInStockAmount() > 0)
//                {
//                    BigDecimal startingBal = inventory.get(userInput).getPrice();
//                    BigDecimal itemPrice = inventory.get(userInput).getPrice();
//                    userBalance = userBalance.subtract(itemPrice);
//                    inventory.get(userInput).itemIsPurchased();
//                    System.out.println("You choose " + inventory.get(userInput).getItemName());
//                    System.out.println("Your change is $" + userBalance);
//                    logSale(startingBal);
//                } else
//                {
//                    System.out.println("Item is SOLD OUT!");
//                }
//            }
//            else
//            {
//                System.out.println("Not Enough Money");
//            }
//        }
//        else
//        {
//            System.out.println("Not A Valid Location");
//        }

    }

    public void logSale(BigDecimal startBalance) {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime presentTime = LocalDateTime.now();
        try {

            FileWriter newPrint = new FileWriter("Log.txt", true);
            newPrint.write("\n" + timeFormat.format(presentTime) + " " + startBalance  + " " + userBalance);

            newPrint.close();
        } catch (IOException e) {
        }
    }

    public boolean isNumeric(String moneyInput) {
        try {
            if(Integer.parseInt(moneyInput) > 0) {
                return true;
            }
        } catch (NumberFormatException e) {}
        System.out.println(moneyInput + " is not a proper value.");
        return false;
    }

    public String printVendingContents() {
        char previousId = inventory.get("A1").getLocationId().charAt(0);
        int longestItemLength = 0;
        int longestItemTypeLength = 0;
        for(Map.Entry<String, VendingItem> item : inventory.entrySet()) {
            if(item.getValue().getItemName().length() > longestItemLength) {
                longestItemLength = item.getValue().getItemName().length();
            }
            if(item.getValue().getItemType().length() > longestItemTypeLength) {
                longestItemTypeLength = item.getValue().getItemType().length();
            }
        }

        for(Map.Entry<String, VendingItem> item : inventory.entrySet()) {
            String itemName = item.getValue().getItemName() + (" ").repeat(longestItemLength - item.getValue().getItemName().length());
            String itemType = item.getValue().getItemType() + (" ").repeat(longestItemTypeLength - item.getValue().getItemType().length());
            if(item.getValue().getLocationId().charAt(0) == previousId) {
                System.out.print("|"+ item.getValue().getLocationId() + " " + itemName + " " + itemType +
                        " $" + item.getValue().getPrice()  + "  InStock: " + item.getValue().getInStockAmount() );
            }
            else {
                System.out.print("\n|"+ item.getValue().getLocationId() + " " + itemName  + " " + itemType  +
                        " $" + item.getValue().getPrice()  + "  InStock: " + item.getValue().getInStockAmount() );
            }
            previousId = item.getValue().getLocationId().charAt(0);
            System.out.println("");
        }

        return "";
    }

    public void completeTransaction() {
        System.out.println("Thank you for your purchase!");
        System.out.println("Please take your change: $" + userBalance);
        userBalance = new BigDecimal(0);
    }
}
