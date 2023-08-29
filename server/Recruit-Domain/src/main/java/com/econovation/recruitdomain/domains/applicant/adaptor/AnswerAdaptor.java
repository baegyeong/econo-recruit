package com.econovation.recruitdomain.domains.applicant.adaptor;

import com.econovation.recruitcommon.annotation.Adaptor;
import com.econovation.recruitdomain.domains.applicant.domain.Answer;
import com.econovation.recruitdomain.domains.applicant.domain.AnswerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class AnswerAdaptor {
    private final AnswerRepository answerRepository;

    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    public void saveAll(List<Answer> answers) {
        answerRepository.saveAll(answers);
    }

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    public List<Answer> findByAnswerIds(List<String> applicantIds) {
        return answerRepository.findByApplicantIdIn(applicantIds);
    }

    public List<Answer> findByAnswerId(String applicantId) {
        return answerRepository.findByApplicantId(applicantId);
    }
}
