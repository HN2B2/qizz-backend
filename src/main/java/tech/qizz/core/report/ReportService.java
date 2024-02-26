package tech.qizz.core.report;

import tech.qizz.core.entity.User;
import tech.qizz.core.report.dto.GetAllReportResponse;
import tech.qizz.core.report.dto.ReportDetailResponse;

public interface ReportService {

    GetAllReportResponse getAllReport(
        Integer page,
        Integer limit,
        String name,
        String from,
        String to,
        User user
    );

    ReportDetailResponse getReportDetail(Long quizId);
}
