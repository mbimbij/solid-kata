package com.codurance.lsp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleShould {
    @Test
    void start_engine() {
        Vehicle vehicle = new TestableVehicle();
        vehicle.startEngine();

        assertThat(vehicle.engineIsStarted())
                .isTrue();
    }

    @Test
    void stop_engine() {
        Vehicle vehicle = new TestableVehicle();
        vehicle.startEngine();
        vehicle.stopEngine();

        assertThat(vehicle.engineIsStarted())
                .isFalse();
    }

    static class TestableVehicle extends Vehicle {
    }
}