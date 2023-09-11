package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class GlobalMessage {

    public static void sendMessage(String componente, FacesMessage.Severity severity, String message) {
        FacesContext.getCurrentInstance().addMessage(componente,new FacesMessage(severity,message,null));
    }
}
