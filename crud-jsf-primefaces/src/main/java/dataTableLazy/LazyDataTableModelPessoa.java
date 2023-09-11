package dataTableLazy;

import dao.GenericDao;
import model.Pessoa;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ViewScoped
@Named
public class LazyDataTableModelPessoa implements Serializable {

    @Inject
    private GenericDao genericDao;

    @Inject
    private EntityManager entityManager;

    private List<Pessoa> listaPessoasFiltradas = new ArrayList<>();

    @SuppressWarnings(value = "unchecked")
    public List<Pessoa> listar(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {

        if (map.size() != 0) {
            if (map.containsKey("nome") && !map.get("nome").getOrder().toString().isEmpty()) {
                boolean isNomeAsc = map.get("nome").getOrder().isAscending();
                if (isNomeAsc) {
                    return entityManager.createQuery("select p from Pessoa p order by p.nome asc").setFirstResult(i).setMaxResults(i1).getResultList();
                } else {
                    return entityManager.createQuery("select p from Pessoa p order by p.nome desc ").setFirstResult(i).setMaxResults(i1).getResultList();
                }
            } else if (map.containsKey("email") && !map.get("email").getOrder().toString().isEmpty()) {
                boolean isEmailAsc = map.get("email").getOrder().isAscending();
                if (isEmailAsc) {
                    return entityManager.createQuery("select p from Pessoa p order by p.email asc").setFirstResult(i).setMaxResults(i1).getResultList();
                } else {
                    return entityManager.createQuery("select p from Pessoa p order by p.email desc").setFirstResult(i).setMaxResults(i1).getResultList();
                }
            } else if (map.containsKey("idade") && !map.get("idade").getOrder().toString().isEmpty()) {
                boolean isIdadeAsc = map.get("idade").getOrder().isAscending();
                if (isIdadeAsc) {
                    return entityManager.createQuery("select p from Pessoa p order by p.idade asc").setFirstResult(i).setMaxResults(i1).getResultList();
                } else {
                    return entityManager.createQuery("select p from Pessoa p order by p.idade desc ").setFirstResult(i).setMaxResults(i1).getResultList();
                }
            } else if (map.containsKey("id") && !map.get("id").getOrder().toString().isEmpty()) {
                    boolean isIDAsc = map.get("id").getOrder().isAscending();
                    if (isIDAsc) {
                        return entityManager.createQuery("select p from Pessoa p order by p.id asc").setFirstResult(i).setMaxResults(i1).getResultList();
                    } else {
                        return entityManager.createQuery("select p from Pessoa p order by p.id desc").setFirstResult(i).setMaxResults(i1).getResultList();
                    }
            }
        }

        if (map1.size() != 0) {
            if (map1.containsKey("globalFilter") && !((String)map1.get("globalFilter").getFilterValue()).isEmpty()) {
                String filterText = map1.get("globalFilter").getFilterValue().toString();
                int filterInt = getIntegerFilter(filterText);
                filterText = "%" + filterText.toLowerCase() + "%";
                return entityManager.createQuery("select p from Pessoa p where lower(p.nome) like:valorPessoa or lower(p.email) like:valorPessoa or p.idade =:idadePessoa")
                        .setParameter("valorPessoa", filterText)
                        .setParameter("idadePessoa", filterInt)
                        .setFirstResult(i)
                        .setMaxResults(i1)
                        .getResultList();
            } else if (map1.containsKey("id") && !((String)map1.get("id").getFilterValue()).isEmpty()) {
                String filterID = map1.get("id").getFilterValue().toString();
                Long pid = Long.parseLong(filterID);
                return entityManager.createQuery("select p from Pessoa p where p.id=:pid")
                        .setParameter("pid", pid)
                        .setFirstResult(i)
                        .setMaxResults(i1)
                        .getResultList();
            }
        }

        return entityManager.createQuery("select p from Pessoa p").setFirstResult(i).setMaxResults(i1).getResultList();
    }

    private int getIntegerFilter(String filterText) {
        try {
            return Integer.parseInt(filterText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Integer getTotalRegistros() {
        return Integer.parseInt(entityManager.createQuery("select count(p.id) from Pessoa p").getSingleResult().toString());
    }

    public List<Pessoa> getListaPessoasFiltradas() {
        return listaPessoasFiltradas;
    }

    public void setListaPessoasFiltradas(List<Pessoa> listaPessoasFiltradas) {
        this.listaPessoasFiltradas = listaPessoasFiltradas;
    }
}

//    @SuppressWarnings(value = "unchecked")
//    @Override
//    public List<Pessoa> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
//
//        listPessoas = entityManager.createQuery("select p from Pessoa p").setFirstResult(first).setMaxResults(pageSize).getResultList();
//
////        setPageSize(pageSize);
//        int qtdRegistros = Integer.parseInt(entityManager.createQuery("select count(p.id) from Pessoa p").getSingleResult().toString());
//        super.setRowCount(qtdRegistros);
//
////        setRowCount((int) rowCount);
//        return listPessoas;
//    }
