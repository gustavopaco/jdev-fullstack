package br.com.webmvnspringbootthymeleaf.service;

import br.com.webmvnspringbootthymeleaf.model.Endereco;
import br.com.webmvnspringbootthymeleaf.model.EnderecoResponse;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class EnderecoService {

    public ResponseEntity<?> buscarCEP(String cep){
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json");
            byte[] bytes =  url.openConnection().getInputStream().readAllBytes();
            String JSONString = new String(bytes, StandardCharsets.UTF_8).replaceAll("\\n","");
            EnderecoResponse enderecoResponse = new Gson().fromJson(JSONString, EnderecoResponse.class);

            if (enderecoResponse.getErro() != null) {
                return new ResponseEntity<>("CEP nao encontrado", HttpStatus.NOT_FOUND);
            } else {
                Endereco endereco = new Endereco();
                endereco.setCep(cep);
                endereco.setLogradouro(enderecoResponse.getLogradouro());
                endereco.setComplemento(enderecoResponse.getComplemento());
                endereco.setBairro(enderecoResponse.getBairro());
                endereco.setCidade(enderecoResponse.getLocalidade());
                endereco.setUf(enderecoResponse.getUf());
                return new ResponseEntity<>(endereco, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("CEP invalido", HttpStatus.BAD_GATEWAY);
        }
    }
}
