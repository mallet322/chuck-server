package ru.elias.server.service.report.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.elias.server.dto.report.JokesByCategoriesReport;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.service.JokeService;
import ru.elias.server.service.MessageSourceHelper;
import ru.elias.server.service.report.BaseReportService;
import ru.elias.server.util.ApiPathConstants;
import ru.elias.server.util.DateFormatConstants;

@Log4j2
@Service(value = ApiPathConstants.JOKE_BY_CATEGORY_REPORT)
public class JokesByCategoriesReportServiceImpl extends BaseReportService<String, JokesByCategoriesReport> {

    private static final String JOKES_BY_CATEGORIES_REPORT = "classpath:reports/jokes_by_category_report.jrxml";

    private final JokeService jokeService;

    public JokesByCategoriesReportServiceImpl(ApplicationContext context,
                                              MessageSourceHelper messageSourceHelper,
                                              JokeService jokeService) throws JRException, IOException {
        super(context, messageSourceHelper);
        this.jokeService = jokeService;
    }

    @Override
    protected String getReportTemplateName() {
        return JOKES_BY_CATEGORIES_REPORT;
    }

    @Override
    protected Map<String, Object> fillReportParams(JokesByCategoriesReport reportData) {
        var params = new HashMap<String, Object>();
        params.put("REPORT_DATE", LocalDateTime.now()
                                               .atZone(ZoneId.systemDefault())
                                               .format(DateFormatConstants.DATE_TIME_SEC_FORMATTER));
        params.put("CATEGORY", reportData.getCategory());
        params.put("CURRENT_USER", getCurrentUser());
        params.put("REPORT_DATA", new JRBeanCollectionDataSource(reportData.getData()));
        return params;
    }

    @Override
    public JokesByCategoriesReport getData(String params) {
        var jokes = jokeService.getAllJokesByCategory(params)
                               .orElseThrow(() -> {
                                   var errorType = ErrorType.JOKE_WITH_CATEGORIES_NOT_FOUND;
                                   var msg = messageSourceHelper.getMessage(errorType);
                                   log.error(msg);
                                   throw new BusinessException(errorType, msg);
                               });
        return JokesByCategoriesReport.builder()
                                      .category(params)
                                      .data(jokes).build();
    }

    protected String getCurrentUser() {
        var principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

}
