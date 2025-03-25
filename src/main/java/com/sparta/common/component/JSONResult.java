package com.sparta.common.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.common.exception.CustomException;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.examples.Example;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.sparta.common.component.CommonExceptionResultMessage.*;


/**
 * <pre>
 *	Ajax 결과
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JSONResult {

	@Schema(hidden = true)
	@JsonIgnore
	private Example holder;

	@Schema(description = "HTTP 상태 코드", example = "200")
	private int status;

	@Schema(description = "응답 코드", example = "A000")
	private String code;

	@Schema(description = "응답 메시지", example = "요청 처리 성공")
	private String message;

	public static JSONResult successBuilder() {
		return JSONResult.builder()
				.status(SUCCESS.getStatus().value())
				.code(SUCCESS.getCode())
				.message(SUCCESS.getMessage())
				.build();
	}

	public static JSONResult failBuilder(Exception e) {
		return JSONResult.builder()
				.status(FAIL.getStatus().value())
				.code(FAIL.getCode())
				.message(FAIL.getMessage())
				.build();
	}

    public static JSONResult failBuilder(CustomException e, String message) {
        return JSONResult.builder()
            .status(e.getResultMessage().getStatus().value())
            .code(e.getResultMessage().getCode())
            .message(message)
            .build();
    }

	public static JSONResult validFailBuilder(MethodArgumentNotValidException e, String message) {
		return JSONResult.builder()
				.status(VALID_FAIL.getStatus().value())
				.code(VALID_FAIL.getCode())
				.message(message)
				.build();
	}


	public static JSONResult dbFailBuilder(DataAccessException e) {
		return JSONResult.builder()
				.status(DB_FAIL.getStatus().value())
				.code(DB_FAIL.getCode())
				.message(DB_FAIL.getMessage())
				.build();
	}

	public static JSONResult notFoundBuilder(Exception e) {
		return JSONResult.builder()
				.status(NOT_FOUND.getStatus().value())
				.code(NOT_FOUND.getCode())
				.message(NOT_FOUND.getMessage())
				.build();
	}
}
