package com.fylora.pomodorotimer.domain.use_case

import com.fylora.pomodorotimer.core.Globals
import java.time.LocalTime

class CalculateSessions {
    operator fun invoke(workTime: LocalTime): Double {
        val workTimeInMinutes = workTime.hour * 60 + workTime.minute
        return workTimeInMinutes.toDouble() / Globals.sessionLength
    }
}