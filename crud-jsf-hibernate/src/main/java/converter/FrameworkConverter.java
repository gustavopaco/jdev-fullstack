package converter;

import connection.SingleHibernateEM;
import model.Framework;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;

@FacesConverter(forClass = Framework.class, value = "frameworkConverter")
public class FrameworkConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String framework) {

        EntityManager entityManager = CDI.current().select(EntityManager.class).get();

        Framework fr = (Framework) entityManager.find(Framework.class,Long.parseLong(framework));
        return fr;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object framework) {

        if (framework == null){
            return null;
        }

        if (framework instanceof Framework){

            Framework fr = (Framework) framework;

            return fr.getId_framework().toString();
        }else{

            return framework.toString();
        }
    }
}
