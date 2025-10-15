package com.miaraylight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Here is my ledger app lives");

        //runMainMenu();

        ArrayList<Transaction> transactions = getAllTransactions("transactions.csv");

        for (Transaction transaction: transactions) {
            System.out.println(transaction.toString());
        }
    }
// Run methods
    public static void runMainMenu() {
        boolean running = true;

        while (running) {
            displayHomeScreen();
            System.out.println("Choose an option: ");
            String choise = scanner.nextLine().trim().toUpperCase();

            switch (choise) {
                case "A":
                    System.out.println(choise);
                    break;
                case "P":
                    System.out.println(choise);
                    break;
                case "L":
                    System.out.println(choise);
                    runLedgerMenu();
                    break;
                case "X":
                    System.out.println("Exiting");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }

    public static void runLedgerMenu() {
        boolean running = true;

        while (running) {
            displayLedgerMenu();
            System.out.println("Choose an option: ");
            String choise = scanner.nextLine().trim().toUpperCase();

            switch (choise) {
                case "A":
                    System.out.println(choise);
                    break;
                case "D":
                    System.out.println(choise);
                    break;
                case "P":
                    System.out.println(choise);
                    break;
                case "R":
                    System.out.println(choise);
                    runReportMenu();
                    break;
                case "H":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }

    public static void runReportMenu() {
        boolean running = true;

        while (running) {
            displayReportMenu();
            System.out.println("Choose an option: ");
            int choise = scanner.nextInt();

            switch (choise) {
                case 1:
                    System.out.println(choise);
                    break;
                case 2:
                    System.out.println(choise);
                    break;
                case 3:
                    System.out.println(choise);
                    break;
                case 4:
                    System.out.println(choise);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }

// Display methods
    private static void displayReportMenu() {
        System.out.println("[1] Month To Date");
        System.out.println("[2] Previous Month");
        System.out.println("[3] Year To Date");
        System.out.println("[4] Search by Vendor");
        System.out.println("[0] Back");
    }

    private static void displayLedgerMenu() {
        System.out.println("[A] All");
        System.out.println("[D] Deposits");
        System.out.println("[P] Payments");
        System.out.println("[R] Reports");
        System.out.println("[H] Home");
    }

    private static void displayHomeScreen() {
        System.out.println("[D] Add Deposit");
        System.out.println("[P] Make Payment (Debit)");
        System.out.println("[L] Ledger");
        System.out.println("[X] Exit");
    }

    private static ArrayList<Transaction> getAllTransactions(String filename) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))){
            String line;
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                float amount = Float.parseFloat(parts[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println("Error:");
            System.out.println(e);
        }
        return transactions;
    }
}
