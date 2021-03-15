package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticateService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  public AuthenticateService authenticateService;

  public SecurityConfig(AuthenticateService authenticateService)
  {
      this.authenticateService = authenticateService;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth)
  {
      auth.authenticationProvider(this.authenticateService);
  }

  @Override
   public void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests().antMatchers("/signup", "/css/*" , "/jss/*")
       .permitAll().anyRequest().authenticated();

       http.formLogin().loginPage("/login").permitAll();

       http.formLogin().defaultSuccessUrl("/home" ,true).permitAll().and().logout()
              .logoutSuccessUrl("/login?logout")
              .permitAll();

   }


}
