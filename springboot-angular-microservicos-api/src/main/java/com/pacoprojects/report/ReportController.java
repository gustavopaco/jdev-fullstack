package com.pacoprojects.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<ReportResponseDto> downloadRelatorio() {
        return ResponseEntity.ok(reportService.downloadRelatorio());
    }

    @PostMapping
    public ResponseEntity<ReportResponseDto> advancedReportParams(@RequestBody ReportParamRequestDto paramRequestDto) {
        return ResponseEntity.ok(reportService.advancedReportParams(paramRequestDto));
    }
}
