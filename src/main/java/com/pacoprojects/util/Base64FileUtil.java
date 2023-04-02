package com.pacoprojects.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class Base64FileUtil {

    private static final String PREFIX = "data:";
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    private static final String SUFFIX = ";base64,";
    private static final String MIME_PDF = ".pdf";
    private static final String MIME_PDF_RESULT = "application/pdf";
    private static final String MIME_XLS = ".xls";
    private static final String MIME_XLS_RESULT = "application/vnd.ms-excel";
    private static final String MIME_XLSX = ".xlsx";
    private static final String MIME_XLSX_RESULT = "officedocument.spreadsheetml.sheet";

    public String generateBase64FromByteArray(byte[] reportInByteArray, String mimeType) {

        mimeType = checkMimeType(mimeType);

        return PREFIX + mimeType + SUFFIX + Base64.getEncoder().encodeToString(reportInByteArray);

    }

    private String checkMimeType(String mimeType) {
        return mimeType == null ? DEFAULT_MIME_TYPE
                : switch (mimeType) {
            case MIME_PDF -> MIME_PDF_RESULT;
            case MIME_XLS -> MIME_XLS_RESULT;
            case MIME_XLSX -> MIME_XLSX_RESULT;
            default -> DEFAULT_MIME_TYPE;
        };

    }
}
