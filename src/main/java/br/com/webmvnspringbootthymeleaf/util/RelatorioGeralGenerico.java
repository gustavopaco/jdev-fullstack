package br.com.webmvnspringbootthymeleaf.util;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Component
public class RelatorioGeralGenerico implements Serializable {

    private static final String DIRETORIO_FOLDER = "reports";
    private static final String SUBREPORT_DIR = "SUBREPORT_DIR"; // Sub relatorio para trabalhar com relatorio 1 para muitos, 1 relatorio dentro de outro.
    private static File arquivoTemp = null;
    private static Exporter exporter = null;
    private final ResourceLoader resourceLoader;

    @Autowired
    public RelatorioGeralGenerico(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String gerarRelatorio(List<?> list, HashMap<String,Object> parametros, String nomeRelatorio, String tipoRelatorio) throws Exception {

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);

        String pathReportJRXML = resourceLoader.getResource("classpath:" + File.separator + DIRETORIO_FOLDER + File.separator + nomeRelatorio + ".jrxml").getURI().getPath();
        String caminhoArquivo = resourceLoader.getResource("classpath:" + File.separator + DIRETORIO_FOLDER + File.separator).getURI().getPath() + nomeRelatorio + tipoRelatorio;

        parametros.put(SUBREPORT_DIR,resourceLoader.getResource("classpath:" + File.separator + DIRETORIO_FOLDER + File.separator).getURI().getPath());

        JasperReport jasperReport = JasperCompileManager.compileReport(pathReportJRXML);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parametros,jrBeanCollectionDataSource);

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
