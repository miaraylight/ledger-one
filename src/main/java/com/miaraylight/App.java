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
    final static  ArrayList<Transaction> transactions = getAllTransactions("transactions.csv");
    public static void main(String[] args) {
        System.out.println("Here is my ledger app lives");
        runMainMenu();


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
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                // ToDo make it pretty output later
                case "A":

                    System.out.println("Here is all transactions:");
                    System.out.println(transactions);

                    break;
                case "D":

                    System.out.println("Here is all deposits:");
                    System.out.println(getAllDeposits());

                    break;
                case "P":

                    System.out.println("Here is all payments:");
                    System.out.println(getAllPayments());

                    break;
                case "R":
                    System.out.println(choice);
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
            int choice = scanner.nextInt();
            scanner.nextLine(); // eat it

            switch (choice) {
                case 1:
                    System.out.println(getMonthToDateTransactions());

                    break;
                case 2:
                    System.out.println(getPreviousMonthTransactions());

                    break;
                case 3:
                    System.out.println(getYearToDateTransactions());

                    break;
                case 4:
                    System.out.println(getPreviousYearTransactions());

                    break;
                case 5:
                    runSearchMenu();

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

    public static void runSearchMenu() {
        boolean running = true;

        while (running) {
            displaySearchMenu();
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // eat it

            switch (choice) {
                case 1:

                    System.out.println("Date");

                    break;
                case 2:

                    System.out.println("Desc");

                    break;
                case 3:
                    System.out.println("Please enter vendors name:");
                    String vendor = scanner.nextLine().trim().toLowerCase();
                    System.out.println(findByVendor(vendor));

                    break;
                case 4:

                    System.out.println("Amount");

                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }


    // Display methods
private static void displaySearchMenu() {
    System.out.println("[1] Date");
    System.out.println("[2] Description");
    System.out.println("[3] Vendor");
    System.out.println("[4] Amount");
    System.out.println("[0] Back");
}

    private static void displayReportMenu() {
        System.out.println("[1] Month To Date");
        System.out.println("[2] Previous Month");
        System.out.println("[3] Year To Date");
        System.out.println("[4] Previous Year");
        System.out.println("[5] Custom search");
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

    private static ArrayList<Transaction> getAllDeposits() {
        ArrayList<Transaction> deposits = new ArrayList<>();

        for(Transaction transaction: transactions){
            if (transaction.getAmount() > 0) {
                deposits.add(transaction);
            }
        }

        return deposits;
    }

    private static ArrayList<Transaction> getAllPayments() {
        ArrayList<Transaction> payments = new ArrayList<>();

        for(Transaction transaction: transactions){
            if (transaction.getAmount() < 0) {
                payments.add(transaction);
            }
        }

        return payments;
    }

    private static ArrayList<Transaction> findByVendor(String vendor) {
        ArrayList<Transaction> transactionsByVendor = new ArrayList<>();

        for (Transaction transaction: transactions) {
            if (vendor.equals(transaction.getVendor().toLowerCase())) {
                transactionsByVendor.add(transaction);
            }
        }

        if(transactionsByVendor.isEmpty()) {
            System.out.println("Nothing found by this vendor");
        }

        return transactionsByVendor;
    }

    private static ArrayList<Transaction> getMonthToDateTransactions() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        System.out.printf("Transactions from %s to %s \n", firstDayOfMonth, today);

        ArrayList<Transaction> filteredByMonthTransactions = new ArrayList<>();

        for (Transaction transaction: transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(firstDayOfMonth) && transactionDate.isBefore(today)) {
                filteredByMonthTransactions.add(transaction);
            }
        }

        if (filteredByMonthTransactions.isEmpty()) {
            System.out.println("No transactions at this month");
        }

        return filteredByMonthTransactions;
    }

    private static ArrayList<Transaction> getYearToDateTransactions() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfYear = today.withDayOfYear(1);

        System.out.printf("Transactions from %s to %s \n", firstDayOfYear, today);

        ArrayList<Transaction> filteredByYearTransactions = new ArrayList<>();

        for (Transaction transaction: transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(firstDayOfYear) && transactionDate.isBefore(today)) {
                filteredByYearTransactions.add(transaction);
            }
        }

        if (filteredByYearTransactions.isEmpty()) {
            System.out.println("No transactions at this year");
        }

        return filteredByYearTransactions;
    }

    private static ArrayList<Transaction> getPreviousMonthTransactions() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfPreviousMonth = firstDayOfCurrentMonth.minusMonths(1);

        System.out.printf("Transactions from %s to %s \n", firstDayOfPreviousMonth, firstDayOfCurrentMonth);

        ArrayList<Transaction> filteredByMonthTransactions = new ArrayList<>();

        for (Transaction transaction: transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(firstDayOfPreviousMonth) && transactionDate.isBefore(firstDayOfCurrentMonth)) {
                filteredByMonthTransactions.add(transaction);
            }
        }

        if (filteredByMonthTransactions.isEmpty()) {
            System.out.println("No transactions for previous month");
        }

        return filteredByMonthTransactions;
    }

    private static ArrayList<Transaction> getPreviousYearTransactions() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentYear = today.withMonth(1).withDayOfMonth(1);
        LocalDate firstDayOfPreviousYear = firstDayOfCurrentYear.minusYears(1);

        System.out.printf("Transactions from %s to %s \n", firstDayOfPreviousYear, firstDayOfCurrentYear);

        ArrayList<Transaction> filteredByYearTransactions = new ArrayList<>();

        for (Transaction transaction: transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(firstDayOfPreviousYear) && transactionDate.isBefore(firstDayOfCurrentYear)) {
                filteredByYearTransactions.add(transaction);
            }
        }

        if (filteredByYearTransactions.isEmpty()) {
            System.out.println("No transactions for previous year");
        }

        return filteredByYearTransactions;
    }

}
