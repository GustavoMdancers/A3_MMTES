package br.mmtes.ticketApi.notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Component
public class EmailNotificacaoStrategy implements NotificacaoStrategy {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailNotificacaoStrategy(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void enviar(NotificacaoEmail notificacao) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(notificacao.getDestinatario());
            helper.setSubject(notificacao.getAssunto());

            Context context = new Context();
            context.setVariable("assunto", notificacao.getAssunto());
            context.setVariable("numeroAtividade", notificacao.getNumeroAtividade());
            context.setVariable("localAtividade", notificacao.getLocalAtividade());
            context.setVariable("descricaoAtividade", notificacao.getDescricaoAtividade());
            context.setVariable("dataInicio", formatar(notificacao));
            context.setVariable("dataTermino", formatarTermino(notificacao));
            context.setVariable("circuitosAfetados", notificacao.getCircuitosAfetados());

            helper.setText(templateEngine.process("ticket-email", context), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail para: " + notificacao.getDestinatario(), e);
        }
    }

    private String formatar(NotificacaoEmail n) {
        return n.getDataInicioExecucao() != null
            ? n.getDataInicioExecucao().format(FORMATO_DATA) : "Não informado";
    }

    private String formatarTermino(NotificacaoEmail n) {
        return n.getDataTerminoExecucao() != null
            ? n.getDataTerminoExecucao().format(FORMATO_DATA) : "Não informado";
    }
}
