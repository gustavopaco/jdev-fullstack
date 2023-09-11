package service;

import model.Pessoa;

public interface IServiceCall {

    void getService(Pessoa pessoa, String uiComponent) throws Exception;
}
