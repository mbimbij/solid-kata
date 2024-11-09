package com.codurance.srp.problem;

import java.time.LocalDate;

public record Transaction(LocalDate date, int amount, int balance) {
}
