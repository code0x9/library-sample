package com.mark.library

import com.google.common.truth.Truth
import com.mark.library.domain.*
import com.mark.library.service.LoanService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import javax.inject.Inject

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests {
    @Inject
    lateinit var bookRepo: BookRepository
    @Inject
    lateinit var loanRepo: LoanRepository
    @Inject
    lateinit var memberRepo: MemberRepository
    @Inject
    lateinit var loanService: LoanService

    @BeforeEach
    fun setup() {
        bookRepo.save(
                listOf(
                        Book(1, "title_1", "author_1", "catalog_1", 1, LocalDate.now()),
                        Book(2, "title_2", "author_2", "catalog_2", 1, LocalDate.now())
                )
        )
        memberRepo.save(
                listOf(
                        Member(1, "member_1@library.gov", emptyList()),
                        Member(2, "member_2@library.gov", emptyList())
                )
        )
    }

    @AfterEach
    fun tearDown() {
        bookRepo.delete(1)
        bookRepo.delete(2)
        memberRepo.delete(1)
        memberRepo.delete(2)
    }

    @Test
    fun testCheckout() {
        val actual = loanService.checkOut(1, 1)
        val expected = Loan(id = 1, bookId = 1, memberId = 1)
        Truth.assertThat(actual).isEqualTo(expected)
    }
}
