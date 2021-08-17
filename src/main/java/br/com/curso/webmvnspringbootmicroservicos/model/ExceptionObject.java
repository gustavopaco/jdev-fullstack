package br.com.curso.webmvnspringbootmicroservicos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
public class ExceptionObject implements Serializable {

    private String erro;
    private String codigo;
}
