package com.pacoprojects.chart;

import java.math.BigDecimal;
import java.util.Objects;

public record ChartDto2(
        String nome,
        BigDecimal salario) {

    public ChartDto2 {
        Objects.requireNonNull(nome, "Nome obrigatório.");
    }
}
