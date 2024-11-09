package com.codurance.srp.problem;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceShould {
    private static final int POSITIVE_AMOUNT = 100;
    private static final int NEGATIVE_AMOUNT = -POSITIVE_AMOUNT;
    private static final LocalDate TODAY = LocalDate.of(2017, 9, 6);

    @Mock
    private Clock clock;

    @Spy
    private TransactionRepositoryInMemory transactionRepository = new TransactionRepositoryInMemory();

    @Mock
    private StatementPrinter printer;

    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        accountService = new AccountService(transactionRepository, clock, printer, 0);
    }

    private void setupClock() {
        given(clock.today()).willReturn(TODAY);
    }

    @Test
    void deposit_amount_into_the_account() {
        setupClock();
        accountService.deposit(POSITIVE_AMOUNT);
        verify(transactionRepository).add(refEq(new Transaction(TODAY, POSITIVE_AMOUNT, POSITIVE_AMOUNT)));
    }

    @Test
    void withdraw_amount_from_the_account() {
        setupClock();
        accountService.withdraw(POSITIVE_AMOUNT);
        verify(transactionRepository).add(refEq(new Transaction(TODAY, NEGATIVE_AMOUNT, -POSITIVE_AMOUNT)));
    }

    @Test
    void call_printer_when_printing() {
        accountService.deposit(1000);
        accountService.withdraw(100);
        accountService.printStatement();
        verify(printer).print(anyList());
    }
}


