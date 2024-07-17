package com.depromeet.walwal.domain.mission.dao

import com.depromeet.walwal.domain.mission.domain.Mission
import org.springframework.data.jpa.repository.JpaRepository

interface MissionRepository : JpaRepository<Mission, Long>
