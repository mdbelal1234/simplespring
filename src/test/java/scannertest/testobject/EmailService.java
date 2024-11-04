package scannertest.testobject;

import org.codemaster.litespringboot.annotations.Component;

@Component
public class EmailService{
    public void sendEmail(String message) {
        System.out.println("Sent: " +message);
    }
}
