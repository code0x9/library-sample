package com.mark.library.service

import com.mark.library.domain.*
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.annotation.PostConstruct

@Service
class LoanService(
        private val bookRepo: BookRepository,
        private val memberRepo: MemberRepository,
        private val loanRepo: LoanRepository
) {
    @PostConstruct
    fun init() {
        bookRepo.save(
                listOf(
                        Book(title = "선과 모터사이클 관리술: 가치에 대한 탐구",
                                author = "로버트 M. 피어시그",
                                catalog = "843",
                                count = 3),
                        Book(title = "제3인류",
                                author = "베르나르 베르베르",
                                catalog = "863",
                                count = 1)
                )
        )

        memberRepo.save(
                listOf(
                        Member(email = "member1@library.gov"),
                        Member(email = "member2@library.gov"),
                        Member(email = "member3@library.gov")
                )
        )

        loanRepo.save(
                listOf(
                        Loan(bookId = 1,
                                memberId = 1,
                                dueDate = LocalDate.now()),
                        Loan(bookId = 1,
                                memberId = 1,
                                dueDate = LocalDate.now().plusDays(1)),
                        Loan(bookId = 1,
                                memberId = 1,
                                dueDate = LocalDate.now().plusDays(2)),
                        Loan(bookId = 1,
                                memberId = 1,
                                dueDate = LocalDate.now().plusDays(3))
                )
        )
    }

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
}
