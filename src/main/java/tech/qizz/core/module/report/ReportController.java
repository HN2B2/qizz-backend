package tech.qizz.core.module.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.entity.User;
import tech.qizz.core.module.report.dto.GetAllReportResponse;
import tech.qizz.core.module.report.dto.ReportDetailResponse;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<GetAllReportResponse> getAllReportByUser(
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "") String name,
        @RequestParam(required = false) String from,
        @RequestParam(required = false) String to,
        @RequestUser User user
    ) {
        GetAllReportResponse reports = reportService.getAllReport(
            page,
            limit,
            name,
            from,
            to,
            user
        );
        return new ResponseEntity<>(reports, HttpStatus.OK);

    }

    @GetMapping("/{quizId}")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<ReportDetailResponse> getReportDetail(
        @PathVariable Long quizId
    ) {
        try {
            ReportDetailResponse report = reportService.getReportDetail(quizId);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
