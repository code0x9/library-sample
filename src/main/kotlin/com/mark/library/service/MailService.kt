package com.mark.library.service

import com.mark.library.domain.LoanRepository
import com.mark.library.ext.toDate
import org.rythmengine.Rythm
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

data class Mail(
        val from: String,
        val to: String,
        val subject: String,
        val text: String
)

const val RECALL_TEMPLATE = """
@args Date dueDate
<중앙2문헌실>
귀하(혹은 가족)의 도서반납일은 @dueDate.format("M월 d일(E)", Locale.KOREA)입니다.
"""

@Service
class MailService(
        private val loanRepo: LoanRepository
) {
    @Scheduled(cron = "0 0 10 * * *")
    fun sendmail() {
        val loans = loanRepo.findByDueDateEquals()
        val mails = loans.map {
            Mail(
                    from = "loan@library.gov",
                    to = it.email,
                    subject = "도서 반납 안내",
                    text = Rythm.render(RECALL_TEMPLATE, it.dueDate.toDate()))
        }
        println(mails)
    }
}
