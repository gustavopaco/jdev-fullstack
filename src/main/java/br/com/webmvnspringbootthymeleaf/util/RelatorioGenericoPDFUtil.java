package br.com.webmvnspringbootthymeleaf.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Component
public class RelatorioGenericoPDFUtil implements Serializable {

    private final ResourceLoader resourceLoader;

    @Autowired
    public RelatorioGenericoPDFUtil(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public byte[] gerarRelatorio(List<?> list, String nRelatorio) throws Exception {

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);

        /* Esse jeito tambem funciona para gerar o Relatorio */
//        File file = ResourceUtils.getFile("classpath:" + File.separator + "reports" + File.separator + "relatorio.jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        String path = resourceLoader.getResource("classpath:" + File.separator + "br/com/webmvnspringbootthymeleaf/reports" + File.separator + "relatorio.jrxml").getURI().getPath();

        JasperReport jasperReport = JasperCompileManager.compileReport(path);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), jrBeanCollectionDataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
