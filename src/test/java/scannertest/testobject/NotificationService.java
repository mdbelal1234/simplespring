package scannertest.testobject;

import org.codemaster.litespringboot.annotations.Autowired;
import org.codemaster.litespringboot.annotations.Component;

@Component
public class NotificationService {
    @Autowired
    private EmailService emailService;

    public void sendNotification(String message) {
        emailService.sendEmail(message);
        System.out.println("Notification sent with message: " + message);
    }
    public EmailService getEmailService() {
        return emailService;
    }
}
