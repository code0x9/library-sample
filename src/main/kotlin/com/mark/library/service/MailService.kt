package com.mark.library.service

import com.mark.library.domain.LoanRepository
import com.mark.library.ext.toDate
import org.rythmengine.Rythm
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.File

data class Mail(
        val from: String,
        val to: String,
        val subject: String,
        val text: String
)

@Service
class MailService(
        private val loanRepo: LoanRepository
) {
    val recallTemplate: File = ClassPathResource("templates/recall.txt").file

    @Scheduled(cron = "0 0 10 * * *")
    fun sendmail() {
        val loans = loanRepo.findByDueDateEquals()
        val mails = loans.map {
            Mail(
                    from = "loan@library.gov",
                    to = it.email,
                    subject = "도서 반납 안내",
                    text = Rythm.render(recallTemplate, it.dueDate.toDate()))
        }
        println(mails)
    }
}
