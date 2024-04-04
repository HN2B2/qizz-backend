package tech.qizz.core.module.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.QuestionHistory;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizJoinedUser;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.module.report.dto.AllParticipantQuestionDetailResponse;
import tech.qizz.core.module.report.dto.AllParticipantQuizResponse;
import tech.qizz.core.repository.QuizJoinedUserRepository;
import tech.qizz.core.repository.QuizRepository;
import tech.qizz.core.module.report.dto.GetAllReportResponse;
import tech.qizz.core.module.report.dto.ReportDetailResponse;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final QuizRepository quizRepository;
    private final QuizJoinedUserRepository quizJoinedUserRepository;

    @SneakyThrows
    @Override
    public GetAllReportResponse getAllReport(
        Integer page,
        Integer limit,
        String name,
        String from,
        String to,
        User user
    ) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom;
        try {
            dateFrom = dateFormat.parse(from);
        } catch (Exception e) {
            dateFrom = dateFormat.parse("2000-01-01");
        }

        Date dateTo;
        try {
            dateTo = dateFormat.parse(to);
        } catch (Exception e) {
            dateTo = dateFormat.parse("3000-01-01");
        }

        Page<Quiz> quizzes = quizRepository.findAllByNameContainingAndCreatedByAndCreatedAtBetween(
            name,
            user,
            dateFrom,
            dateTo,
            pageable
        );
        return GetAllReportResponse.of(quizzes);
    }

    @Override
    public ReportDetailResponse getReportDetail(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
        return ReportDetailResponse.of(quiz);
    }
    @Override
    public AllParticipantQuizResponse getAllParticipantQuizResponse(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundException("Quiz not found"));
        return AllParticipantQuizResponse.of(quiz.getQuizJoinedUsers());
    }

    @Override
    public AllParticipantQuestionDetailResponse getAllParticipantQuestionDetailResponse(Long quizJoinedUserId) {
        QuizJoinedUser quizJoinedUser = quizJoinedUserRepository.findById(quizJoinedUserId)
                .orElseThrow(() -> new NotFoundException("Quiz joined user not found"));
        return AllParticipantQuestionDetailResponse.of(quizJoinedUser.getQuestionHistories());
    }
}
