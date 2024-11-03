package tomacatconfigtest;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.codemaster.litespringboot.config.TomcatConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TomcatConfigTest {

    private static TomcatConfig tomcatConfig;
    private static final int PORT = 8080;

    @BeforeAll
    static void setup() {
        // Initialize and start Tomcat server
        tomcatConfig = TomcatConfig.getInstance();
        tomcatConfig.setContextPath("/testapp");
        tomcatConfig.startServer(PORT);

        // Register a simple test servlet
        tomcatConfig.registerServlet(new TestServlet(), "TestServlet", "/test");
    }

    @AfterAll
    static void teardown() {
        // Stop the Tomcat server
        tomcatConfig.stopServer();
    }

    @Test
    void testServletResponse() throws IOException {
        // Make an HTTP GET request to the test servlet
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://localhost:" + PORT + "/testapp/test");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                // Verify HTTP status and response content
                assertEquals(200, response.getCode(), "Status code should be 200");

                String content = new String(response.getEntity().getContent().readAllBytes());
                assertEquals("<h1>Hello, Test Servlet!</h1>", content.trim(), "Servlet response should match expected content");
            }
        }
    }

    public static class TestServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/html");
            resp.getWriter().println("<h1>Hello, Test Servlet!</h1>");
        }
    }
}
