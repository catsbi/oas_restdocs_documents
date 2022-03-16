package me.hansol.oas_restdocs_documents.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 에러 메세지
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
	private final String status;
	private final String message;
}