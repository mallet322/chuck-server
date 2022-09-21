package ru.elias.server.dto.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportFormat {

    PDF("application/pdf", ".pdf"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx");

    private final String mediaType;

    private final String extension;

}