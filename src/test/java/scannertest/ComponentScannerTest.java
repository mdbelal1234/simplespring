package scannertest;

import org.codemaster.litespringboot.scanner.ComponentScanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scannertest.testobject.EmailService;
import scannertest.testobject.NotificationService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ComponentScannerTest {

    private ComponentScanner componentScanner;

    @BeforeEach
    public void setUp() {
        componentScanner = ComponentScanner.getInstance();
    }

    @Test
    public void testComponentScanning() throws Exception {
        // Scan the package containing the components
        componentScanner.scan("scannertest.testobject");

        // Verify that EmailService component is loaded
        EmailService emailService = componentScanner.getComponent(EmailService.class);
        assertNotNull(emailService, "EmailService should be instantiated and retrieved");

        // Verify that NotificationService component is loaded and has EmailService injected
        NotificationService notificationService = componentScanner.getComponent(NotificationService.class);
        assertNotNull(notificationService, "NotificationService should be instantiated and retrieved");

        // Verify that the emailService field is injected in NotificationService
        assertNotNull(notificationService.getEmailService(), "EmailService should be injected into NotificationService");

        // Test the functionality
        notificationService.sendNotification("Test message");
    }
}

