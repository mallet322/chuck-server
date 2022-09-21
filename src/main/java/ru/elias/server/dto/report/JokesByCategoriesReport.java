package ru.elias.server.dto.report;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JokesByCategoriesReport {

    private String category;

    private List<JokesByCategoriesReportData> data;

}
