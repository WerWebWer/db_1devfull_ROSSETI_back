package rosseti.devful.digitalassistant.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import rosseti.devful.digitalassistant.service.UserDetailsServiceImpl;


/**
 * Конфиг для настройки Spring Security
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /* PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(10));
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(encoder.encode("admin"))
                .roles("USER", "ADMIN"); */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
     /*           .authorizeRequests()
                .anyRequest()
                .authenticated();
       /* .and().httpBasic(); */
    }

     
}