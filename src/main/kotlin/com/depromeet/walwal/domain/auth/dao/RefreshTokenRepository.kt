package com.depromeet.walwal.domain.auth.dao

import com.depromeet.walwal.domain.auth.domain.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, Long>
