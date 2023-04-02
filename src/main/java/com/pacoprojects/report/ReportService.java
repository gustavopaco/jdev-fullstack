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

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportUtilService reportUtilService;
    private final Base64FileUtil base64FileUtil;

    public ReportResponseDto downloadRelatorio() {
        try {
            byte[] report = reportUtilService.gerarRelatorioPDFJdbcConnection(Constantes.NOME_RELATORIO, new HashMap<>());
            String reportBase64 = base64FileUtil.generateBase64FromByteArray(report, Constantes.DEFAULT_MIME_TYPE);
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
