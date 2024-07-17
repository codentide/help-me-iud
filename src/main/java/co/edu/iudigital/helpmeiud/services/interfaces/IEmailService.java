package co.edu.iudigital.helpmeiud.services.interfaces;

public interface IEmailService {

    Boolean sendMail(String message, String email, String emailSubject);
}
