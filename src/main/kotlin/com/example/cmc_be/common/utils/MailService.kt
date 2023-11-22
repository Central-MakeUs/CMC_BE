package com.example.cmc_be.common.utils

import com.example.cmc_be.common.constants.CmcStatic.CODE
import com.example.cmc_be.common.constants.CmcStatic.HTML
import com.example.cmc_be.common.constants.CmcStatic.MAIL_PREFIX
import com.example.cmc_be.common.constants.CmcStatic.MAIL_TEMPLATE
import com.example.cmc_be.common.constants.CmcStatic.MAIL_TITLE
import com.example.cmc_be.common.constants.CmcStatic.UTF
import com.example.cmc_be.common.exeption.InternalServerException
import com.example.cmc_be.domain.user.exeption.SendEmailErrorCode
import kotlinx.coroutines.DelicateCoroutinesApi
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Component
class MailService(
    private val emailSender: JavaMailSender
) {
    @OptIn(DelicateCoroutinesApi::class)
    fun sendEmailAsync(email: String, code: String) {
        GlobalScope.launch {
            try {
                sendEmail(email, code)
            } catch (exception: Exception) {
                throw InternalServerException(SendEmailErrorCode.UNABLE_TO_SEND_EMAIL)
            }
        }
    }

    private fun sendEmail(email: String, code: String) {
        val message = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, UTF)
        helper.setTo(email)
        helper.setSubject(MAIL_TITLE)
        val html = setContext(code)
        helper.setText(html, true)
        emailSender.send(message)
    }

    private fun setContext(code: String): String {
        val context = Context()
        context.setVariable(CODE, code)
        val templateEngine = SpringTemplateEngine()
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = MAIL_PREFIX
        templateResolver.suffix = HTML
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.characterEncoding = UTF
        templateResolver.order = 1
        templateEngine.setTemplateResolver(templateResolver)
        return templateEngine.process(MAIL_TEMPLATE, context)
    }
}