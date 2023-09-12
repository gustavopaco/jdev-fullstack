package com.curso.praticaweb.service;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRExporterContext;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterContext;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.export.annotations.ExporterParameter;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RelatorioService implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String FOLDER_RELATORIOS = "F:\\Java\\JavaProjects\\web-mvn-ajax-jsp\\src\\main\\java\\com\\curso\\praticaweb\\relatorios";
    private static final String SUBREPORT_DIR = "SUBREPORT_DIR"; // Sub relatorio para trabalhar com relatorio 1 para muitos, 1 relatorio dentro de outro.
    private String SEPARATOR = File.separator; // Separador do caminho do relatorio "/" -> Windows e "\" -> para Linux
    private String caminhoArquivoRelatorio = null; // Caminho do relatorio dentro do sistema operacional
//    private Exporter exporter = null;
    private JRExporter exporter = null;
    private String caminhoSubReport_DIR = "";
    private File arquivoGerado = null;


    public String gerarRelatorio(List<?> listaDataBeanCollection, HashMap parametrosRelatorio, String nomeRelatorioJasper, String nomeRelatorioSaida, ServletContext servletContext) throws Exception {

        // 1- Criar lista de Collection Data Source de Beans que carrega os dados para o Relatorio.
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listaDataBeanCollection);

        // 2- Descobrir o caminho do relatorio Jasper(arquivo do relatorio)
        String caminhoRelatorio = FOLDER_RELATORIOS;
        // 3- Montar o arquivo que vai representar o relatorio .jasper
        File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper");

        // 4- Verificar se o caminho do relatorio nao existe ou se o arquivo nao existe no caminho em caso de implantacao em um servidor,
        // porque a estrutura de pastas pode mudar e afetar o codigo...
        if (caminhoRelatorio == null || (caminhoRelatorio != null && caminhoRelatorio.isEmpty()) || !file.exists()) {
            caminhoRelatorio = FOLDER_RELATORIOS;
//            SEPARATOR = "";
        }

        // 5- Caminho para imagens no relatorio
        parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);

        // 6- O caminho completo onde o relatorio compilado eh indicado
        String caminhoArquivosJasper = caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper";

        // 7- Carregamento do Relatorio
        JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivosJasper);

        // 8- Seta os parametros SUBREPORT_DIR com o caminho fisico para SUBREPORT
        caminhoSubReport_DIR = caminhoRelatorio + SEPARATOR;
        parametrosRelatorio.put(SUBREPORT_DIR,caminhoSubReport_DIR);

        // 9- Carregar arquivo compilado na memoria
        JasperPrint relatorio = JasperFillManager.fillReport(relatorioJasper,parametrosRelatorio, jrBeanCollectionDataSource);

        // 10- Definindo tipo do arquivo do relatorio (Definido como PDF)
        exporter = new JRPdfExporter();

        // 11- Setando caminho do relatorio exportado
        caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + ".pdf";

        // 12- Criar novo arquivo exportado
        arquivoGerado = new File(caminhoArquivoRelatorio);

        // 13- Prepara a impressao
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,relatorio);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,arquivoGerado);

//        exporter.setExporterInput(new SimpleExporterInput(relatorio));
//        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(arquivoGerado));
//        SimplePdfExporterConfiguration simplePdfExporterConfiguration = new SimplePdfExporterConfiguration();
//        exporter.setConfiguration(simplePdfExporterConfiguration);

        // 14- Executa a exportacao
        exporter.exportReport();

        // 15- Remove o arquivo do servidor apost ser feito o download
        arquivoGerado.deleteOnExit();

        return caminhoArquivoRelatorio;
    }

}
