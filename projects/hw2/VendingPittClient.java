package edu.pitt.cs.as1;

import java.util.*;

/**
 * Client code for for VendingMachine classes.
 *
 * @author Constantinos Costa
 */
public class VendingPittClient {

    /**
     * Runner
     * @param args Program arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder clear = new StringBuilder();
        for (int i = 0; i < 1; i++) {
            clear.append("\n");
        }
        VendingMachine posvar = new VendingMachine();
        VendingMachine sennot = new VendingMachine();
        VendingMachine current = null;
        System.out.println(clear);
        do {
            System.out.println("Which machine would you like to visit?");
            System.out.println("(1) Posvar Hall");
            System.out.println("(2) Sennott Square");
            System.out.println("(3) Quit");

            String choice = sc.nextLine();

            if (choice.equals("1")) {
                current = posvar;
            } else if (choice.equals("2")) {
                current = sennot;
            } else if (choice.equals("3")) {
                System.exit(0);
            } else {
                System.out.println(clear);
                System.out.println("That is not a valid option. Try again.");
                continue;
            }
            System.out.println(clear);

            String item;
            do {
                System.out.println(current);
                System.out.println("Enter your choice (or type 'back' to go"
                    + " back to the main menu or"
                    + " 'restock' to restock the machine):");
                item = sc.nextLine().trim();
                if (item.equalsIgnoreCase("restock")) {
                    System.out.println(clear);
                    current.restock();
                    System.out.println("You have restocked the machine.");
                } else if (!item.equalsIgnoreCase("back")) {
                    System.out.println(clear);
                    System.out.println("You dispensed " + current.vend(item));
                } else {
                    System.out.println(clear);
                }
            } while (!item.equalsIgnoreCase("back"));
        } while (true);
    }
}