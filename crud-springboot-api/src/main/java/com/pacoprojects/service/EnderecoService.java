package com.pacoprojects.service;

import com.pacoprojects.api.BasicApi;
import com.pacoprojects.api.retrofit.RetrofitInitializer;
import com.pacoprojects.dto.EnderecoDTO;
import com.pacoprojects.model.EnderecoAPIResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Response;

import java.io.IOException;

@Service
@AllArgsConstructor
public class EnderecoService {

    private final BasicApi basicApi;
    private final RetrofitInitializer viaCepApi;

    public ResponseEntity<?> getEndereco(String cep) {

        if (cep != null && !cep.isBlank()) {

            cep = cep.replace("-", "");

            try {
                EnderecoAPIResponse apiResponse = retrofitAddress(cep);
//                EnderecoAPIResponse apiResponse = basicApi.consumeBasicApi(cep);

                return ResponseEntity.ok(new EnderecoDTO(apiResponse));
            } catch (Exception e) {
                if (e.getCause() instanceof IOException) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP invalido", e);
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cep Invalido ou servico fora do ar");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cep Invalido");
    }

    public EnderecoAPIResponse retrofitAddress(String cep) {

        try {
            Response<EnderecoAPIResponse> apiResponse = viaCepApi.generateRetrofitApi().getAddress(cep).execute();

            if (apiResponse.isSuccessful()) {
                if (apiResponse.code() == 200) {
                    if (apiResponse.body() != null && !apiResponse.body().isErro()) {
                        return apiResponse.body();
                    }
                    System.out.println(apiResponse.errorBody());
                    throw new IllegalArgumentException();
                } else {
                    System.out.println(apiResponse.errorBody());
                    throw new IllegalArgumentException();
                }
            } else {
                System.out.println(apiResponse.errorBody());
                throw new IllegalStateException();
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
