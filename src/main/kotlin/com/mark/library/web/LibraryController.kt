package com.mark.library.web

import com.mark.library.domain.Loan
import com.mark.library.service.LoanService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

data class CheckoutRequest(
        val memberId: Long,
        val bookId: Long
)
data class CheckinRequest(
        val memberId: Long,
        val bookId: Long
)

@RestController
class LibraryController(
        private val loanService: LoanService
) {
    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    fun checkOut(@RequestBody req: CheckoutRequest): Loan =
            loanService.checkOut(req.memberId, req.bookId)

    @PostMapping("/checkin")
    @ResponseStatus(HttpStatus.CREATED)
    fun checkIn(@RequestBody req: CheckinRequest): Loan =
            loanService.checkIn(req.memberId, req.bookId)
}
