# LedgerOne - Financial Transaction Tracker CLI Application

LedgerOne is a Java-based Command-Line Interface (CLI) application for tracking financial transactions. It can be used for personal or business purposes to log deposits and payments, view a complete ledger, and generate reports. All financial data is stored in a persistent file (`transactions.csv`) ensuring data retention between sessions.

---

## 🌟 Project Overview

This CLI application allows users to:
- Record income and expenses.
- View transactions in various ways.
- Generate pre-defined and custom reports.
- Maintain all data in a standardized format: 
**date|time|description|vendor|amount**

---

## ✨ Key Features & Screens

### 🏠 Home Screen Options

- `[D]` **Add Deposit**: Log a positive (income) transaction.
- `[P]` **Make Payment (Debit)**: Log a negative (expense) transaction.
- `[L]` **Ledger**: Navigate to transaction listings and reports.
- `[X]` **Exit**: Exit the application.

---

### 📒 Ledger Screen Options

The Ledger view is the hub for analyzing transactions (newest first):

- `[A]` **All**: Show all transactions.
- `[D]` **Deposits**: Show only deposits.
- `[P]` **Payments**: Show only payments.
- `[R]` **Reports**: Access the Reports Screen.
- `[H]` **Home**: Return to the Home Screen.

---

### 📊 Reports Screen Options

Perform in-depth analysis with several filters:

- **Month To Date**: Transactions from the start of the current month.
- **Previous Month**: Transactions from the previous full month.
- **Year To Date**: From January 1st to today.
- **Previous Year**: Entire previous year.
- **Search by Vendor**: Filter by vendor name.
- **Custom Search (Challenge)**: Search by any combination of:
    - Start Date
    - End Date
    - Description Keyword
    - Vendor Name
    - Amount (optional)

- `Back`: Return to the Ledger Screen.

---

## 💻 Interesting Code Segment: Custom Search Logic

The ***Custom Search*** is the most complex feature, allowing multi-parameter filtering. It uses Java Streams to handle filtering efficiently and gracefully skips criteria that are not provided by the user.

### 🔍 Code Snippet

```java

   ```

---

**Attribution**: *Portions of this README were generated with the assistance of *ChatGPT (GPT-4)*, developed by [OpenAI](https://openai.com/), in **October 2025**. For more information, visit [https://openai.com/chatgpt](https://openai.com/chatgpt).*
