package com.depromeet.walwal.domain.missionRecord.dao

import com.depromeet.walwal.domain.missionRecord.domain.MissionRecord
import org.springframework.data.jpa.repository.JpaRepository

interface MissionRecordRepository : JpaRepository<MissionRecord, Long>
