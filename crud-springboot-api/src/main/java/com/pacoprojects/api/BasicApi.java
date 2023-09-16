package com.pacoprojects.api;

import com.google.gson.Gson;
import com.pacoprojects.model.EnderecoAPIResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class BasicApi {

    public EnderecoAPIResponse consumeBasicApi(String cep) throws IOException {

        /* Criando URL a ser consultada*/
        URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");

        /* Obtendo dados da REQUEST
        * openConnection() -> Abre conexao
        * getInputStream().readAllBytes -> Obtem dados da REQUEST e Le tudo*/
        byte[] bytes = url.openConnection().getInputStream().readAllBytes();

        /* Gerando JSON a partir de Byte Array, LIMPANDO todos "\n" da Leitura gerado pelo readAllBytes()*/
        String json = new String(bytes, StandardCharsets.UTF_8).replaceAll("\n","");

        EnderecoAPIResponse apiResponse = new Gson().fromJson(json, EnderecoAPIResponse.class);

        System.out.println(apiResponse);

        return  apiResponse;
    }

    public EnderecoAPIResponse consumeBasicApiAlex(String cep) throws IOException {
        /* Criando URL a ser consultada*/
        URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");

        /* Abrindo Conexao */
        URLConnection connection = url.openConnection();

        /* Obtendo dados da REQUEST */
        InputStream inputStream = connection.getInputStream();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String cepX = "";
        StringBuilder jsonCep = new StringBuilder();

        while ((cepX = reader.readLine()) != null) {
            jsonCep.append(cepX);
        }

        EnderecoAPIResponse apiResponse = new Gson().fromJson(jsonCep.toString(), EnderecoAPIResponse.class);

        System.out.println(apiResponse);

        return apiResponse;
    }
}
