package br.com.curso.webmvnspringbootmicroservicos.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ChartDTO {

    private List<String> nome;
    private List<BigDecimal> salario;
}
