package com.codurance.srp;

public class AccountService {

    private final TransactionRepository transactionRepository;
    private final Clock clock;
    private final StatementsPrinter printer;

    public AccountService(TransactionRepository transactionRepository, Clock clock, Console console, StatementsPrinter printer) {
        this.transactionRepository = transactionRepository;
        this.clock = clock;
        this.printer = printer;
    }

    public void deposit(int amount) {
        transactionRepository.add(transactionWith(amount));
    }


    public void withdraw(int amount) {
        transactionRepository.add(transactionWith(-amount));
    }

    public void printStatement() {
        printer.print(transactionRepository.all());
    }

    private Transaction transactionWith(int amount) {
        return new Transaction(clock.today(), amount);
    }
}
