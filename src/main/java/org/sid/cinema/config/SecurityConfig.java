package org.sid.cinema.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	//@Autowired
	//private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","USER");
		auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("USER");
		
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/index","/allvilles").hasRole("USER");
		http.authorizeRequests().antMatchers("/addVille","/save","/edit","/delete").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
	}
	

	/*
	auth.jdbcAuthentication().dataSource(dataSource)
	.usersByUsernameQuery("select email as principal, password as credentials , enabled from user where email=?")
	.authoritiesByUsernameQuery("select user_email as principal , role_name as role from users_roles where user_email=?")
	.rolePrefix("ROLE_");
	*/
}
