package br.com.curso.webmvnspringbootmicroservicos.util;

import lombok.AllArgsConstructor;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Component
public class RelatorioGeralGenerico implements Serializable {

//    private static final String DIRETORIO_FOLDER = "br/com/webmvnspringbootthymeleaf/reports";
//    private static final String SUBREPORT_DIR = "SUBREPORT_DIR"; // Sub relatorio para trabalhar com relatorio 1 para muitos, 1 relatorio dentro de outro.
    private static File arquivoTemp = null;
    private static Exporter exporter = null;
    private final JdbcTemplate jdbcTemplate;

    public String gerarRelatorio(List<?> list, HashMap<String, Object> parametros, String nomeRelatorio, String tipoRelatorio) throws Exception {

        /* IMPORTANT: Se o Relatorio vier do Banco de Dados....fazendo a conexao por JDBC*/
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();

        /* IMPORTANT: Se o Relatorio vier de uma lista de dentro do sistema */
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);

//        String pathReportJRXML = resourceLoader.getResource("classpath:" + File.separator + DIRETORIO_FOLDER + File.separator + nomeRelatorio + ".jrxml").getURI().getPath();
//        String caminhoArquivo = resourceLoader.getResource("classpath:" + File.separator + DIRETORIO_FOLDER + File.separator).getURI().getPath() + nomeRelatorio + tipoRelatorio;
//        parametros.put(SUBREPORT_DIR, resourceLoader.getResource("classpath:" + File.separator + DIRETORIO_FOLDER + File.separator).getURI().getPath());
        final InputStream inputStream = this.getClass().getResourceAsStream("/" + nomeRelatorio + ".jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, connection);

        if (tipoRelatorio.equals(".pdf")) {
            exporter = new JRPdfExporter();
        } else if (tipoRelatorio.equals(".xls")) {
            exporter = new JRXlsExporter();
        }

        arquivoTemp = new File(nomeRelatorio + tipoRelatorio);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(arquivoTemp));

        exporter.exportReport();
        arquivoTemp.deleteOnExit();

        connection.close();

        return arquivoTemp.getAbsolutePath();
    }
}
