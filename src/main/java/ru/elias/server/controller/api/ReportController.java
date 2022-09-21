package ru.elias.server.controller.api;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.util.Map;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.server.dto.report.ReportFormat;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.service.report.BaseReportService;
import ru.elias.server.util.ApiPathConstants;

@Slf4j
@RestController
@RequestMapping(path = ApiPathConstants.API_V_1 + ApiPathConstants.REPORT)
@RequiredArgsConstructor
@Tag(name = "Reports")
public class ReportController {

    private final Map<String, BaseReportService> reports;

    @GetMapping(value = ApiPathConstants.JOKE_BY_CATEGORY_REPORT)
    public void getJokesByCategoriesReport(@Parameter(description = "Param (category name)")
                                           String param,
                                           @Parameter(description = "Report format (PDF, XLSX, DOCX)")
                                           @NotNull ReportFormat format,
                                           HttpServletResponse response) throws IOException {
        generateReport(ApiPathConstants.JOKE_BY_CATEGORY_REPORT, param, format, response);
    }

    private void generateReport(String reportName,
                                Object params,
                                ReportFormat format,
                                HttpServletResponse response) {
        if (reports.containsKey(reportName)) {
            reports.get(reportName).generateReport(params, format, response, reportName);
        } else {
            throw new BusinessException(ErrorType.UNSUCCESSFUL_REPORT_EXPORT);
        }
    }

}
