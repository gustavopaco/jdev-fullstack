package com.pacoprojects.report;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ReportParamRequestDto(

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
        LocalDate initDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
        LocalDate endDate
) {
}
