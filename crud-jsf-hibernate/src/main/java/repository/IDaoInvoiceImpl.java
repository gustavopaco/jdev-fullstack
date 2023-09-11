package repository;

import model.Invoice;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

@RequestScoped
@Named
public class IDaoInvoiceImpl implements IDaoInvoice{

    @Inject
    private EntityManager entityManager;

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Invoice> getInvoicesByUserID(Long id_usuario) {

        return (List<Invoice>) entityManager
                .createQuery("select i from Invoice i where i.usuario.id_usuario =:user_id order by i.id_invoice")
                .setParameter("user_id",id_usuario).getResultList();
    }
}
