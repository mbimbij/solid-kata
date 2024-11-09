package com.codurance.srp.problem;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryInMemory implements TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void add(Transaction transaction) {
        this.transactions.add(transaction);
    }

    @Override
    public List<Transaction> all() {
        return transactions;
    }
}
