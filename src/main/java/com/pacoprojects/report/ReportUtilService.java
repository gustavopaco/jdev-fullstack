package com.pacoprojects.report;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReportUtilService implements Serializable {

    private final JdbcTemplate jdbcTemplate;
    private static final String PATH = "/report/";
    private static final String FULL_PATH = "src/main/resources/report/";
    private static final String FILE_JRXML_TYPE = ".jrxml";
    private static final String FILE_JASPER_TYPE = ".jasper";

    public byte[] gerarRelatorioPDFJdbcConnection(String nomeRelatorio, Map<String, Object> parametros) throws SQLException, JRException, IOException {

        // Obtendo conexao com o banco de dados
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();

        return JasperExportManager.exportReportToPdf(genericJasperPrint(nomeRelatorio,parametros,connection, null));
    }

    public byte[] gerarRelatorioPDFDataList(String nomeRelatorio, Map<String, Object> parametros, List<?> dados) throws SQLException, JRException, IOException {

        return JasperExportManager.exportReportToPdf(genericJasperPrint(nomeRelatorio, parametros, null, dados));
    }

    private boolean fileJasperExist(String nomeRelatorio) {
        return new File((FULL_PATH + nomeRelatorio + FILE_JASPER_TYPE)).isFile();
    }

    private JasperPrint genericJasperPrint(String nomeRelatorio, Map<String, Object> parametros, Connection connection, List<?> dados) throws JRException, IOException, SQLException {
        if (connection != null) {
            return fileJasperExist(nomeRelatorio) ? generateJasperPrint(nomeRelatorio, parametros, connection)
                    : generateCompileAndJasperPrint(nomeRelatorio, parametros, connection);
        }
        return fileJasperExist(nomeRelatorio) ? generateJasperPrint(nomeRelatorio, parametros, dados)
                : generateCompileAndJasperPrint(nomeRelatorio, parametros, dados);
    }

    private JasperPrint generateJasperPrint(String nomeRelatorio, Map<String, Object> parametros, Connection connection) throws JRException, IOException, SQLException {
        // Lendo caminho de arquivo Jasper
        File file = new File(FULL_PATH + nomeRelatorio + FILE_JASPER_TYPE);
        // Carregar caminho do arquivo Jasper
        InputStream pathJasperFile = new FileInputStream(file);
        // Gerar relatorio com dados de conexao
        JasperPrint jasperPrint = JasperFillManager.fillReport(pathJasperFile, parametros, connection);
        pathJasperFile.close();
        connection.close();
        return jasperPrint;
    }

    private JasperPrint generateJasperPrint(String nomeRelatorio, Map<String, Object> parametros, List<?> dados) throws JRException, IOException {
        // Lendo caminho de arquivo Jasper
        File file = new File(FULL_PATH + nomeRelatorio + FILE_JASPER_TYPE);
        // Carregar caminho do arquivo Jasper
        InputStream pathJasperFile = new FileInputStream(file);
        // Se o Relatorio vier de uma lista de dentro do sistema
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dados);
        // Gerar relatorio com dados da lista dentro do sistema
        JasperPrint jasperPrint = JasperFillManager.fillReport(pathJasperFile, parametros, jrBeanCollectionDataSource);
        pathJasperFile.close();
        return jasperPrint;
    }

    private JasperPrint generateCompileAndJasperPrint(String nomeRelatorio, Map<String, Object> parametros, Connection connection) throws JRException, IOException, SQLException {
        // Carregar caminho do arquivo Jrxml
        InputStream pathJrxmlFile = this.getClass().getResourceAsStream(PATH + nomeRelatorio + FILE_JRXML_TYPE);
        // Gerando Novo Arquivo Jasper
        JasperReport jasperReport = generateNewFileJasper(nomeRelatorio, pathJrxmlFile);
        // Gerar relatorio com dados de conexao
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, connection);

        connection.close();
        return jasperPrint;
    }

    private JasperPrint generateCompileAndJasperPrint(String nomeRelatorio, Map<String, Object> parametros, List<?> dados) throws JRException, IOException, SQLException {
        // Carregar caminho do arquivo Jrxml
        InputStream pathJrxmlFile = this.getClass().getResourceAsStream(PATH + nomeRelatorio + FILE_JRXML_TYPE);
        // Gerando Novo Arquivo Jasper
        JasperReport jasperReport = generateNewFileJasper(nomeRelatorio, pathJrxmlFile);
        // Se o Relatorio vier de uma lista de dentro do sistema
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dados);
        // Gerar relatorio com dados de conexao

        return JasperFillManager.fillReport(jasperReport, parametros, jrBeanCollectionDataSource);
    }

    private JasperReport generateNewFileJasper(String nomeRelatorio, InputStream pathJrxmlFile) throws JRException, IOException {
        // Compila arquivo Jrxml
        JasperReport jasperReport = JasperCompileManager.compileReport(pathJrxmlFile);
        // Gera Arquivo Jasper
        JRSaver.saveObject(jasperReport, (FULL_PATH + nomeRelatorio + FILE_JASPER_TYPE));
        if (pathJrxmlFile != null) {
            pathJrxmlFile.close();
        }
        return jasperReport;
    }
}
