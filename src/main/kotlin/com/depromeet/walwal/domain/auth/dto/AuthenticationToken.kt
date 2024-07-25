package com.depromeet.walwal.domain.auth.dto

import com.depromeet.walwal.domain.auth.domain.TokenType

class AuthenticationToken(val userId: String, val tokenType: TokenType, val provider: String)
