package com.tong.notification.mvc;

import com.tong.notification.Exception.SlackNotificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static org.springframework.http.HttpStatus.FAILED_DEPENDENCY;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Map<String, HttpStatus> SLACK_MESSAGE_TO_HTTP_STATUS_MAP =
            Map.of("CHANNEL_NOT_FOUND", HttpStatus.BAD_REQUEST,
                    "NO_TEXT", HttpStatus.BAD_REQUEST,
                    "NOT_AUTHED", HttpStatus.UNAUTHORIZED,
                    "INVALID_AUTH", HttpStatus.UNAUTHORIZED,
                    "NO_PERMISSION", HttpStatus.FORBIDDEN,
                    "RATE_LIMITED", HttpStatus.TOO_MANY_REQUESTS,
                    "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR,
                    "REQUEST_TIMEOUT", HttpStatus.REQUEST_TIMEOUT,
                    "MSG_TOO_LONG", HttpStatus.PAYLOAD_TOO_LARGE);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e) {
        log.error("unknown exception is thrown, to be investigated", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        log.error("The Method Argument is not valid", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SlackNotificationException.class)
    public ResponseEntity<String> handleSlackException(SlackNotificationException slackNotificationException) {

        String slackErrorMessage = slackNotificationException.getSlackErrorMessage();

        if (SLACK_MESSAGE_TO_HTTP_STATUS_MAP.containsKey(slackErrorMessage.toUpperCase())) {
            return ResponseEntity.status(SLACK_MESSAGE_TO_HTTP_STATUS_MAP.get(slackErrorMessage.toUpperCase()))
                    .body(slackErrorMessage);
        }
        return ResponseEntity.status(FAILED_DEPENDENCY).body(slackErrorMessage);
    }
}
