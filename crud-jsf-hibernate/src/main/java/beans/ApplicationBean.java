package beans;

import dao.GenericDAO;
import model.Framework;
import repository.IDaoUsuario;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named(value = "applicationBean")
public class ApplicationBean implements Serializable {

    private List<Framework> frameworks = new ArrayList<>();

    private List<SelectItem> selectItems = new ArrayList<>();

    @Inject
    private GenericDAO genericDAO;

    @Inject
    private IDaoUsuario iDaoUsuario;

    public List<Framework> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<Framework> frameworks) {
        this.frameworks = frameworks;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    @SuppressWarnings(value = "unchecked")
    @PostConstruct
    public void init() {
        frameworks = genericDAO.getEntities(Framework.class);
        selectItems = iDaoUsuario.getStates();
    }
}
