package com.econovation.recruitdomain.domains.applicant.domain;

import com.econovation.recruitdomain.common.aop.domainEvent.Events;
import com.econovation.recruitdomain.common.events.AnswerRegisteredEvent;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Question.class)
    private Question question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "applicant_id", length = 36, nullable = false)
    private UUID applicantId;

    @PostPersist
    public void postPersist() {
        AnswerRegisteredEvent answerRegisteredEvent =
                AnswerRegisteredEvent.builder().applicantId(applicantId).build();
        Events.raise(answerRegisteredEvent);
    }
}
