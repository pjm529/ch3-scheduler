package com.sparta.common.exception;

import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.JSONResult;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

/**
 * <pre>
 * 	전역 예외 처리기
 * </pre>
 * <pre>
 * <b>History:</b>
 * 		Park Jun Mo, 1.0, 2025-03-19 초기작성
 * </pre>
 *
 * @author Park Jun Mo
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ControllerAdvice
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
@Hidden // Swagger 문서에서 제외
public class ExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object customException(HttpServletRequest request, CustomException e) {
        log.error("Exception URI : " + request.getRequestURI());
        log.error("customException : " + e.getMessage(), e);

        BaseResponse res = new BaseResponse();
        String message = StringUtils.isEmpty(e.getMessage()) ? e.getResultMessage().getMessage() : e.getMessage();

        res.setJsonResult(JSONResult.failBuilder(e, message));
        return ResponseEntity.status(e.getResultMessage().getStatus()).body(res);
    }

    @ExceptionHandler({
            HttpClientErrorException.class,
            MissingServletRequestParameterException.class,
            NoResourceFoundException.class
    })
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Object httpClientErrorException(HttpServletRequest request, Exception e) {
        log.error("Exception URI : " + request.getRequestURI());
        log.error("NotFound : " + e.getMessage(), e);
        BaseResponse res = new BaseResponse();
        res.setJsonResult(JSONResult.notFoundBuilder(e));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleDataAccessException(HttpServletRequest request, DataAccessException e) {
        log.error("Exception URI : " + request.getRequestURI());
        log.error("DataAccessException : " + e.getMessage(), e);

        BaseResponse res = new BaseResponse();
        res.setJsonResult(JSONResult.dbFailBuilder(e));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException URI : " + request.getRequestURI());
        log.error("MethodArgumentNotValidException : " + e.getMessage(), e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        FieldError fieldError = fieldErrors.get(fieldErrors.size() - 1);  // 가장 첫 번째 에러 필드
        String fieldName = fieldError.getField();   // 필드명
        Object rejectedValue = fieldError.getRejectedValue();   // 입력값

        BaseResponse res = new BaseResponse();
        res.setJsonResult(JSONResult.validFailBuilder(e, fieldName + " 필드의 입력값 [ " + rejectedValue + " ]이 유효하지 않습니다."));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exception(HttpServletRequest request, Exception e) {
        log.error("Exception URI : " + request.getRequestURI());
        log.error("Exception : " + e.getMessage(), e);
        BaseResponse res = new BaseResponse();
        res.setJsonResult(JSONResult.failBuilder(e));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }
}
