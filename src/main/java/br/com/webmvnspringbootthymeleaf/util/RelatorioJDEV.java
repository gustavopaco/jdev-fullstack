package br.com.webmvnspringbootthymeleaf.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RelatorioJDEV implements Serializable {

    @Autowired
    private ResourceLoader resourceLoader;

    public byte[] gerarRelatorio(List<?> list) throws Exception {
        final InputStream in = this.getClass().getResourceAsStream("/relatorio.jrxml");
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);

        JasperReport jasperReport = JasperCompileManager.compileReport(in);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), jrBeanCollectionDataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
