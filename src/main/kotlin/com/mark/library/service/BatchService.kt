package com.mark.library.service

import com.mark.library.domain.Book
import com.mark.library.domain.BookRepository
import kotlinx.coroutines.experimental.launch
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BatchService(
        private val bookRepo: BookRepository
) {
    val jobs = mutableMapOf<Int, Process>()

    fun execute(scripts: List<String>): List<Int> {
        val processes = scripts.map { script ->
            val process = ProcessBuilder(script).start()
            jobs[process.hashCode()] = process
            process
        }
        launch {
            processes.forEach { process ->
                val lines = process.inputStream
                        .bufferedReader()
                        .lines()
                lines.forEach { line ->
                    val entries = line.split(",")
                    bookRepo.save(
                            Book(title = entries[0],
                                    author = entries[1],
                                    catalog = entries[2],
                                    count = entries[3].toInt(),
                                    stockedDate = LocalDate.parse(entries[4]))
                    )
                }
                process.waitFor()
                jobs.remove(process.hashCode())
            }
        }
        return processes.map { p -> p.hashCode() }
    }

    fun query(jobId: Int): Boolean {
        return jobs[jobId]?.isAlive ?: false
    }
}
