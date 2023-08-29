package com.econovation.recruit.api.card.controller;

import static com.econovation.recruitcommon.consts.RecruitStatic.BOARD_SUCCESS_DELETE_MESSAGE;
import static com.econovation.recruitcommon.consts.RecruitStatic.BOARD_SUCCESS_REGISTER_MESSAGE;

import com.econovation.recruit.api.applicant.usecase.AnswerLoadUseCase;
import com.econovation.recruit.api.card.docs.CreateBoardExceptionDocs;
import com.econovation.recruit.api.card.docs.CreateColumnsExceptionDocs;
import com.econovation.recruit.api.card.docs.UpdateBoardExceptionDocs;
import com.econovation.recruit.api.card.usecase.BoardLoadUseCase;
import com.econovation.recruit.api.card.usecase.BoardRegisterUseCase;
import com.econovation.recruit.api.card.usecase.CardLoadUseCase;
import com.econovation.recruit.api.card.usecase.CardRegisterUseCase;
import com.econovation.recruit.api.card.usecase.NavigationUseCase;
import com.econovation.recruitcommon.annotation.ApiErrorExceptionsExample;
import com.econovation.recruitdomain.domains.board.domain.Navigation;
import com.econovation.recruitdomain.domains.board.dto.ColumnsResponseDto;
import com.econovation.recruitdomain.domains.card.dto.CardResponseDto;
import com.econovation.recruitdomain.domains.dto.CreateWorkCardDto;
import com.econovation.recruitdomain.domains.dto.UpdateLocationBoardDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "[2.0]. 칸반보드 API", description = "칸반보드 관련 API")
@Slf4j
public class BoardRestController {
    private final BoardLoadUseCase boardLoadUseCase;
    private final BoardRegisterUseCase boardRecordUseCase;
    private final CardRegisterUseCase cardRegisterUseCase;
    private final CardLoadUseCase cardLoadUseCase;
    private final NavigationUseCase navigationUseCase;
    private final AnswerLoadUseCase answerLoadUseCase;
    // 칸반보드 전체 조회 by navLoc
    @Operation(summary = "업무 칸반보드 생성", description = "업무 칸반(지원자가 아닌) 생성")
    @ApiErrorExceptionsExample(CreateBoardExceptionDocs.class)
    @PostMapping("/boards/work-card")
    public ResponseEntity<String> createWorkBoard(
            @RequestBody CreateWorkCardDto createWorkCardDto) {
        cardRegisterUseCase.saveWorkCard(createWorkCardDto);
        return new ResponseEntity(BOARD_SUCCESS_REGISTER_MESSAGE, HttpStatus.OK);
    }

    @Operation(summary = "지원서 칸반보드 열(세로줄) 생성", description = "지원서 칸반보드 열(세로줄) 생성")
    @ApiErrorExceptionsExample(CreateColumnsExceptionDocs.class)
    @PostMapping("/boards/navigation/{navigation-id}/columns")
    public ResponseEntity<String> createBoardColumn(
            @PathVariable("navigation-id") Integer navigationId, String title) {
        boardRecordUseCase.createColumn(title, navigationId);
        return new ResponseEntity(BOARD_SUCCESS_REGISTER_MESSAGE, HttpStatus.OK);
    }

    @Operation(summary = "지원서 칸반보드 위치 수정")
    @ApiErrorExceptionsExample(UpdateBoardExceptionDocs.class)
    @PostMapping("/boards/location")
    public ResponseEntity<String> updateLocationBoard(
            UpdateLocationBoardDto updateLocationBoardDto) {
        boardRecordUseCase.relocateCard(updateLocationBoardDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "지원자 id로 지원서를 조회합니다.")
    @GetMapping("/applicants/{applicant-id}")
    public ResponseEntity<List<Map<String, String>>> getApplicantById(
            @PathVariable(value = "applicant-id") UUID applicantId) {
        return new ResponseEntity<>(
                answerLoadUseCase.execute(applicantId.toString()), HttpStatus.OK);
    }

    @Operation(summary = "모든 지원자의 지원서를 조회합니다.")
    @GetMapping("/applicants")
    public ResponseEntity<Map<String, Map<String, String>>> getApplicants() {
        return new ResponseEntity<>(answerLoadUseCase.execute(), HttpStatus.OK);
    }

    @Operation(
            summary = "지원서 칸반보드 조회 by navigationId",
            description = "navigationId에 해당하는 모든 칸반을 조회합니다.")
    @GetMapping("/boards/navigation/{navigation-id}")
    public ResponseEntity<List<Map<ColumnsResponseDto, CardResponseDto>>> getBoardByNavigationId(
            @PathVariable("navigation-id") Integer navigationId) {
        return new ResponseEntity<>(cardLoadUseCase.getByNavigationId(navigationId), HttpStatus.OK);
    }

    //    @GetMapping("/boards/cards")
    //    public List<Card> getCardAll() {
    //        return new ArrayList<>(cardLoadUseCase.findAll());
    //    }

    @Operation(summary = "카드 생성", description = "카드를 삭제합니다")
    @PostMapping("/boards/cards/{card-id}/delete")
    public ResponseEntity<String> deleteCard(Long cardId) {
        cardRegisterUseCase.deleteById(cardId);
        return new ResponseEntity<>(BOARD_SUCCESS_DELETE_MESSAGE, HttpStatus.OK);
    }

    //    ---------- Navigation ----------
    @PostMapping("/boards/navigation")
    public ResponseEntity<Navigation> createNavigation(String navTitle) {
        Navigation navigation = navigationUseCase.createNavigation(navTitle);
        return new ResponseEntity<>(navigation, HttpStatus.OK);
    }

    @PostMapping("/boards/navigation/update")
    public ResponseEntity<Navigation> createNavigation(Integer navLoc, String navTitle) {
        Navigation navigation = navigationUseCase.updateNavigationByNavLoc(navLoc, navTitle);
        return new ResponseEntity<>(navigation, HttpStatus.OK);
    }

    @GetMapping("/boards/navigation")
    public Navigation getNavigationByNavLoc(Integer navLoc) {
        return boardLoadUseCase.getNavigationByNavLoc(navLoc);
    }

    @GetMapping("/boards/navigations")
    public List<Navigation> getAllNavigation() {
        List<Navigation> navigations = boardLoadUseCase.getAllNavigation();
        for (Navigation nav : navigations) {
            log.info(nav.getId().toString() + ":" + nav.getNavTitle() + ":" + nav.getNavTitle());
        }
        return navigations;
    }

    @PostMapping("/boards/navigation/delete")
    public ResponseEntity deleteNavigation(Integer navId) {
        navigationUseCase.deleteById(navId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
