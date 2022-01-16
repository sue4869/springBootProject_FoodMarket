package com.psc.sample.oauthlogin.config;

import com.psc.sample.oauthlogin.repository.UserRepository;
import com.psc.sample.oauthlogin.service.CustomOAuth2UserService;
import com.psc.sample.oauthlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService; // 3

    private final CustomOAuth2UserService customOAuth2UserService;

    private final ClientRegistrationRepository clientRegistrationRepository;

    private final UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN","SUPERADMIN")
//                .and()
//                .withUser("user").password(passwordEncoder().encode("user123")).roles("USER")
//                .and()
//                .withUser("manager").password(passwordEncoder().encode("manager123")).roles("MANAGER","ADMIN");
//        auth

    }

//    @Bean
//    DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
////        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
//
//        return daoAuthenticationProvider;
//    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()// crsf() 를 비활성화한다.
                .authorizeRequests() // 권한을 어떻게할지 밑으로 쭉 쓴다.
//                .antMatchers("/main").hasRole("USER") // 유저만 먹혔다.
                .antMatchers("/main").permitAll() // main.html 은 유저 권한이 있으면 들어오게한다.
                .antMatchers("/findId").permitAll() // findId.html 는 모든 사용자에게 권한을 준다.
                .antMatchers("/login").permitAll() // loginForm02.html 는 모든 사용자에게 권한을 준다.
                .antMatchers("/event").permitAll() // event.html 는 모든 사용자에게 권한을 준다.
                .antMatchers("/goodList").permitAll() // findId 는 모든 사용자에게 권한을 준다.
                .antMatchers("/registor").permitAll() // registor 는 모든 사용자에게 권한을 준다.
                .antMatchers("/findPw01").permitAll() // findPw01 는 모든 사용자에게 권한을 준다.
                .antMatchers("/findPw02").permitAll() // findPw02 는 모든 사용자에게 권한을 준다.
                .antMatchers("/findPw03").permitAll() // findPw03 는 모든 사용자에게 권한을 준다.
                .antMatchers("/findPw04").permitAll() // findPw04 는 모든 사용자에게 권한을 준다.
                .antMatchers("/loginFailed").permitAll()
                .antMatchers("/sendName").permitAll()
                .antMatchers("/message").permitAll()
                .antMatchers("/authNum").permitAll()
                .antMatchers("/idOverlapCheck").permitAll()
                .antMatchers("/sendBoolean").permitAll()
                .antMatchers("/registorCheck").permitAll()
                .antMatchers("/moreUserInformation").permitAll()
                .antMatchers("/goHome").permitAll()
                .antMatchers("/findingId").permitAll()
                .antMatchers("/findPwData01").permitAll()
                .antMatchers("/findPwPhone").permitAll()
                .antMatchers("/findPwAuthNum").permitAll()
                .antMatchers("/findPwData02").permitAll()
                .antMatchers("/findPwData03").permitAll()
                .antMatchers("/goodListForevent1/**").permitAll() // event.html 는 모든 사용자에게 권한을 준다.
                .antMatchers("/goodListForevent1/1").permitAll()
                .antMatchers("/goodDetail").permitAll() // event.html 는 모든 사용자에게 권한을 준다.
                .antMatchers("/heart").permitAll()
                .antMatchers("/heartFull").permitAll()
                .antMatchers("/basket").permitAll()
                .antMatchers("/loginCheckPage").permitAll()
                .antMatchers("/loginForm02").permitAll()
                // 아래 두개는 html 에서 image 와 css 를 받기위해서
                // 원래는 바로 받아지는데 Spring security 로 configure 를 사용하므로
                // 페이지들이 권한없이는 들어갈수없으므로 아래와 같이 해줌으로 html 에서 css, images 파일을 받아올수 있다.
                .antMatchers("/images/**").permitAll() // images 하위 모든 폴더는 모든 사용자에게 권한을 준다.
                .antMatchers("/css/**").permitAll() // css 하위 모든 폴더는 모든 사용자에게 권한을 준다.
                .antMatchers("/js/**").permitAll()
                .antMatchers("/memInfoFix").permitAll()
                .antMatchers("/memberconfirm").permitAll()
                .antMatchers("/memInfoFixPw").permitAll()
                .antMatchers("/addressModify").permitAll()
                .antMatchers("/deleteUser").permitAll()
                .antMatchers("/loginCheckPage").permitAll()
                .antMatchers("/paymentPage").permitAll()
                .antMatchers("/basket").permitAll()
                .antMatchers("/selectDelivery").permitAll()
                .antMatchers("/orderList").permitAll()
                .antMatchers("/goodDetail/**").permitAll()
                .antMatchers("/reviewClick").permitAll()
                .antMatchers("/review").permitAll()
                .antMatchers("/reviewWrite").permitAll()
                .antMatchers("/refund01").permitAll()
                .antMatchers("/refund02").permitAll()
                .antMatchers("/cancelHistory").permitAll()
                .antMatchers("/cancelHistoryDetail").permitAll()
                .antMatchers("/basketProductForDetail").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/adminPage").hasRole("USER")
//                .antMatchers("/**").permitAll()
                .anyRequest().authenticated() // 모든요청은 권한 체크를 하도록한다.
                    .and()
                        .exceptionHandling().accessDeniedPage("/accessDenied") // exception 이 발생했을때 custom 한 html 로 띄어준다.
                    .and()
                        .logout() // 로그아웃 설정. 여기서는 저희가 만든 회원가입과 구글로그인을 통한 회원만 반영됨
//                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
//                                .logoutSuccessHandler(oidcLogoutSuccessHandler())
//                                .logoutSuccessUrl("/login") // logout 이 완료되면 loginForm02.html 로 redirect 해준다.
//                                .invalidateHttpSession(true) // logout 하면 모든 세션을 만료시킨다.
//                                .clearAuthentication(true) // logout 하면 모든 권한을 초기화시킨다.
//                                .deleteCookies("JSESSIONID") // 쿠키를 지운다.
                .and()
                .oauth2Login().loginPage("/login") // oauth2(구글 네이버 카카오)로 로그인하기위한 페이지를 loginForm02.html 로 해준다.
//                .defaultSuccessUrl("/main") // 로그인이 성공하면 main.html 을 보여준다
                .defaultSuccessUrl("/moreUserInformation")
                .userInfoEndpoint() // ???
                .userService(customOAuth2UserService); // userService 를 customOAuth2UserService 를 참조해서 한다.

    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
//        auth.userDetailsService(userService)
//                // 해당 서비스(userService)에서는 UserDetailsService를 implements해서
//                // loadUserByUsername() 구현해야함 (서비스 참고)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }

    private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("http://localhost:80/"); // oicdLogut ( 즉 구글 유저가 로그아웃하거나 일반유저가 로그아웃 하면 보여줄 기본페이지를 lcoalhost:80/ 으로 지정해준다.
        return successHandler;
    }

}

