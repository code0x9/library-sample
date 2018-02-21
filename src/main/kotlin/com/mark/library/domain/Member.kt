package com.mark.library.domain

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import javax.persistence.*

@Entity
data class Member(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        val email: String,
        @OneToMany
        val loans: List<Loan> = emptyList()
)

@RepositoryRestResource(collectionResourceRel = "member", path = "member")
interface MemberRepository : PagingAndSortingRepository<Member, Long>
