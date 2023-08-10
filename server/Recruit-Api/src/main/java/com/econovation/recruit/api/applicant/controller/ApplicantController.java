package com.econovation.recruit.api.applicant.controller;

import com.econovation.recruit.api.applicant.usecase.ApplicantRegisterUseCase;
import com.econovation.recruit.api.applicant.usecase.TimeTableLoadUseCase;
import com.econovation.recruit.api.applicant.usecase.TimeTableRegisterUseCase;
import com.econovation.recruit.api.docs.CreateApplicantExceptionDocs;
import com.econovation.recruitcommon.annotation.ApiErrorExceptionsExample;
import com.econovation.recruitdomain.domains.applicant.dto.BlockRequestDto;
import com.econovation.recruitdomain.domains.applicant.dto.TimeTableDto;
import com.econovation.recruitdomain.domains.timetable.TimeTable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "[1.0]. 지원서 API", description = "지원서 관련 API")
public class ApplicantController {
    private static final String APPLY_SUCCESS_MESSAGE = "성공적으로 지원됐습니다";
    private final ApplicantRegisterUseCase applicantRegisterUseCase;
    private final TimeTableRegisterUseCase timeTableRegisterUseCase;
    private final TimeTableLoadUseCase timeTableLoadUseCase;

    @Operation(summary = "지원자가 지원서를 작성합니다.")
    @ApiErrorExceptionsExample(CreateApplicantExceptionDocs.class)
    @PostMapping("/applicants")
    public ResponseEntity registerApplicant(@RequestBody List<BlockRequestDto> blockElements) {
        applicantRegisterUseCase.execute(blockElements);
        return new ResponseEntity<>(APPLY_SUCCESS_MESSAGE, HttpStatus.OK);
    }

    @Operation(summary = "지원자가 면접 가능 시간을 작성합니다.")
    @ApiErrorExceptionsExample(CreateApplicantExceptionDocs.class)
    @PostMapping("/applicants/{applicant-id}/time-tables")
    public ResponseEntity registerApplicantTimeTable(
            @PathVariable(value = "applicant-id") UUID applicantId,
            @RequestBody List<Integer> startTimes) {
        timeTableRegisterUseCase.execute(applicantId, startTimes);
        return new ResponseEntity<>(APPLY_SUCCESS_MESSAGE, HttpStatus.OK);
    }

    @Operation(summary = "모든 면접 가능 시간을 조회합니다.")
    @GetMapping("/timetables")
    public ResponseEntity<List<TimeTableDto>> getTimeTables() {
        return new ResponseEntity(timeTableLoadUseCase.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "지원자의 면접 가능 시간을 조회합니다.")
    @GetMapping("/applicant/{applicant-id}/timetables")
    public ResponseEntity<List<TimeTable>> getTimeTables(
            @PathVariable(name = "applicant-id") UUID applicantId) {
        List<TimeTable> timeTables = timeTableLoadUseCase.getTimeTableByApplicantId(applicantId);
        return new ResponseEntity(timeTables, HttpStatus.OK);
    }

    @Operation(summary = "면접 가능 시간마다 일치하는 지원자의 정보(희망분야, 이름)를 조회합니다.")
    @GetMapping("/timetables/applicants")
    public ResponseEntity<Map<Integer, List<String>>> getApplicantsByTimeTable() {
        return new ResponseEntity(
                timeTableLoadUseCase.findAllSimpleApplicantWithTimeTable(), HttpStatus.OK);
    }
}
