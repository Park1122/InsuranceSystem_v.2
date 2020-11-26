package system.insurance.backend.service;

import javax.mail.MessagingException;

public interface MailService {
    void sendMail(String email) throws MessagingException;
}
