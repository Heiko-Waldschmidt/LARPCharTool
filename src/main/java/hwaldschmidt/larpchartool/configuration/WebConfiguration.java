package hwaldschmidt.larpchartool.configuration;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Configuration for web specific stuff
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {


    /**
     * In the H2 implementation is the class WebServlet included. We add this here to the web server to make
     * it available for the user.
     * @return the servlet registration bean for the h2 WebServlet
     */
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    // ************************************************************************
    // CONFIGURE INTERNATIONALIZATION
    // http://justinrodenbostel.com/2014/05/13/part-4-internationalization-in-spring-boot/
    // ************************************************************************

    /**
     * Sets the default locale
     * @return a locale resolver with the default locale
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    /**
     * Creates an interceptor for the "lang" param inside the url
     * @return a locale interceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Adds our locale interceptor to the InterceptorRegistry
     * @param registry the registry where we want to add the interceptor to
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
