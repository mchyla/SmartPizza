//package com.my.app.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////                .inMemoryAuthentication()
////                .withUser("user").password("pass").roles("USER"); //to nie jest kurwa to co ci wkleilem
////    }
////
//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
//                .password("admin").roles("ADMIN");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/dupa").hasAnyAuthority("USER")
//                .antMatchers("/login").permitAll()
//                //.antMatchers("/index").hasAnyAuthority("USER")
//                //.antMatchers("/api/**").permitAll()
//                //.antMatchers("/api/private/**").hasAnyAuthority("ADMIN")
//                //.antMatchers("/addPizza").permitAll()
//                //.antMatchers("/addIngredient").hasAnyAuthority("ADMIN")
//                //.antMatchers("/api/authenticated/**").hasAnyAuthority("ADMIN", "USER")
//                .and()
//                .formLogin() jebaj te klase jest zjebana
//                .loginPage("/login") j
//                .defaultSuccessUrl("/")
//                .loginProcessingUrl("/login")
//                .failureUrl("/login");
//    }
//}
