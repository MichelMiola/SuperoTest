package br.com.test.supero;

import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import br.com.test.supero.web.filters.HeadersFilter;

import java.util.EnumSet;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    private static final ApplicationHome home = new ApplicationHome(Application.class);

    @Bean
    public JettyServletWebServerFactory webServerFactory() throws Exception {
        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        factory.addServerCustomizers(server -> server.setRequestLog(requestLog()));
        return factory;
    }

    private static RequestLog requestLog() {
        CustomRequestLog requestLog = new CustomRequestLog(home.getDir().toPath().resolve("access.log").toString());
        return requestLog;
    }

    @Bean
    public FilterRegistrationBean headersFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HeadersFilter());
        registration.addUrlPatterns("*");
        registration.setName("headersFilter");
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.setOrder(1);
        return registration;
    }

}
