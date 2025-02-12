package com.econovation.recruitdomain.common.aop.domainEvent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnExpression("${ableDomainEvent:true}")
public class EventPublisherAspect implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;
    private ThreadLocal<Boolean> appliedLocal = new ThreadLocal<>();

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object handleEvent(ProceedingJoinPoint joinPoint) throws Throwable {

        Boolean appliedValue = appliedLocal.get();
        boolean nested = false;

        if (appliedValue != null && appliedValue) {
            nested = true;
        } else {
            nested = false;
            appliedLocal.set(Boolean.TRUE);
        }

        if (!nested) Events.setPublisher(publisher);

        try {
            return joinPoint.proceed();
        } finally {
            if (!nested) {
                Events.reset();
                appliedLocal.remove();
            }
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.publisher = eventPublisher;
    }
}
