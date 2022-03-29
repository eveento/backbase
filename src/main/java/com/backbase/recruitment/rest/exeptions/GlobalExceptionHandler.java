package com.backbase.recruitment.rest.exeptions;

import com.backbase.recruitment.utils.DateTimeUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nullable;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling {

    private final String DATE = "date";
    private final String VIOLATIONS = "violations";
    private final String MESSAGE = "message";

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                NativeWebRequest request) {
        BindingResult result = exception.getBindingResult();

        Problem problem = Problem.builder()
                .withTitle("Method argument not valid")
                .withStatus(defaultConstraintViolationStatus())
                .with(VIOLATIONS, createViolations(result))
                .build();
        return create(exception, problem, request);
    }

    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (Objects.isNull(entity)) {
            return null;
        }
        Problem problem = entity.getBody();

        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        ProblemBuilder builder = Problem.builder()
                .with(DATE, DateTimeUtils.formatToFullIsoDate(LocalDateTime.now()))
                .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? Problem.DEFAULT_TYPE : problem.getType())
                .withStatus(problem.getStatus())
                .withTitle(problem.getTitle());

        if (problem instanceof ConstraintViolationProblem cvp) {
            builder
                    .with(VIOLATIONS, (cvp).getViolations())
                    .with(MESSAGE, "Data integrity error");
        } else {
            builder
                    .withCause(((DefaultProblem) problem).getCause())
                    .withDetail(problem.getDetail())
                    .withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Problem> handleNoSuchElementException(NoSuchElementException exception, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withTitle("Element not found")
                .withStatus(Status.NOT_FOUND)
                .with(MESSAGE, exception.getMessage())
                .build();
        return create(exception, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConstraintViolationException(DataIntegrityViolationException exception, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withStatus(Status.BAD_REQUEST)
                .with(MESSAGE, exception.getMessage())
                .build();
        return create(exception, problem, request);
    }

}