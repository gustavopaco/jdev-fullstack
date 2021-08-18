package br.com.curso.webmvnspringbootmicroservicos.util;

import br.com.curso.webmvnspringbootmicroservicos.model.AddressResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {

    @GET(value = "{cep}/json")
    Call<AddressResponse> getAddressRetrofit(@Path(value = "cep") String cep);

    @GET(value = "{cep}/json")
    Callback<AddressResponse> getAddressRetrofit2(@Path(value = "cep") String cep);
}
