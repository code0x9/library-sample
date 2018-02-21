package com.mark.library.ext

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun LocalDate.toDate(): Date =
        Date.from(
                this.atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
