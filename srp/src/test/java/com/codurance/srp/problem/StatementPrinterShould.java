package com.codurance.srp.problem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class StatementPrinterShould {

    @Mock
    private Console console;

    @InjectMocks
    private StatementPrinter printer;

    private static final List<Transaction> TRANSACTIONS = Arrays.asList(
            new Transaction(LocalDate.of(2014, 4, 1), 1000,1000),
            new Transaction(LocalDate.of(2014, 4, 2), -100, 900),
            new Transaction(LocalDate.of(2014, 4, 10), 500, 1400)
    );

    @Test
    void print_statement() {
        printer.print(TRANSACTIONS);
        InOrder inOrder = inOrder(console);
        inOrder.verify(console).printLine("DATE | AMOUNT | BALANCE");
        inOrder.verify(console).printLine("10/04/2014 | 500.00 | 1400.00");
        inOrder.verify(console).printLine("02/04/2014 | -100.00 | 900.00");
        inOrder.verify(console).printLine("01/04/2014 | 1000.00 | 1000.00");
    }
}
