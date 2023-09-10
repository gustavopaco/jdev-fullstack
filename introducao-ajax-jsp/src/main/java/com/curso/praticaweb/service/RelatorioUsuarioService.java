package com.curso.praticaweb.service;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class RelatorioUsuarioService implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String FOLDER_RELATORIOS = "/WEB-INF/relatorios";
    private static final String SUBREPORT_DIR = "SUBREPORT_DIR"; // Sub relatorio para trabalhar com relatorio 1 para muitos, 1 relatorio dentro de outro.

    public static byte[] gerarRelatorio(List listaDataBeanCollection, HashMap parametrosRelatorio, String nomeRelatorioJasper, String nomeRelatorioSaida, ServletContext servletContext) throws Exception {
        byte[] bytes;
        // 1- Criar lista de Collection Data Source de Beans que carrega os dados para o Relatorio.
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listaDataBeanCollection);

        String caminhoRelatorio = servletContext.getRealPath(FOLDER_RELATORIOS);
        parametrosRelatorio.put(SUBREPORT_DIR,caminhoRelatorio + File.separator);
        JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio + File.separator + nomeRelatorioJasper + ".jasper", parametrosRelatorio, jrBeanCollectionDataSource);
            bytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return bytes;
    }

}
