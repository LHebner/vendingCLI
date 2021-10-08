package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.techelevator.view.Menu;
import MakingChange.MakeChange;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "EXIT";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction ";
	private static final String PURCHASE_MENU_OPTION_EXIT = "Go Back";

	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION, PURCHASE_MENU_OPTION_EXIT };

	private static final String FEED_OPTION_1 = "add $1";
	private static final String FEED_OPTION_2 = "add $2";
	private static final String FEED_OPTION_3 = "add $5";
	private static final String FEED_OPTION_4 = "add $10";
	private static final String FEED_OPTION_EXIT = "Go Back";
	private static final String[] FEED_MENU_OPTIONS = { FEED_OPTION_1, FEED_OPTION_2, FEED_OPTION_3, FEED_OPTION_4,
			FEED_OPTION_EXIT };

	private Menu menu;
	public Scanner userInput = new Scanner(System.in);

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
	Date date = new Date();
	List<Products> productsList = new ArrayList<Products>();
	List<String> audit = new ArrayList<>();

	public void run() throws IOException {

		File vendingMachine = new File("vendingmachine.csv");
		Scanner sc = new Scanner(vendingMachine.getAbsoluteFile());

		while (sc.hasNextLine()) {
			String[] display = sc.nextLine().split("\\|");
			if (display[3].equals("Chip")) {
				productsList.add(new Chips(display[1], display[0], Double.parseDouble(display[2])));
			} else if (display[3].equals("Gum")) {
				productsList.add(new Gum(display[1], display[0], Double.parseDouble(display[2])));
			} else if (display[3].equals("Drinks")) {
				productsList.add(new Beverages(display[1], display[0], Double.parseDouble(display[2])));
			} else if (display[3].equals("Candy")) {
				productsList.add(new Candy(display[1], display[0], Double.parseDouble(display[2])));
			}
		}

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				for (Products product : productsList) {
					System.out.print(product.getSlot() + " | " + product.getName() + " | " + "$" + product.getPrice()
							+ " | " + product.getQuantity() + "\n");
				}

				// display vending machine items

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase

				MakeChange makeChange = new MakeChange();
				CustomerMoney customerBalance = new CustomerMoney();
				double customerBal = 0;
				double customerDeposit = 0;

				while (true) {
					String secondChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					if (secondChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {

						String thirdChoice = (String) menu.getChoiceFromOptions(FEED_MENU_OPTIONS);


						//tested
						if (thirdChoice.equals(FEED_OPTION_1)) {
							customerBalance.addToCurrent(1.00);
							customerDeposit = 1.00;

						} else if (thirdChoice.equals(FEED_OPTION_2)) {
							customerBalance.addToCurrent(2.00);
							customerDeposit = 2.00;

						} else if (thirdChoice.equals(FEED_OPTION_3)) {

							customerBalance.addToCurrent(5.00);
							customerDeposit = 5.00;

						} else if (thirdChoice.equals(FEED_OPTION_4)) {
							customerBalance.addToCurrent(10.00);
							customerDeposit = 10.00;

						} else if (thirdChoice.equals(FEED_OPTION_EXIT)) {
							break;
						}

						System.out.println("Current money provided: " + "$" + customerBalance.getCurrentBal());
						audit.add(dateFormat.format(date) + " FEED MONEY: $" + customerDeposit + " $"
								+ String.format("%.2f", customerBalance.getCurrentBal()) + "\n");

					} else if (secondChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						System.out.println("Please enter the slot of the product you would like to purchase: ");
						String userProduct = userInput.nextLine();

						for (Products productSelection : productsList) {
							if (productSelection.getSlot().equals(userProduct)) {
								// check the price, make sure they have enough money
								System.out.println("Current balance: $" + customerBalance.getCurrentBal() + " | "
										+ "Product cost: $" + productSelection.getPrice());

								double productPrice = productSelection.getPrice();
								int productQuantity = productSelection.getQuantity();
								if (productPrice > customerBalance.getCurrentBal() || productQuantity == 0) {
									System.out.println("Invalid choice");
								} else {
									productSelection.decrementQuantity();
									customerBal = customerBalance.getCurrentBal();
									customerBal -= productPrice;

									System.out.println("Here is your item: " + productSelection.getName());
									System.out.println(productSelection.getMessage());
								}
								audit.add(dateFormat.format(date) + " " + productSelection.getName() + " "
										+ productSelection.getSlot() + " $" + customerBalance.getCurrentBal() + "\n");
							}

						}
						// not sure location
//							File log = new File("/19_Capstone/java");
//							try (FileWriter writer = new FileWriter(log.getAbsoluteFile(), true)) {
//							for (String x : audit) {
//								writer.write(x + "\n");
//							}
//							writer.flush();
//						}
//						break;

					} else if (secondChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						audit.add(dateFormat.format(date) + " GIVE CHANGE: " + makeChange.makeChange(customerBal) + " $"
								+ String.format("%.2f", 0.0) + "\n");
						System.out.println(makeChange.makeChange(customerBal) + ". "
								+ "Your current balance is now $0.00. Have a great day!");

						File auditFile = new File("audit.txt");
						try (FileWriter writer = new FileWriter(auditFile.getAbsoluteFile(), true)) {
							for (String x : audit) {
								writer.write(x + "\n");
							}
							writer.flush();
						}
						break;
					} else if (secondChoice.equals(PURCHASE_MENU_OPTION_EXIT)) {
						break;
					}

				}

			}
		}

	}

	// added throws exception
	public static void main(String[] args) throws IOException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}