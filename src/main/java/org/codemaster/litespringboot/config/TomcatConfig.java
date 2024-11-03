package org.codemaster.litespringboot.config;

import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import java.io.File;

public class TomcatConfig {

    private static TomcatConfig instance;
    private Tomcat tomcat;
    private Context context;

    private String contextPath = "";
    private String appBase = "webapps";

    private TomcatConfig() {
    }

    // Singleton instance getter
    public static synchronized TomcatConfig getInstance() {
        if (instance == null) {
            instance = new TomcatConfig();
        }
        return instance;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setAppBase(String appBase) {
        this.appBase = appBase;
    }

    public void startServer(int port) {
        if (tomcat == null) {
            initTomcat(port);
        } else {
            System.out.println("Tomcat server is already running on port " + port);
        }
    }

    private void initTomcat(int port) {
        try {
            tomcat = new Tomcat();
            tomcat.setPort(port);
            tomcat.getConnector(); // Initialize default HTTP connector

            Host host = tomcat.getHost();
            host.setName("localhost");
            host.setAppBase(appBase);

            String docBase = new File(".").getAbsolutePath();
            context = tomcat.addContext(contextPath, docBase);

            tomcat.start();
            System.out.println("Tomcat server started on port " + port);
        } catch (LifecycleException e) {
            throw new RuntimeException("Failed to start Tomcat on port " + port, e);
        }
    }

    public <T extends HttpServlet> void registerServlet(T servletInstance, String servletName, String urlMapping) {
        if (tomcat == null || context == null) {
            throw new IllegalStateException("Tomcat server is not initialized. Call startServer() first.");
        }

        tomcat.addServlet(contextPath, servletName, servletInstance);
        context.addServletMappingDecoded(urlMapping, servletName);
        System.out.println("Servlet registered: " + servletName + " with mapping " + urlMapping);
    }

    public void stopServer() {
        if (tomcat != null) {
            try {
                tomcat.stop();
                tomcat.destroy();
                tomcat = null;  // Reset instance to allow restart
                System.out.println("Tomcat server stopped.");
            } catch (LifecycleException e) {
                throw new RuntimeException("Failed to stop Tomcat server", e);
            }
        } else {
            System.out.println("Tomcat server is not running.");
        }
    }
}

