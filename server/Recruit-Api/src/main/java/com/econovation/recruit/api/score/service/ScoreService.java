package com.econovation.recruit.api.score.service;

import static com.econovation.recruitcommon.consts.RecruitStatic.CRETERIA_SET;

import com.econovation.recruit.api.config.security.SecurityUtils;
import com.econovation.recruit.api.score.usecase.ScoreUseCase;
import com.econovation.recruitdomain.domains.dto.CreateScoreDto;
import com.econovation.recruitdomain.domains.dto.ScoreAverageDto;
import com.econovation.recruitdomain.domains.dto.ScoreVo;
import com.econovation.recruitdomain.domains.interviewer.domain.Interviewer;
import com.econovation.recruitdomain.domains.score.domain.Score;
import com.econovation.recruitdomain.domains.score.exception.ScoreInvalidFieldException;
import com.econovation.recruitdomain.out.InterviewerLoadPort;
import com.econovation.recruitdomain.out.ScoreLoadPort;
import com.econovation.recruitdomain.out.ScoreRecordPort;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScoreService implements ScoreUseCase {
    private final ScoreRecordPort scoreRecordPort;
    private final ScoreLoadPort scoreLoadPort;
    private final InterviewerLoadPort interviewerLoadPort;

    @Override
    public void createScore(CreateScoreDto scoreDto) {
        Long idpId = SecurityUtils.getCurrentUserId();
        List<String> inputCreteria =
                scoreDto.getScoreVo().stream()
                        .map(scoreVo -> scoreVo.getCreteria())
                        .collect(Collectors.toList());
        if (!CRETERIA_SET.containsAll(inputCreteria)) throw ScoreInvalidFieldException.EXCEPTION;
        List<Score> scores =
                scoreDto.getScoreVo().stream()
                        .map(
                                scoreVo ->
                                        Score.builder()
                                                .applicantId(scoreDto.getApplicantId())
                                                .score(scoreVo.getScore())
                                                .criteria(scoreVo.getCreteria())
                                                .idpId(idpId)
                                                .build())
                        .collect(java.util.stream.Collectors.toList());
        scoreRecordPort.save(scores);
    }

    @Override
    @Transactional
    public void updateScore(CreateScoreDto scoreDto) {
        Long idpId = SecurityUtils.getCurrentUserId();
        Map<String, Float> scoreVoMap =
                scoreDto.getScoreVo().stream()
                        .collect(Collectors.toMap(ScoreVo::getCreteria, ScoreVo::getScore));

        scoreLoadPort.findByApplicantId(scoreDto.getApplicantId()).stream()
                .filter(
                        score ->
                                CRETERIA_SET.contains(score.getCriteria())
                                        && score.getIdpId().equals(idpId))
                .forEach(
                        score -> {
                            Float matchingScore = scoreVoMap.get(score.getCriteria());
                            if (matchingScore != null) {
                                score.updateScore(matchingScore);
                            }
                        });
    }

    @Override
    public Map<String, List<ScoreVo>> getByApplicantId(String applicantId) {
        List<Score> scores = scoreLoadPort.findByApplicantId(applicantId);
        Map<Long, String> interviewers = getAssociatedMapWithIdpIdWithName(scores);
        Map<String, List<ScoreVo>> result =
                scores.stream()
                        .filter(score -> CRETERIA_SET.contains(score.getCriteria()))
                        .collect(Collectors.groupingBy(score -> interviewers.get(score.getIdpId())))
                        .entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry ->
                                                entry.getValue().stream()
                                                        .map(
                                                                score ->
                                                                        ScoreVo.builder()
                                                                                .score(
                                                                                        score
                                                                                                .getScore())
                                                                                .creteria(
                                                                                        score
                                                                                                .getCriteria())
                                                                                .build())
                                                        .collect(Collectors.toList())));
        // Calculate average score
        List<ScoreVo> averageScores = calculateAverageScore(result);
        result.put("average", averageScores);

        return result;
    }

    @Override
    public ScoreAverageDto getApplicantScoreWithAverage(String applicantId) {
        Map<String, List<ScoreVo>> byApplicantId = getByApplicantId(applicantId);
        List<ScoreVo> average = byApplicantId.get("average");

        // totalAverage = average.stream().map(ScoreVo::getScore).reduce(0, Integer::sum) / 4;
        Float averageScore =
                average.stream().map(ScoreVo::getScore).reduce(0f, Float::sum)
                        / CRETERIA_SET.size();
        return ScoreAverageDto.of(averageScore, byApplicantId);
    }

    private List<ScoreVo> calculateAverageScore(Map<String, List<ScoreVo>> result) {
        List<ScoreVo> averageScores = new LinkedList<>();

        for (String criteria : CRETERIA_SET) {
            Double average =
                    result.values().stream()
                            .flatMap(Collection::stream)
                            .filter(scoreVo -> criteria.equals(scoreVo.getCreteria()))
                            .mapToDouble(ScoreVo::getScore)
                            .average()
                            .orElse(0);

            averageScores.add(
                    ScoreVo.builder().score(average.floatValue()).creteria(criteria).build());
        }

        return averageScores;
    }

    @NotNull
    private Map<Long, String> getAssociatedMapWithIdpIdWithName(List<Score> scores) {
        return interviewerLoadPort
                .loadInterviewerByIdpIds(
                        scores.stream().map(Score::getIdpId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(Interviewer::getId, Interviewer::getName));
    }
}
