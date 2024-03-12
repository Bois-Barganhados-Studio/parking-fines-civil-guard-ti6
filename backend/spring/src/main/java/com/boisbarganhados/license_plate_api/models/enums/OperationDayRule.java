package com.boisbarganhados.license_plate_api.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationDayRule {
    SEG_SEX("SEGUNDA A SEXTA"),
    SAB("SABADO"),
    DOM("DOMINGO");

    private String dayString;
}