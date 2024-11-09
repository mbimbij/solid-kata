package com.codurance.srp.problem;

import lombok.RequiredArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StatementPrinter {
    private static final String STATEMENT_HEADER = "DATE | AMOUNT | BALANCE";
    static final String DATE_FORMAT = "dd/MM/yyyy";
    static final String AMOUNT_FORMAT = "#.00";

    private final Console console;

    void print(List<Transaction> transactions) {
        printHeader();
        printTransactions(transactions);
    }

    private void printTransactions(List<Transaction> transactions) {
        List<String> statementLines = transactions.stream()
                .map(this::statementLine)
                .collect(Collectors.toList())
                .reversed();
        statementLines.forEach(this::printLine);
    }

    private void printHeader() {
        printLine(StatementPrinter.STATEMENT_HEADER);
    }

    private void printLine(String line) {
        this.console.printLine(line);
    }

    private String statementLine(Transaction transaction) {
        return MessageFormat.format("{0} | {1} | {2}",
                formatDate(transaction.date()),
                formatNumber(transaction.amount()),
                formatNumber(transaction.balance()));
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
