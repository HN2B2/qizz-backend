package tech.qizz.core.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.User;
import tech.qizz.core.quiz.QuizRepository;
import tech.qizz.core.report.dto.GetAllReportResponse;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final QuizRepository quizRepository;

    @Override
    public GetAllReportResponse getAllReport(
        Integer page,
        Integer limit,
        String name,
        String from,
        String to,
        User user
    ) throws ParseException {
        Pageable pageable = PageRequest.of(page - 1, limit);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = from == null ? dateFormat.parse("2000-01-01") : dateFormat.parse(from);
        Date dateTo = to == null ? new Date() : dateFormat.parse(to);

        Page<Quiz> quizzes = quizRepository.findQuizzesByNameContainingAndCreatedByAndCreatedAtBetween(
            name,
            user,
            dateFrom,
            dateTo,
            pageable
        );
        return GetAllReportResponse.of(quizzes);
    }
}
