package org.codemaster;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codemaster.config.TomcatConfig;

import java.io.IOException;

public class SpringLiteApplication {
    public static void main(String[] args) {
        // Initialize TomcatConfig singleton and configure the server
        TomcatConfig tomcatConfig = TomcatConfig.getInstance();
        tomcatConfig.setContextPath("/myapp");  // Set a context path if desired
        tomcatConfig.startServer(8080);  // Start server on port 8080

        // Register a test servlet
        tomcatConfig.registerServlet(new HelloServlet(), "HelloServlet", "/hello");

        // Add shutdown hook to stop Tomcat gracefully on exit
        Runtime.getRuntime().addShutdownHook(new Thread(tomcatConfig::stopServer));
    }

    // Example Servlet
    public static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/html");
            resp.getWriter().println("<h1>Hello from Embedded Tomcat!</h1>");
        }
    }
}