package converter;

import model.Cidades;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import java.io.Serializable;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable {

    @Override /* Retorna um objeto Estado a partir do seu codigo - EXECUTADO QUANDO FOR SALVAR, DA TELA PARA O SERVIDOR */
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String codigoCidade) {

        EntityManager entityManager = CDI.current().select(EntityManager.class).get();

        Cidades cidades = (Cidades) entityManager.find(Cidades.class, Long.parseLong(codigoCidade));

        return cidades;
    }

    @Override /* Retorna apenas o codigo do Estado em String  - EXECUTADO DO SERVIDOR PARA TELA */
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object cidade) {

        if (cidade == null) {
            return null;
        }

        if (cidade instanceof Cidades) {

            Cidades cidades = (Cidades) cidade;

            return cidades.getId().toString();
        } else {

            return cidade.toString();
        }
    }
}
