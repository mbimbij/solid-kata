package com.codurance.srp;

import java.time.LocalDate;

public record StatementTransaction(LocalDate date, int amount, int balance) {
}
