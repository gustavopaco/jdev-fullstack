package service;

import com.google.gson.Gson;
import model.CepResponse;
import model.Pessoa;
import org.primefaces.context.PrimeFacesContext;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Model
public class IServiceCallImpl implements IServiceCall {

    public void getService(Pessoa pessoa, String uiComponent) {
        try {
            URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");

            byte[] bytes =  url.openConnection().getInputStream().readAllBytes();

            String json = new String(bytes, StandardCharsets.UTF_8).replaceAll("\\n","");

            CepResponse cepResponse = new Gson().fromJson(json,CepResponse.class);

            if (cepResponse.isErro()) {
                invalidarCep(pessoa);
                PrimeFacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cep nao encontrado", null));
            }else {
                pessoa.setLogradouro(cepResponse.getLogradouro());
                pessoa.setComplemento(cepResponse.getComplemento());
                pessoa.setBairro(cepResponse.getBairro());
                pessoa.setLocalidade(cepResponse.getLocalidade());
                pessoa.setUf(cepResponse.getUf());
                pessoa.setIbge(cepResponse.getIbge());
                pessoa.setGia(cepResponse.getGia());
                pessoa.setDdd(cepResponse.getDdd());
                pessoa.setSiafi(cepResponse.getSiafi());
                pessoa.setCepValid(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            invalidarCep(pessoa);
            PrimeFacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cep invalido", null));
        }
    }

    private void invalidarCep(Pessoa pessoa){
        pessoa.setLogradouro(null);
        pessoa.setComplemento(null);
        pessoa.setBairro(null);
        pessoa.setLocalidade(null);
        pessoa.setUf(null);
        pessoa.setIbge(null);
        pessoa.setGia(null);
        pessoa.setDdd(null);
        pessoa.setSiafi(null);
        pessoa.setCepValid(false);
    }
}
