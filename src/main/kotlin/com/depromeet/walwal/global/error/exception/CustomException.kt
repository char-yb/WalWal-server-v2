package com.depromeet.walwal.global.error.exception

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
