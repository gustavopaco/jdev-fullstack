package converter;

import model.Estados;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import java.io.Serializable;

@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable {

    @Override /* Retorna um objeto Estado a partir do seu codigo - EXECUTADO QUANDO FOR SALVAR, DA TELA PARA O SERVIDOR */
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String codigoEstado) {

        if (codigoEstado.equalsIgnoreCase("select")){
            return null;
        }

        EntityManager entityManager = CDI.current().select(EntityManager.class).get();

        Estados estados = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));

        return estados;
    }

    @Override /* Retorna apenas o codigo do Estado em String  - EXECUTADO DO SERVIDOR PARA TELA */
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object estado) {

        if (estado == null) {
            return null;
        }

        if (estado instanceof Estados) {

            Estados estados = (Estados) estado;

            return estados.getId().toString();
        } else {

            return estado.toString();
        }
    }
}
