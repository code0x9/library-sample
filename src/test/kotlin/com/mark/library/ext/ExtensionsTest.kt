package com.mark.library.ext

import com.google.common.truth.Truth
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*

class ExtensionsTest {
    @Test
    fun `LocalDate_toDate() converts java_time_LocalDate to java_util_Date`() {
        val src = LocalDate.now()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val expected = calendar.time
        val actual = src.toDate()

        Truth.assertThat(actual.toString())
                .isEqualTo(expected.toString())
    }
}
