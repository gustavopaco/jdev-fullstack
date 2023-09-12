package util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.MessageFormat;

@FacesValidator(value = "genericCheckBoxValidator")
public class GenericCheckBoxValidator implements Validator
{

    private static final String ERROR_WRONG_COMPONENT = "Wrong component type, component must be UISelectBoolean.";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        if (!(component instanceof UISelectBoolean))
        {
            throw new IllegalArgumentException(String.format(ERROR_WRONG_COMPONENT, component.getClass().getName()));
        }

        if (Boolean.FALSE.equals(value))
        {
            String requiredMessage = ((UIInput) component).getRequiredMessage();

            if (requiredMessage == null)
            {
                Object label = component.getAttributes().get("label");
                if (label == null || (label instanceof String && ((String) label).length() == 0))
                    label = component.getValueExpression("label");

                if (label == null)
                    label = component.getClientId(context);

                requiredMessage = MessageFormat.format(UIInput.REQUIRED_MESSAGE_ID, label);
            }

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessage, requiredMessage));
        }
    }

}
