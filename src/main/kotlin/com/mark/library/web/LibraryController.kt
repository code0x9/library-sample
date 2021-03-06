package com.mark.library.web

import com.mark.library.domain.Loan
import com.mark.library.service.BatchService
import com.mark.library.service.LoanService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

data class CheckoutRequest(
        val memberId: Long,
        val bookId: Long
)

data class CheckinRequest(
        val memberId: Long,
        val bookId: Long
)

data class BatchRequest(
        val scripts: String
)

@RestController
class LibraryController(
        private val loanService: LoanService,
        private val batchService: BatchService
) {
    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    fun checkOut(@RequestBody req: CheckoutRequest): Loan =
            loanService.checkOut(req.memberId, req.bookId)

    @PostMapping("/checkin")
    @ResponseStatus(HttpStatus.CREATED)
    fun checkIn(@RequestBody req: CheckinRequest): Loan =
            loanService.checkIn(req.memberId, req.bookId)

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    fun batch(@RequestBody req: BatchRequest): List<Int> =
            batchService.execute(req.scripts.split(","))

    @GetMapping("/batch/{jobId}")
    fun batch(@PathVariable jobId: String): Boolean =
            batchService.query(jobId.toInt())
}
