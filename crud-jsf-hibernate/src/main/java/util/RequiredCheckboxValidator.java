package util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "requiredCheckboxValidator")
public class RequiredCheckboxValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        HtmlSelectBooleanCheckbox checkbox = (HtmlSelectBooleanCheckbox) uiComponent;

        if (checkbox.getSubmittedValue().equals(false)) {
            String requiredMessage = ((UIInput) uiComponent).getRequiredMessage();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,requiredMessage,requiredMessage));
        }
    }
}
