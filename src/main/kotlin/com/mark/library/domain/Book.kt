package com.mark.library.domain

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        val title: String,
        val author: String,
        val catalog: String,
        val stocked: Date = Date()
)

@RepositoryRestResource(collectionResourceRel = "book", path = "book")
interface BookRepository : PagingAndSortingRepository<Book, Long> {
    fun findByTitle(@Param("title") title: String): List<Book>
}
