package com.codurance.srp;

import lombok.RequiredArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StatementsPrinter {
    private static final String STATEMENT_HEADER = "DATE | AMOUNT | BALANCE";
    static final String DATE_FORMAT = "dd/MM/yyyy";
    static final String AMOUNT_FORMAT = "#.00";

    private final Console console;

    void print(List<Transaction> transactions) {
        printHeader();
        printTransactions(transactions);
    }

    private void printHeader() {
        printLine(StatementsPrinter.STATEMENT_HEADER);
    }

    private void printLine(String line) {
        this.console.printLine(line);
    }

    private void printTransactions(List<Transaction> transactions) {
        final AtomicInteger balance = new AtomicInteger(0);
        List<String> statementLines = transactions.stream()
                .map(transaction -> statementLine(transaction, balance.addAndGet(transaction.amount())))
                .collect(Collectors.toList())
                .reversed();
        statementLines.forEach(this::printLine);
    }

    private String statementLine(Transaction transaction, int balance) {
        return MessageFormat.format("{0} | {1} | {2}", formatDate(transaction.date()), formatNumber(transaction.amount()), formatNumber(balance));
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateFormatter.format(date);
    }

    private String formatNumber(int amount) {
        DecimalFormat decimalFormat = new DecimalFormat(AMOUNT_FORMAT, DecimalFormatSymbols.getInstance(Locale.UK));
        return decimalFormat.format(amount);
    }
}
