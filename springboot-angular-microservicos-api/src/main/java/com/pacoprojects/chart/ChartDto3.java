package com.pacoprojects.chart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class ChartDto3 {

    private String nome;
    private BigDecimal salario;


    public ChartDto3(String nome, BigDecimal salario) {
        Objects.requireNonNull(nome, "Nome obrigatório.");
        this.nome = nome;
        this.salario = salario;
    }
}
