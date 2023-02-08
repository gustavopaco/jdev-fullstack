package com.pacoprojects.service;

import com.pacoprojects.api.BasicApi;
import com.pacoprojects.dto.EnderecoDTO;
import com.pacoprojects.model.Endereco;
import com.pacoprojects.model.EnderecoAPIResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
@AllArgsConstructor
public class EnderecoService {

    private final BasicApi basicApi;

    public ResponseEntity<?> getEndereco(String cep) {

        if (cep != null && !cep.isBlank()) {

            cep = cep.replace("-", "");

            try {
                EnderecoAPIResponse apiResponse = basicApi.consumeBasicApi(cep);

                return ResponseEntity.ok(new EnderecoDTO(apiResponse));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cep Invalido ou servico fora do ar");
            }
        }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cep Invalido");
    }
}
