package com.pacoprojects.api.retrofit;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
@AllArgsConstructor
public class RetrofitInitializer {

    private static final String URL_VIACEP = "https://viacep.com.br/ws/";

    @Bean
    public Retrofit getRetrofitViaCep() {
        return new Retrofit
                .Builder()
                .baseUrl(URL_VIACEP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ViaCepApi generateRetrofitApi() {
        return getRetrofitViaCep().create(ViaCepApi.class);
    }
}
