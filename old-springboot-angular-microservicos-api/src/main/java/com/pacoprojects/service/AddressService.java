package com.pacoprojects.service;

import com.pacoprojects.model.AddressResponse;
import com.pacoprojects.util.RetrofitInitializer;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@Service
public class AddressService{

    private final RetrofitInitializer retrofitInitializer;

    // IMPORTANT: Metodo consulta CEP usando biblioteca do Java - Sync
    public ResponseEntity<AddressResponse> getAddress(String cep) {

        String urlConsulta = "https://viacep.com.br/ws/" + cep + "/json/";

        try {

            URL url = new URL(urlConsulta);

            byte[] bytes = url.openConnection().getInputStream().readAllBytes();

            String jSON = new String(bytes, StandardCharsets.UTF_8).replaceAll("\\n", "");

            AddressResponse addressResponse = new Gson().fromJson(jSON, AddressResponse.class);

            if (addressResponse.isErro()) {
                throw new IllegalArgumentException();
            }

            return ResponseEntity.ok(addressResponse);

        } catch (Exception exception) {
            if (exception instanceof IOException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP invalido", exception);
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP nao encontrado", exception);
        }
    }

    // IMPORTANT: Metodo consulta CEP usando -  Retrofit Sync
    public ResponseEntity<AddressResponse> getAddress2(String cep) {

        try {
            Response<AddressResponse> response = retrofitInitializer.testAPI().getAddressRetrofit(cep).execute();

            if (response.isSuccessful()) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().isErro()) {
                        throw new IllegalArgumentException();
                    }
                    return ResponseEntity.ok(response.body());
                } else {
                    System.out.println(response.errorBody());
                    throw new IllegalArgumentException();
                }
            } else {
                System.out.println(response.errorBody());
                throw new IllegalStateException();
            }

        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP nao encontrado", e);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP invalido", e);
        }
    }

    // TODO: Codigo com problema pois a chamada eh Async
    public ResponseEntity<AddressResponse> getAddress3(String cep) {
        RetrofitInterface retrofitInterface = new RetrofitInterface() {
            @Override
            public void onSucess(AddressResponse addressResponse) {
                ResponseEntity.ok(addressResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                if (throwable instanceof IllegalArgumentException) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP nao encontrado", throwable);
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP invalido", throwable);
            }

        };

        retrofitInitializer.testAPI().getAddressRetrofit(cep).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AddressResponse> call,@NonNull Response<AddressResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().isErro()) {
                        retrofitInterface.onError(new IllegalArgumentException());
                    }
                    retrofitInterface.onSucess(response.body());

                } else {
                    retrofitInterface.onError(new IllegalArgumentException());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddressResponse> call,@NonNull Throwable throwable) {
                retrofitInterface.onError(new IllegalStateException());
            }
        });
        return null;
    }

    public interface RetrofitInterface {
        void onSucess(AddressResponse addressResponse);
        void onError(Throwable throwable);
    }
}

