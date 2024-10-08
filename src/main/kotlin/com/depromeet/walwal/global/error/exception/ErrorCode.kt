package com.depromeet.walwal.global.error.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
	val status: HttpStatus,
	val message: String,
) {
	SAMPLE_ERROR(HttpStatus.BAD_REQUEST, "Sample Error Message"),

	// Common
	METHOD_ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "요청 한 값 타입이 잘못되어 binding에 실패하였습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP method 입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류, 관리자에게 문의하세요"),

	// apple client
	APPLE_KEY_CLIENT_FAILED(HttpStatus.BAD_REQUEST, "애플 키 생성에 실패했습니다."),
	APPLE_TOKEN_CLIENT_FAILED(HttpStatus.BAD_REQUEST, "애플 토큰 생성에 실패했습니다."),
	APPLE_PRIVATE_KEY_ENCODING_FAILED(HttpStatus.BAD_REQUEST, "애플 개인키 인코딩에 실패했습니다."),

	// Member
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
	MEMBER_INVALID_NORMAL(HttpStatus.FORBIDDEN, "일반 회원이 아닙니다."),
	MEMBER_SOCIAL_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "소셜 정보를 찾을 수 없습니다."),
	OAUTH_PROVIDER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "지원하지 않는 Oauth Provider입니다."),
	ALREADY_EXISTS_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),

	// Security
	AUTH_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "시큐리티 인증 정보를 찾을 수 없습니다."),
	EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
	MEMBER_ALREADY_REGISTERED(HttpStatus.CONFLICT, "이미 가입된 회원입니다."),
	MEMBER_ALREADY_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
	MEMBER_INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "올바르지 않는 닉네임입니다."),
	MEMBER_ALREADY_DELETED(HttpStatus.NOT_FOUND, "이미 탈퇴한 회원입니다."),
	PASSWORD_NOT_MATCHES(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
	ID_TOKEN_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "ID 토큰 검증에 실패했습니다."),

	// Mission
	MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 미션을 찾을 수 없습니다."),
	MISSION_VISIBILITY_NULL(HttpStatus.BAD_REQUEST, "미션 공개 여부가 null입니다."),
	MISSION_STATUS_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR, "미션 상태 중 매치되지 않는 미션이 있습니다."),

	// MissionRecord
	MISSION_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 미션 기록을 찾을 수 없습니다."),
	MISSION_RECORD_USER_MISMATCH(HttpStatus.FORBIDDEN, "미션을 생성한 유저와 로그인된 계정이 일치하지 않습니다"),
	MISSION_RECORD_DURATION_OVERBALANCE(HttpStatus.BAD_REQUEST, "미션 참여 시간이 지정 된 시간보다 초과하였습니다"),
	MISSION_RECORD_UPLOAD_STATUS_IS_NOT_NONE(
		HttpStatus.BAD_REQUEST,
		"미션 기록의 이미지 업로드 상태가 NONE이 아닙니다.",
	),
	MISSION_RECORD_UPLOAD_STATUS_IS_NOT_PENDING(
		HttpStatus.BAD_REQUEST,
		"미션 기록의 이미지 업로드 상태가 PENDING이 아닙니다.",
	),
	MISSION_RECORD_ALREADY_EXISTS_TODAY(HttpStatus.BAD_REQUEST, "오늘 이미 작성 된 미션 기록이 존재합니다."),
	MISSION_RECORD_DURATION_OVERTIME(HttpStatus.CONFLICT, "가능한 미션 인증 시간이 초과되었습니다."),

	// Image
	IMAGE_KEY_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지 키를 찾을 수 없습니다."),
	IMAGE_FILE_EXTENSION_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지 파일 형식을 찾을 수 없습니다."),

	// Notification
	SELF_SENDING_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "본인에게 메세지를 전송할 수 없습니다."),
	TODAY_COMPLETED_MISSION_SENDING_NOT_ALLOWED(
		HttpStatus.BAD_REQUEST,
		"오늘 미션을 완료한 미션에는 메세지를 전송할 수 없습니다.",
	),
	FINISHED_MISSION_URGING_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "종료된 미션에는 재촉하기를 할 수 없습니다."),
	NOTIFICATION_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 알림 타입을 찾을 수 없습니다."),
}
