package com.pacoprojects.report;

import com.pacoprojects.util.Base64FileUtil;
import com.pacoprojects.util.Constantes;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportUtilService reportUtilService;
    private final Base64FileUtil base64FileUtil;

    public ReportResponseDto downloadRelatorio() {
        return generateReport(Constantes.NOME_RELATORIO_BASICO, Constantes.DEFAULT_MIME_TYPE, new HashMap<>());
    }

    public ReportResponseDto advancedReportParams(ReportParamRequestDto paramRequestDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("DATA_INICIO", paramRequestDto.initDate().toString());
        map.put("DATA_FIM", paramRequestDto.endDate().toString());
        return generateReport(Constantes.NOME_RELATORIO_PARAM, Constantes.DEFAULT_MIME_TYPE, map);
    }

    private ReportResponseDto generateReport(String nomeRelatorio, String mimeType, Map<String, Object> objectMap) {
        try {
            byte[] report = reportUtilService.gerarRelatorioPDFJdbcConnection(nomeRelatorio, objectMap);
            String reportBase64 = base64FileUtil.generateBase64FromByteArray(report, mimeType);
            return ReportResponseDto
                    .builder()
                    .report(reportBase64)
                    .build();
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constantes.REPORT_SQL_EXCEPTION_MESSAGE);
        } catch (JRException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constantes.JR_EXCEPTION_MESSAGE);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constantes.IO_EXCEPTION_MESSAGE);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constantes.INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }
}
