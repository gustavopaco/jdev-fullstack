package com.pacoprojects.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Component
public class RelatorioJDEV implements Serializable {

    public byte[] gerarRelatorio(List<?> list) throws Exception {
        final InputStream in = this.getClass().getResourceAsStream("/relatorio.jrxml");
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);

        JasperReport jasperReport = JasperCompileManager.compileReport(in);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), jrBeanCollectionDataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
