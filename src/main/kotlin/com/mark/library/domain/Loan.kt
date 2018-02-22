package com.mark.library.domain

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

const val LOAN_DURATION: Long = 14

@Entity
data class Loan(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        val bookId: Long,
        val memberId: Long,
        val checkoutDate: LocalDate = LocalDate.now(),
        val dueDate: LocalDate = LocalDate.now().plusDays(LOAN_DURATION)
)

@RepositoryRestResource(collectionResourceRel = "loan", path = "loan")
interface LoanRepository : PagingAndSortingRepository<Loan, Long> {
    interface MailAndDueOnly {
        val email: String
        val dueDate: LocalDate
    }

    @Query("""
SELECT m.email   AS email,
       l.dueDate AS dueDate
FROM   Loan l,
       Member m
WHERE  l.memberId = m.id
       AND l.dueDate = ?1""")
    fun findByDueDateEquals(date: LocalDate = LocalDate.now().plusDays(1)): List<MailAndDueOnly>

    fun findFirstByBookIdAndMemberId(bookId: Long, memberId: Long): Loan?
}
