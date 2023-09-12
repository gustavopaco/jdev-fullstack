package repository;

import model.Invoice;

import java.util.List;

public interface IDaoInvoice {

    List<Invoice> getInvoicesByUserID(Long id_usuario);
}
