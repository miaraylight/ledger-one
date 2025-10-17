package com.miaraylight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static final String TRANSACTION_FILE = "transactions.csv";
    final static Scanner scanner = new Scanner(System.in);
    final static ArrayList<Transaction> transactions = getAllTransactions();

    public static void main(String[] args) {
        runWelcome();

        runMainMenu();
    }

    // Run methods
    public static void runMainMenu() {
        boolean running = true;

        while (running) {
            displayMainMenu();
            System.out.println("Choose an option: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":
                    Transaction newDeposit = createTransaction(true);
                    formatAsCard(newDeposit);
                    System.out.println("✅ Deposit successfully added.");
                    break;
                case "P":
                    Transaction newPayment = createTransaction(false);
                    formatAsCard(newPayment);
                    System.out.println("💰 Payment successfully recorded.");
                    break;
                case "L":
                    runLedgerMenu();
                    break;
                case "X":
                    printGoodbye();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    public static void runLedgerMenu() {
        boolean running = true;

        while (running) {
            displayLedgerMenu();
            System.out.println("Choose an option: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A":

                    displayHeadline("All Transactions");
                    formatAsCard(transactions);

                    break;
                case "D":

                    displayHeadline("All Deposits");
                    formatAsCard(getAllDeposits());

                    break;
                case "P":

                    displayHeadline("All Payments");
                    formatAsCard(getAllPayments());

                    break;
                case "R":
                    runReportMenu();
                    break;
                case "H":
                    System.out.println("Returning to Home Menu...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    public static void runReportMenu() {
        boolean running = true;

        while (running) {
            displayReportMenu();
            int userInput = getIntInput("Choose an option: ");


            switch (userInput) {
                case 1:
                    displayReportResults(getMonthToDateTransactions());

                    break;
                case 2:
                    displayReportResults(getPreviousMonthTransactions());

                    break;
                case 3:
                    displayReportResults(getYearToDateTransactions());

                    break;
                case 4:
                    displayReportResults(getPreviousYearTransactions());

                    break;
                case 5:
                    runSearchMenu();

                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }

        }
    }

    public static void runSearchMenu() {
        boolean running = true;

        while (running) {
            displaySearchMenu();
            int userInput = getIntInput("Choose an option: ");

            switch (userInput) {
                case 1:
                    searchByDateRange();
                    break;
                case 2:
                    searchByDescription();
                    break;
                case 3:
                    searchByVendor();
                    break;
                case 4:
                    searchByAmountRange();
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
        System.out.println(AnsiColors.BOLD + AnsiColors.BLUE + "╔════════════════════════════════════════════╗" + AnsiColors.RESET);
        System.out.println(AnsiColors.BLUE + "              🔍 Search Menu        " + AnsiColors.RESET);
        System.out.println(AnsiColors.BLUE + "╠════════════════════════════════════════════╣" + AnsiColors.RESET);

        System.out.println("  " + AnsiColors.YELLOW + "[1]" + AnsiColors.RESET + " 📅 Search by Date        ");
        System.out.println("  " + AnsiColors.BLUE + "[2]" + AnsiColors.RESET + " 📝 Search by Description ");
        System.out.println("  " + AnsiColors.GREEN + "[3]" + AnsiColors.RESET + " 🏷️  Search by Vendor      ");
        System.out.println("  " + AnsiColors.CYAN + "[4]" + AnsiColors.RESET + " 💵 Search by Amount       ");
        System.out.println("  " + AnsiColors.RED + "[0]" + AnsiColors.RESET + " 🔙 Back to Reports        ");

        System.out.println(AnsiColors.CYAN + "╚════════════════════════════════════════════╝" + AnsiColors.RESET);
    }

    private static void displayReportMenu() {
        System.out.println(AnsiColors.BOLD + AnsiColors.CYAN + "╔════════════════════════════════════════════╗" + AnsiColors.RESET);
        System.out.println(AnsiColors.CYAN + "              📊 Reports Menu        " + AnsiColors.RESET);
        System.out.println(AnsiColors.CYAN + "╠════════════════════════════════════════════╣" + AnsiColors.RESET);

        System.out.println("  " + AnsiColors.YELLOW + "[1]" + AnsiColors.RESET + " 📆 Month To Date            ");
        System.out.println("  " + AnsiColors.BLUE + "[2]" + AnsiColors.RESET + " ⏪ Previous Month           ");
        System.out.println("  " + AnsiColors.CYAN + "[3]" + AnsiColors.RESET + " 🗓️  Year To Date            ");
        System.out.println("  " + AnsiColors.GREEN + "[4]" + AnsiColors.RESET + " 📉 Previous Year            ");
        System.out.println("  " + AnsiColors.PURPLE + "[5]" + AnsiColors.RESET + " 🔎 Custom Search            ");
        System.out.println("  " + AnsiColors.RED + "[0]" + AnsiColors.RESET + " 🔙 Back to Ledger           ");

        System.out.println(AnsiColors.BLUE + "╚════════════════════════════════════════════╝" + AnsiColors.RESET);
    }

    private static void displayLedgerMenu() {
        System.out.println(AnsiColors.BOLD + AnsiColors.BLUE + "╔════════════════════════════════════════════╗" + AnsiColors.RESET);
        System.out.println(AnsiColors.BLUE + "               📒 Ledger Menu                " + AnsiColors.RESET);
        System.out.println(AnsiColors.BLUE + "╠════════════════════════════════════════════╣" + AnsiColors.RESET);

        System.out.println("  " + AnsiColors.YELLOW + "[A]" + AnsiColors.RESET + " 📋 View All Transactions               ");
        System.out.println("  " + AnsiColors.GREEN + "[D]" + AnsiColors.RESET + " 💰 View Deposits                       ");
        System.out.println("  " + AnsiColors.RED + "[P]" + AnsiColors.RESET + " 💸 View Payments                       ");
        System.out.println("  " + AnsiColors.CYAN + "[R]" + AnsiColors.RESET + " 📊 Reports Menu                        ");
        System.out.println("  " + AnsiColors.PURPLE + "[H]" + AnsiColors.RESET + " 🔙 Return to Home Menu                 ");

        System.out.println(AnsiColors.BLUE + "╚════════════════════════════════════════════╝" + AnsiColors.RESET);
    }

    private static void displayMainMenu() {
        System.out.println(AnsiColors.BOLD + AnsiColors.CYAN + "╔════════════════════════════════════════════╗" + AnsiColors.RESET);
        System.out.println(AnsiColors.CYAN + "              🏠 Main Menu                   " + AnsiColors.RESET);
        System.out.println(AnsiColors.CYAN + "╠════════════════════════════════════════════╣" + AnsiColors.RESET);

        System.out.println("  " + AnsiColors.GREEN + "[D]" + AnsiColors.RESET + " ➕ Add Deposit                        ");
        System.out.println("  " + AnsiColors.RED + "[P]" + AnsiColors.RESET + " ➖ Make Payment (Debit)                ");
        System.out.println("  " + AnsiColors.YELLOW + "[L]" + AnsiColors.RESET + " 📒 View Ledger                         ");
        System.out.println("  " + AnsiColors.PURPLE + "[X]" + AnsiColors.RESET + " ❌ Exit Application                    ");

        System.out.println(AnsiColors.CYAN + "╚════════════════════════════════════════════╝" + AnsiColors.RESET);
    }

    public static void printGoodbye() {
        System.out.println();
        System.out.println(AnsiColors.GREEN + AnsiColors.BOLD + "\nThanks for using LedgerOne! ✨\nKeep tracking your finances like a pro! 💼💸" + AnsiColors.RESET);
    }

    public static void displayHeadline(String text) {
        String formattedStr = String.format("         %s", text);
        System.out.println(AnsiColors.BLUE + AnsiColors.BOLD + "╔════════════════════════════════════════════╗" + AnsiColors.RESET);
        System.out.println(AnsiColors.YELLOW + formattedStr + AnsiColors.RESET);
        System.out.println("╚════════════════════════════════════════════╝" + AnsiColors.RESET);
    }

    public static void runWelcome() {

        final String logo = """
                ▄                 █                        ▄▄▄▄              \s
                █       ▄▄▄    ▄▄▄█   ▄▄▄▄   ▄▄▄    ▄ ▄▄  ▄▀  ▀▄ ▄ ▄▄    ▄▄▄ \s
                █      █▀  █  █▀ ▀█  █▀ ▀█  █▀  █   █▀  ▀ █    █ █▀  █  █▀  █\s
                █      █▀▀▀▀  █   █  █   █  █▀▀▀▀   █     █    █ █   █  █▀▀▀▀\s
                █▄▄▄▄▄ ▀█▄▄▀  ▀█▄██  ▀█▄▀█  ▀█▄▄▀   █      █▄▄█  █   █  ▀█▄▄▀\s
                                      ▄  █                                   \s
                                       ▀▀                                    \s""";

        final String[] COLORS = {"\u001B[31m", // Red
                "\u001B[33m", // Yellow
                "\u001B[32m", // Green
                "\u001B[36m", // Cyan
                "\u001B[34m", // Blue
                "\u001B[35m", // Magenta
                "\u001B[91m", // Bright Red
                "\u001B[92m", // Bright Green
                "\u001B[93m", // Bright Yellow
                "\u001B[94m", // Bright Blue
        };


        final String RESET = "\u001B[0m";
        int colorIndex = 0;
        try {
            for (char ch : logo.toCharArray()) {
                System.out.print(COLORS[colorIndex % COLORS.length] + ch);
                Thread.sleep(1);
                colorIndex++;
            }
            System.out.println(RESET);
        } catch (InterruptedException e) {
            System.out.println("Error:" + e.getMessage());
        }

        // welcome banner
        System.out.println(AnsiColors.BLUE + AnsiColors.BOLD + "╔════════════════════════════════════════════╗" + AnsiColors.RESET);
        System.out.println(AnsiColors.YELLOW + "          💰 Welcome to LedgerOne 💰 " + AnsiColors.RESET);
        System.out.println("╚════════════════════════════════════════════╝" + AnsiColors.RESET);

        try {
            for (int i = 0; i < 17; i++) {
                System.out.print(COLORS[colorIndex % COLORS.length] + "^-^" + RESET);
                Thread.sleep(20);
                colorIndex++;
            }
            System.out.println();
        } catch (InterruptedException e) {
            System.out.println("Loading interrupted: " + e.getMessage());
        }


    }


    // Read methods
    private static ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
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
            System.out.println("Error:" + e.getMessage());
        }
        return transactions;
    }

    private static ArrayList<Transaction> getAllDeposits() {
        ArrayList<Transaction> deposits = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                deposits.add(transaction);
            }
        }

        return deposits;
    }

    private static ArrayList<Transaction> getAllPayments() {
        ArrayList<Transaction> payments = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                payments.add(transaction);
            }
        }

        return payments;
    }

    private static ArrayList<Transaction> getMonthToDateTransactions() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        String info = String.format("\nTransactions from %s to %s \n", firstDayOfMonth, today);
        displayHeadline(info);

        ArrayList<Transaction> filteredByMonthTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(firstDayOfMonth) && transactionDate.isBefore(today)) {
                filteredByMonthTransactions.add(transaction);
            }
        }

        return filteredByMonthTransactions;
    }

    private static ArrayList<Transaction> getYearToDateTransactions() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfYear = today.withDayOfYear(1);

        System.out.printf("Transactions from %s to %s \n", firstDayOfYear, today);

        ArrayList<Transaction> filteredByYearTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
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

        String info = String.format("Transactions from %s to %s \n", firstDayOfPreviousMonth, firstDayOfCurrentMonth);
        displayHeadline(info);

        ArrayList<Transaction> filteredByMonthTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(firstDayOfPreviousMonth) && transactionDate.isBefore(firstDayOfCurrentMonth)) {
                filteredByMonthTransactions.add(transaction);
            }
        }

        return filteredByMonthTransactions;
    }

    private static ArrayList<Transaction> getPreviousYearTransactions() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentYear = today.withMonth(1).withDayOfMonth(1);
        LocalDate firstDayOfPreviousYear = firstDayOfCurrentYear.minusYears(1);

        String info = String.format("Transactions from %s to %s \n", firstDayOfPreviousYear, firstDayOfCurrentYear);
        displayHeadline(info);

        ArrayList<Transaction> filteredByYearTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(firstDayOfPreviousYear) && transactionDate.isBefore(firstDayOfCurrentYear)) {
                filteredByYearTransactions.add(transaction);
            }
        }

        return filteredByYearTransactions;
    }

    private static void searchByDateRange() {
        LocalDate startDate;
        LocalDate endDate;

        try {
            System.out.print("Please enter START date (ex. 2023-01-30): ");
            startDate = LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        try {
            System.out.print("Please enter END date (ex. 2024-01-30): ");
            endDate = LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        if (endDate.isBefore(startDate)) {
            System.out.println("⚠️ Error: End date cannot be before the start date.");
            return;
        }

        ArrayList<Transaction> result = filterByDate(startDate, endDate);
        displayHeadline("Date Range (" + startDate + " to " + endDate + ")");
        displayReportResults(result);
    }

    private static ArrayList<Transaction> filterByDate(LocalDate startDate, LocalDate endDate) {
        ArrayList<Transaction> filteredByDateTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                filteredByDateTransactions.add(transaction);
            }
        }

        return filteredByDateTransactions;
    }

    private static void searchByVendor() {
        System.out.print("Please enter vendor's name: ");
        String vendor = scanner.nextLine().trim().toLowerCase();

        if (vendor.isEmpty()) {
            System.out.println("⚠️ Vendor name cannot be empty.");
            return;
        }

        ArrayList<Transaction> result = filterByVendor(vendor);
        displayReportResults(result);
    }

    private static ArrayList<Transaction> filterByVendor(String vendor) {
        ArrayList<Transaction> transactionsByVendor = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getVendor().toLowerCase().contains(vendor)) {
                transactionsByVendor.add(transaction);
            }
        }

        return transactionsByVendor;
    }

    private static void searchByAmountRange() {
        float lowAmount = getFloatInput("Please enter LOWEST amount: ");
        float highAmount = getFloatInput("Please enter HIGHEST amount: ");

        if (lowAmount > highAmount) {
            System.out.println("⚠️ Error: Highest amount cannot be less than the lowest amount.");
            return;
        }

        ArrayList<Transaction> result = filterByAmount(lowAmount, highAmount);
        displayHeadline("Amount Range: $" + lowAmount + " to $" + highAmount);
        displayReportResults(result);
    }

    private static ArrayList<Transaction> filterByAmount(float lowAmount, float highAmount) {
        ArrayList<Transaction> filteredByAmountTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            float transactionAmount = transaction.getAmount();
            if (transactionAmount >= lowAmount && transactionAmount <= highAmount) {
                filteredByAmountTransactions.add(transaction);
            }
        }

        return filteredByAmountTransactions;
    }

    private static void searchByDescription() {
        System.out.print("Please enter description keyword: ");
        String description = scanner.nextLine().trim();

        if (description.isEmpty() || description.isBlank()) {
            System.out.println("⚠️ Description cannot be empty.");
            return;
        }

        ArrayList<Transaction> result = filterTransactionsByDescription(description);
        displayReportResults(result);
    }

    private static ArrayList<Transaction> filterTransactionsByDescription(String description) {
        ArrayList<Transaction> filteredTransactionsByDescription = new ArrayList<>();

        for (Transaction transaction : transactions) {
            String transactionDescription = transaction.getDescription();
            if (transactionDescription.contains(description)) {
                filteredTransactionsByDescription.add(transaction);
            }
        }

        return filteredTransactionsByDescription;
    }


    // Write methods
    private static Transaction createTransaction(boolean isDeposit) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        System.out.println("Enter description:");
        String description = scanner.nextLine().trim();

        System.out.println("Enter vendor:");
        String vendor = scanner.nextLine().trim();

        System.out.println("Enter amount:");
        float amount = scanner.nextFloat();
        scanner.nextLine();

        if (isDeposit) {
            if (amount < 0) {
                amount = -amount;
            }
        } else {

            if (amount > 0) {
                amount = -amount;
            }
        }

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        transactions.add(transaction);

        try {
            FileWriter writer = new FileWriter(TRANSACTION_FILE, true);
            writer.write("\n" + transaction.toCsv());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }

        return transaction;

    }


    // Formatting methods
    public static void formatAsCard(ArrayList<Transaction> transactions) {

        for (Transaction t : transactions) {
            String colorAmount = t.getAmount() >= 0 ? AnsiColors.GREEN : AnsiColors.RED;

            System.out.println(AnsiColors.BOLD + AnsiColors.WHITE + "╔════════════════════════════════════════════╗" + AnsiColors.RESET);
            System.out.println(" " + AnsiColors.RESET + "  📅 Date:      " + AnsiColors.YELLOW + t.getDate() + AnsiColors.RESET);
            System.out.println(" " + AnsiColors.RESET + "  ⏰ Time:      " + AnsiColors.YELLOW + t.getTime() + AnsiColors.RESET);
            System.out.println(" " + AnsiColors.RESET + "  📝 Note:      " + AnsiColors.BLUE + t.getDescription() + AnsiColors.RESET);
            System.out.println(" " + AnsiColors.RESET + "  🏷️ Vendor:    " + AnsiColors.CYAN + t.getVendor() + AnsiColors.RESET);
            System.out.println(" " + AnsiColors.RESET + "  💰 Amount:    " + colorAmount + String.format("$%.2f", t.getAmount()) + AnsiColors.RESET);
            System.out.println(AnsiColors.WHITE + "╚════════════════════════════════════════════╝" + AnsiColors.RESET);
            System.out.println(); // space between cards
        }


    }

    public static void formatAsCard(Transaction transaction) {
        // Reuse the logic for one card
        ArrayList<Transaction> list = new ArrayList<>();
        list.add(transaction);
        formatAsCard(list); // Delegate to the list method
    }

    // Helpers

    public static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
            }
        }
    }

    private static float getFloatInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Float.parseFloat(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void displayReportResults(ArrayList<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("\n🤷‍♂️ No transactions found for ");
        } else {
            formatAsCard(transactions);
        }
    }

}
