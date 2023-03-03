package com.dmba.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerBySum {

    @JsonProperty
    private String name;

    @JsonProperty
    private BigDecimal sum;
}
