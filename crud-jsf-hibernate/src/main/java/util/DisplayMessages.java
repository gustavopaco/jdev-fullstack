package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class DisplayMessages {

    public static void sendMessage(String componentID, FacesMessage.Severity severity, String message ) {

        FacesContext.getCurrentInstance().addMessage(componentID, new FacesMessage(severity, message, null));
    }
}
//        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Attention",message); Eh possivel passar outros parametros alem da menssagem, olhar o construtor
//        FacesMessage facesMessage = new FacesMessage(severity, message,null);
//        FacesContext.getCurrentInstance().addMessage(componentID, facesMessage); // Onde ta null eh possivel ligar a um componente da tela pelo ID
