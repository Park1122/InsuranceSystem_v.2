package system.insurance.backend.dto

import java.time.LocalDate

data class TestDTO(
        val data: String? = "No Data",
        val date: LocalDate = LocalDate.now()
)
