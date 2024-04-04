package tech.qizz.core.module.report;

import tech.qizz.core.entity.User;
import tech.qizz.core.module.report.dto.AllParticipantQuestionDetailResponse;
import tech.qizz.core.module.report.dto.AllParticipantQuizResponse;
import tech.qizz.core.module.report.dto.GetAllReportResponse;
import tech.qizz.core.module.report.dto.ReportDetailResponse;

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


    AllParticipantQuizResponse getAllParticipantQuizResponse(Long quizId);

    AllParticipantQuestionDetailResponse getAllParticipantQuestionDetailResponse(Long quizJoinedUserId);
}
