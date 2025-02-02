package com.api.shop.demo.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardCredit {
    private String nameCredit;
    private String numberCredit;
    private LocalDate validate;
    private String cvc;
}
