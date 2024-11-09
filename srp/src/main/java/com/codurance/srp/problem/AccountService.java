package com.codurance.srp.problem;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountService {

    private final TransactionRepository transactionRepository;
    private final Clock clock;
    private final StatementPrinter printer;
    private final AtomicInteger amount;

    public AccountService(TransactionRepository transactionRepository, Clock clock, StatementPrinter printer, int amount) {
        this.transactionRepository = transactionRepository;
        this.clock = clock;
        this.printer = printer;
        this.amount = new AtomicInteger(amount);
    }

    public void deposit(int amount) {
        transactionRepository.add(transactionWith(amount));
    }

    public void withdraw(int amount) {
        transactionRepository.add(transactionWith(-amount));
    }

    public void printStatement() {
        List<Transaction> transactions = transactionRepository.all();
        printer.print(transactions);
    }

    private Transaction transactionWith(int amount) {
        return new Transaction(clock.today(), amount, this.amount.addAndGet(amount));
    }
}
