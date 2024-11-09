package com.codurance.srp;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class AccountService {

    private final TransactionRepository transactionRepository;
    private final Clock clock;
    private final StatementsPrinter printer;
    private final int initialAmount;

    public AccountService(TransactionRepository transactionRepository, Clock clock, StatementsPrinter printer, int initialAmount) {
        this.transactionRepository = transactionRepository;
        this.clock = clock;
        this.printer = printer;
        this.initialAmount = initialAmount;
    }

    public void deposit(int amount) {
        transactionRepository.add(transactionWith(amount));
    }

    public void withdraw(int amount) {
        transactionRepository.add(transactionWith(-amount));
    }

    public void printStatement() {
        List<Transaction> transactions = transactionRepository.all();
        AtomicInteger balance = new AtomicInteger(initialAmount);
        List<StatementTransaction> statementTransactions = transactions.stream()
                .map(toStatementTransaction(balance))
                .toList();
        printer.print(statementTransactions);
    }

    private static Function<Transaction, StatementTransaction> toStatementTransaction(AtomicInteger balance) {
        return transaction -> new StatementTransaction(transaction.date(),
                transaction.amount(),
                balance.addAndGet(transaction.amount()));
    }

    private Transaction transactionWith(int amount) {
        return new Transaction(clock.today(), amount);
    }
}
