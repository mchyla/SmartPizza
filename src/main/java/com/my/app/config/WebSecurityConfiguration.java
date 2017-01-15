package com.my.app.config;
//tu masz do ciagniecia z bazy, tylko musisz sobie dostowoac pare rzeczy

import com.my.app.model.User;
import com.my.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

                try {
                    User user = userRepository.findOneByEmail(email);
                    if(user == null){
                        user = userRepository.findByLogin(email);
                    }
                    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList(user.getRole().name()));
                } catch (UsernameNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
}
