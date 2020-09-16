package edu.pitt.cs.as1;

import edu.pitt.cs.as1.Shelf;
import edu.pitt.cs.as1.VendingItem;

public class VendingMachine {
    private static double totalSales = 0;
    private static final String[][] items = {
            {"1", "Red Bull" },
            {"1.50", "Cheetos" },
            {"1.50", "Bugles" },
            {"2", "Pretzels" }, 
            {"1", "Snickers" },
            {"1", "Twix" },
            {"1.25", "M n Ms" },
            {".75 ", "Popcorn" },
            {"1", "Twizzlers" },
            {"1", "Mars Bar" },
            {"1", "Cookies" },
            {"1.50", "Kit Kats" },
            {"1.25", "Carrots" },
            {"0.50", "Almond Joy" },
            {"0.50", "Spearmint" },
            {"0.50", "Five gum" },
            {"3.50", "Pepperoni" },
            {"1.75", "Cheez-Its" },
            {".25 ", "Slim Jim" },
            {"1.50", "Lays Chips" }};

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;
    private static final int QUANTITY = 5;

    private ShelfInterface<VendingItemInterface> shelf;

    public VendingMachine() {

        shelf = new Shelf<>(ROWS,COLUMNS,QUANTITY);

        for (int i = 0; i < ROWS; ++i)
            for (int j = 0; j < COLUMNS; ++j) {
                for (int k = 0; k < QUANTITY; ++k) {
                    String[] s = items[i * COLUMNS + j];
                    shelf.add(i,j,k,new VendingItem(Double.parseDouble(s[0]), s[1]));
                }
            }
    }

    public void restock() {
        for (int i = 0; i < ROWS; ++i)
            for (int j = 0; j < COLUMNS; ++j) {
                if (shelf.get(i,j,0) == null) {
                    for (int k = 0; k < QUANTITY; ++k) {
                        String[] s = items[i * COLUMNS + j];
                        shelf.add(i,j,k,new VendingItem(Double.parseDouble(s[0]), s[1]));                    }
                }
            }
    }

    public VendingItemInterface vend(String code) {
		for (int i = 0; i < ROWS; ++i)
            for (int j = 0; j < COLUMNS; ++j) {
                if (shelf.get(i,j,0) != null) {
                    if (shelf.getName(i,j,0).equalsIgnoreCase(code)) {
                        VendingItemInterface tovend = shelf.get(i,j,0);
                        totalSales += shelf.get(i,j,0).getPrice();
                        shelf.remove(i,j);
                        return tovend;

                    }
                } else {
                    System.out.println("You need to restock the items");
                }
            }

        return null;
    }

    private boolean free() {
        return true;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("------------------------------------------------------"
                + "------------\n");
        s.append("                            Pitt Vending                "
                + "            \n");
        for (int i = 0; i < ROWS; i++) {
            s.append("--------------------------------------------------"
                    + "----------------\n");
            for (int j = 0; j < COLUMNS; j++) {
                VendingItemInterface item = shelf.get(i,j,0);
                String str = String.format("| %-10s ",
                        (shelf.contains(item) ? item.getName():"(empty)"));
                s.append(str);
            }
            s.append("|\n");
        }
        s.append("------------------------------------------------------"
                + "------------\n");
        s.append(String.format("There are %d items with a total "
                + "value of $%.2f.%n", getNumberOfItems(), getTotalValue()));
        s.append(String.format("Total sales across vending machines "
                + "is now: $%.2f.%n", getTotalSales()));
        return s.toString();
    }

    public static double getTotalSales() {
        return totalSales;
    }

    public double getTotalValue() {
        double totalValue = 0;
        for (int i = 0; i < ROWS; ++i)
            for (int j = 0; j < COLUMNS; ++j) {
                for (int k = 0; k < QUANTITY; ++k) {
                    if (!shelf.isEmpty(i,j,k)) {
                        totalValue += shelf.get(i,j,k).getPrice();
                    }
                }
            }
        return totalValue;
    }

    public int getNumberOfItems() {
        int numberOfItems = 0;
        for (int i = 0; i < ROWS; ++i)
            for (int j = 0; j < COLUMNS; ++j) {
                for (int k = 0; k < QUANTITY; ++k) {
                    if (!shelf.isEmpty(i,j,k)) {
                        numberOfItems++;
                    }
                }
            }
        return numberOfItems;
    }

}
