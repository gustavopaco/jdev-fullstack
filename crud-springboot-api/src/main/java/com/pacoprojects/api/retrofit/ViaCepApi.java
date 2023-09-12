package com.pacoprojects.api.retrofit;

import com.pacoprojects.model.EnderecoAPIResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepApi {

    @GET(value = "{cep}/json")
    Call<EnderecoAPIResponse> getAddress(@Path(value = "cep") String cep);
}
