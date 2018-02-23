package com.mark.library.service

import com.mark.library.domain.*
import org.springframework.stereotype.Service

@Service
class LoanService(
        private val bookRepo: BookRepository,
        private val memberRepo: MemberRepository,
        private val loanRepo: LoanRepository
) {
    fun checkOut(memberId: Long, bookId: Long): Loan {
        val member = memberRepo.findOne(memberId)
                ?: throw IllegalArgumentException("member $memberId not found")
        val book: Book = bookRepo.findOne(bookId)
                ?: throw IllegalArgumentException("book $bookId not found")
        if (book.count < 1)
            throw IllegalStateException("book $bookId is out of stack")

        val loan = loanRepo.save(
                Loan(
                        bookId = bookId,
                        memberId = memberId
                )
        )

        // update book remaining
        bookRepo.save(book.copy(count = book.count - 1))

        // update member loans
        memberRepo.save(member.copy(loans = member.loans + loan))

        return loan
    }

    fun checkIn(memberId: Long, bookId: Long): Loan {
        val member = memberRepo.findOne(memberId)
                ?: throw IllegalArgumentException("member $memberId not found")
        val book: Book = bookRepo.findOne(bookId)
                ?: throw IllegalArgumentException("book $bookId not found")
        val loan: Loan = loanRepo.findFirstByBookIdAndMemberId(bookId, memberId)
                ?: throw IllegalArgumentException("loan by book $bookId and member $memberId not found")

        // update book remaining
        bookRepo.save(book.copy(count = book.count + 1))

        // update member loans
        memberRepo.save(member.copy(loans = member.loans - loan))

        // delete loan
        loanRepo.delete(loan.id)

        return loan
    }
}
