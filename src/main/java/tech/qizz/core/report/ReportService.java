package tech.qizz.core.report;

import java.text.ParseException;
import tech.qizz.core.entity.User;
import tech.qizz.core.report.dto.GetAllReportResponse;

public interface ReportService {

    GetAllReportResponse getAllReport(
        Integer page,
        Integer limit,
        String name,
        String from,
        String to,
        User user
    ) throws ParseException;
}
