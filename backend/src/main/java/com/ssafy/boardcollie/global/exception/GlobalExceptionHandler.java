package com.ssafy.boardcollie.global.exception;

import com.ssafy.boardcollie.global.mattermost.NotificationManager;
import com.ssafy.boardcollie.global.response.JsonResponse;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final NotificationManager notificationManager;

    @ExceptionHandler(GlobalRuntimeException.class) // business Exception
    public ResponseEntity<?> handlingBusinessException(GlobalRuntimeException e, HttpServletRequest req) {
        log.warn(e.getMessage());
        sendToMatterMost(e, req);
        return JsonResponse.fail(e.statusCode, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest req) {
        // 검증 실패시 발생.
        sendToMatterMost(e, req);
        return JsonResponse.fail(HttpStatus.BAD_REQUEST, "잘못된 값입니다.");
    }

    // HttpMessageNotReadableException -> body의 타입이 잘못됨
    // HttpRequestMethodNotSupportedException-> method 지원 x
    @ExceptionHandler({ HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<?> handleDateTimeFormatException(HttpMessageNotReadableException e, HttpServletRequest req) {
        sendToMatterMost(e, req);
        return JsonResponse.fail(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentHandlingException(MethodArgumentNotValidException e, HttpServletRequest req) {
        sendToMatterMost(e, req);
        return JsonResponse.fail(HttpStatus.BAD_REQUEST, "입력값 에러");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerException(Exception e, HttpServletRequest req) {
        e.printStackTrace();
        sendToMatterMost(e, req);
        return JsonResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
    }

    private void sendToMatterMost(Exception e, HttpServletRequest request) {
        notificationManager.sendNotification(e, request.getRequestURI(), getParams(request));
    }

    private String getParams(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            params.append("- ").append(key).append(" : ").append(request.getParameter(key)).append("\n");
        }
        return params.toString();
    }

}
