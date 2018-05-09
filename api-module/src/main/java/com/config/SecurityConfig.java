package com.config;

import com.service.serviceImpl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


/**
 * created by Smita Hasole on 23-04-2018
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource);

//        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
//        if (bcrypt.matches(password, user.getPassword())) {
//            if (!user.isActive()) {
//                throw new OAuth2Exception("Please activate your account");
//            }
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/","/register").permitAll()
                .antMatchers("reimbursement/complete","reimbursement/all/*","reimbursement/approve").hasAuthority("Financer")
                .antMatchers("reimbursement/all/unapproved" ,"reimbursement/approve ").hasAnyAuthority("Approver", "Financer")
                .antMatchers("/form").hasAnyAuthority("Approver","Financer", "Requester");

    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////                .userDetailsService(userDetailsService)
////                .passwordEncoder(new BCryptPasswordEncoder());
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    }


    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
////        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("sql...")
//                .authoritiesByUsernameQuery("sql...");
//    }
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }

//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        BCryptPasswordEncoder encoder = passwordEncoder();
//        return new AuthenticationManagerBuilder()
//                .inMemoryAuthentication()
//                .withUser("user").password(encoder.encode("password"))
//                .and()
//                .passwordEncoder(encoder)
//                .and()
//                .build();
//    }

//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }


//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder;
//    }

//    @Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailService).passwordEncoder(encoder());
//
//    }
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//        return authProvider;
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider());
//    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
