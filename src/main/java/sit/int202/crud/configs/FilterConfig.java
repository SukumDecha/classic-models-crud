package sit.int202.crud.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sit.int202.crud.filters.LogFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registerFilter() {
         FilterRegistrationBean reg = new FilterRegistrationBean(new LogFilter());

         return reg;
    }
}

