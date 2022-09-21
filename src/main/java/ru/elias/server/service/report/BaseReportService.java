package ru.elias.server.service.report;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.elias.server.dto.report.ReportFormat;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.service.MessageSourceHelper;

@Slf4j
public abstract class BaseReportService<P, R> {

    private static final String ATTACHMENT_PREFIX = "attachment; filename=report";

    protected final MessageSourceHelper messageSourceHelper;

    private final JasperReport reportTemplate;

    protected BaseReportService(ApplicationContext context,
                                MessageSourceHelper messageSourceHelper)
            throws JRException, IOException {
        reportTemplate = getJasperReport(context, getReportTemplateName());
        this.messageSourceHelper = messageSourceHelper;
    }

    protected abstract String getReportTemplateName();

    protected abstract Map<String, Object> fillReportParams(R reportData);

    public abstract R getData(P params);

   public void generateReport(P params, ReportFormat format,
                               HttpServletResponse response, String reportName) {
        var dataForOutput = getData(params);
        try (OutputStream os = response.getOutputStream()) {
            putResponseHeaders(format, response);
            var jasperPrint = generateReport(dataForOutput);
            exportReport(jasperPrint, os, format);
        } catch (IOException e) {
            var errorType = ErrorType.UNSUCCESSFUL_REPORT_EXPORT;
            log.warn(messageSourceHelper.getMessage(errorType), e);
            throw new BusinessException(errorType);
        }

    }

    private void generateDocx(OutputStream outputStream, JasperPrint jasperPrint) throws JRException {
        final var exporter = new JRDocxExporter();
        final OutputStreamExporterOutput outputStreamExporterOutput =
                new SimpleOutputStreamExporterOutput(outputStream);
        exporter.setExporterOutput(outputStreamExporterOutput);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        final var config = new SimpleDocxReportConfiguration();
        config.setFramesAsNestedTables(true);
        exporter.setConfiguration(config);
        exporter.exportReport();
    }

    private void generatePdf(OutputStream outputStream, JasperPrint jasperPrint) throws JRException {
        final var exporter = new JRPdfExporter();
        final OutputStreamExporterOutput outputStreamExporterOutput =
                new SimpleOutputStreamExporterOutput(outputStream);
        exporter.setExporterOutput(outputStreamExporterOutput);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.exportReport();
    }

    private void generateXlsx(OutputStream outputStream, JasperPrint jasperPrint) throws JRException {
        final var exporter = new JRXlsxExporter();
        final OutputStreamExporterOutput outputStreamExporterOutput =
                new SimpleOutputStreamExporterOutput(outputStream);
        exporter.setExporterOutput(outputStreamExporterOutput);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.exportReport();
    }

    private JasperPrint throwReportExportException(JRException e) {
        var errorType = ErrorType.UNSUCCESSFUL_REPORT_EXPORT;
        log.warn(messageSourceHelper.getMessage(errorType), e);
        throw new BusinessException(errorType);
    }

    private void exportReport(JasperPrint jasperPrint, OutputStream outputStream, ReportFormat format) {
        try {
            switch (format) {
                case PDF:
                    generatePdf(outputStream, jasperPrint);
                    break;
                case DOCX:
                    generateDocx(outputStream, jasperPrint);
                    break;
                case XLSX:
                    generateXlsx(outputStream, jasperPrint);
                    break;
                default:
                    // empty logic
            }
        } catch (JRException e) {
            throwReportExportException(e);
        }
    }

    private void putResponseHeaders(ReportFormat reportFormat, HttpServletResponse response) {
        response.setContentType(reportFormat.getMediaType());
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT_PREFIX + reportFormat.getExtension());
    }

    protected JasperPrint generateReport(R reportData) {
        return fillReportTemplate(fillReportParams(reportData));
    }

    private JasperPrint fillReportTemplate(Map<String, Object> params) {
        try {
            return JasperFillManager.fillReport(reportTemplate, params, new JREmptyDataSource());
        } catch (JRException e) {
            return throwReportExportException(e);
        }
    }

    protected JasperReport getJasperReport(ApplicationContext context,
                                           String fileName) throws IOException, JRException {
        var reportTemplateFile =
                context.getResource(fileName).getInputStream();
        return JasperCompileManager.compileReport(reportTemplateFile);
    }

    protected String getCurrentUser() {
        var principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

}

