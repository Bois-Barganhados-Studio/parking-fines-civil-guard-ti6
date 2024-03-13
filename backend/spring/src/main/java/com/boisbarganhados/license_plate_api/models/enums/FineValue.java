package com.boisbarganhados.license_plate_api.models.enums;

public enum FineValue {
    LOW(100), MEDIUM(200), HIGH(300);

    private final int value;

    FineValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
