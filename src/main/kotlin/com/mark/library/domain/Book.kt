package com.mark.library.domain

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        val title: String,
        val author: String,
        val catalog: String,
        val count: Int = 0,
        val stockedDate: LocalDate = LocalDate.now()
)

@RepositoryRestResource(collectionResourceRel = "book", path = "book")
interface BookRepository : PagingAndSortingRepository<Book, Long>
