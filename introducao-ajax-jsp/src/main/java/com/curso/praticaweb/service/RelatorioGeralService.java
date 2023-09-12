package com.curso.praticaweb.service;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class RelatorioGeralService implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String DIRETORIO_FOLDER = "/WEB-INF/relatorios";
    private static final String SUBREPORT_DIR = "SUBREPORT_DIR"; // Sub relatorio para trabalhar com relatorio 1 para muitos, 1 relatorio dentro de outro.
    private static File arquivoTemp = null;
    private static Exporter exporter = null;

    public static String gerarRelatorio(List list, HashMap parametros, String nomeRelatorio, ServletContext servletContext, String tipoRelatorio) throws Exception {
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);
        String caminhoRelatorio = servletContext.getRealPath(DIRETORIO_FOLDER);
        String caminhoArquivo = caminhoRelatorio + File.separator + nomeRelatorio + tipoRelatorio;
        parametros.put(SUBREPORT_DIR,caminhoRelatorio + File.separator);
        JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio + File.separator + nomeRelatorio + ".jasper",parametros,jrBeanCollectionDataSource);

        if (tipoRelatorio.equals(".pdf")){
            exporter = new JRPdfExporter();
        }else if (tipoRelatorio.equals(".xls")) {
            exporter = new JRXlsExporter();
        }

        arquivoTemp = new File(caminhoArquivo);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(arquivoTemp));

        exporter.exportReport();
        arquivoTemp.deleteOnExit();

        return caminhoArquivo;
    }
}
