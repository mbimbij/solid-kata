package com.codurance.srp;

import java.time.LocalDate;

public record Transaction(LocalDate date, int amount) {
}
