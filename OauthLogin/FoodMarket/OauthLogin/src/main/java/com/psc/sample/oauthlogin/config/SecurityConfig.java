package com.psc.sample.oauthlogin.config;

import com.psc.sample.oauthlogin.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/main").hasRole("USER")
                .antMatchers("/findId").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/welcome").hasRole("USER")
                .anyRequest().authenticated()
                    .and()
                        .exceptionHandling().accessDeniedPage("/accessDenied")
                    .and()
                        .logout()
//                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                            .logoutSuccessUrl("/login")
//                            .invalidateHttpSession(true)
//                            .deleteCookies("JSESSIONID")
//                                .logoutSuccessHandler(oidcLogoutSuccessHandler())
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                .and()
                .oauth2Login().loginPage("/login")
                .defaultSuccessUrl("/main")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

    }

    private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("http://localhost:80/");
        return successHandler;
    }

}

