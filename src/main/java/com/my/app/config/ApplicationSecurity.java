package com.my.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private RESTAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RESTAuthenticationSuccessHandler authenticationSuccessHandler;

    //Tu ciagniesz usera na sztywno, jak chcesz z bazy komentujesz to, ale narazie zostaw
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
                .password("admin").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/dupa").hasAnyAuthority("USER")
                .antMatchers("/login").permitAll();
        //co tam jest nie ta ze nie dziala nie rozumiem
//        http.authorizeRequests()
//                .antMatchers("/private/api/**").hasAnyAuthority(Role.ROLE_ADMIN.name()) //o jak tutaj, wybor do Ciebie
//                .antMatchers("/protected/api/**").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name())
//                .antMatchers("/public/api/**").permitAll();
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.formLogin().successHandler(authenticationSuccessHandler);
        http.formLogin().failureHandler(authenticationFailureHandler);
        http.formLogin().successForwardUrl("/"); //dokladnie :D
        //http.formLogin().loginPage("login.html");
    }
}
