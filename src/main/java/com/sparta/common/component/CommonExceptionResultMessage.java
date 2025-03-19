package com.sparta.common.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * <pre>
 *	요청 처리 결과 메세지
 * </pre>
 * 
 * <pre>
 * <b>History:</b>
 * 		Park Jun Mo, 1.0, 2025-03-19 초기작성
 * </pre>
 *
 * @author Park Jun Mo
 * @version 1.0
 * @since 1.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum CommonExceptionResultMessage {

	/* JSON 결과 */
	SUCCESS(HttpStatus.OK, "A000", "요청 처리 성공"),
	FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "E000", "요청 처리 실패"),
	DB_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "요청 DB 처리 실패"),
	VALID_FAIL(HttpStatus.BAD_REQUEST, "E002", "유효성 검증에 실패하였습니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "E404", "NOT FOUND"),

	UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E999", "알 수 없는 오류");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
