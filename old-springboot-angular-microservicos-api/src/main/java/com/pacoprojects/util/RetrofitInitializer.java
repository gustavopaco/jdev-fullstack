package com.pacoprojects.util;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@AllArgsConstructor
@Configuration
public class RetrofitInitializer {

        @Bean
        public Retrofit getRetrofit() {
            return new Retrofit.Builder()
                    .baseUrl("https://viacep.com.br/ws/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


    public API testAPI() {
        return getRetrofit().create(API.class);
    }
}

//    private final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//            .addInterceptor(chain -> chain.proceed(chain.request()))
//            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            .connectTimeout(15, TimeUnit.SECONDS)
//            .readTimeout(15, TimeUnit.SECONDS)
//            .writeTimeout(15, TimeUnit.SECONDS)
//            .build();

//    private Retrofit retrofit = new Retrofit.Builder()
//            .client(new OkHttpClient().newBuilder().build())
//            .baseUrl("https://viacep.com.br/ws/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();

//    public static class OkClientClassRetrofit {
//        @Bean
//        public Retrofit getRetrofit() {
//            return new Retrofit.Builder()
//                    .client(new OkHttpClient().newBuilder()
//                            .addInterceptor(chain -> chain.proceed(chain.request()))
//                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                            .connectTimeout(15, TimeUnit.SECONDS)
//                            .readTimeout(15, TimeUnit.SECONDS)
//                            .writeTimeout(15, TimeUnit.SECONDS)
//                            .build())
//                    .baseUrl("https://viacep.com.br/ws/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//    }
