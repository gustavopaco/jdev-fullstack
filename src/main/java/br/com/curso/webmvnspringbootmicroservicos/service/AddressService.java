package br.com.curso.webmvnspringbootmicroservicos.service;

import br.com.curso.webmvnspringbootmicroservicos.model.AddressResponse;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class AddressService {


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
}
