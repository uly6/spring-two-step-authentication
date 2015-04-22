package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan
public class TwoStepAuthenticationApplication {


    public static void main(String[] args) {
        SpringApplication.run(TwoStepAuthenticationApplication.class, args);
    }

    @Configuration
    protected static class WebMvcConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("login");
            registry.addViewController("/login").setViewName("login");
            registry.addViewController("/code").setViewName("code");
            registry.addViewController("/home").setViewName("home");
        }
    }

    @Configuration
    @Order(-10)
    protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.authorizeRequests()
                    .antMatchers("/static/**").permitAll()
                    .antMatchers("/code").hasRole("PRE_AUTH_USER")
                    .antMatchers("/home").hasRole("USER")
                    .anyRequest().authenticated();

            http.formLogin()
                    .loginPage("/login").permitAll()
                    // always use the default success url despite if a protected page had been previously visited
                    .defaultSuccessUrl("/code", true)
                    .and()
                    .logout().permitAll();
            // @formatter:on
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.parentAuthenticationManager(authenticationManager);
            auth.inMemoryAuthentication()
                    .withUser("user").password("password").roles("PRE_AUTH_USER");
        }
    }

}
