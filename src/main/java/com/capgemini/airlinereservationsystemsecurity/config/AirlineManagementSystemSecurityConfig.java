package com.capgemini.airlinereservationsystemsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capgemini.airlinereservationsystemsecurity.filter.CustomUsernamePasswordAuthenticationFilter;
import com.capgemini.airlinereservationsystemsecurity.handlers.UserLogoutSuccessHandler;
import com.capgemini.airlinereservationsystemsecurity.security.AirlineManagmentSystemAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class AirlineManagementSystemSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AirlineManagmentSystemAuthenticationEntryPoint  airlineAuthenticationEntryPoint;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private UserLogoutSuccessHandler userLogoutSuccessHandler;
	
	@Bean
	public UsernamePasswordAuthenticationFilter getUsernamePasswordAuthenticationFilter() throws Exception{
		CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(authenticationFailureHandler);
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder login) throws Exception {
		login.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http)throws Exception {
		http.csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(airlineAuthenticationEntryPoint)
		.and()
		.anonymous()
		.and()
		.authorizeRequests()
		.antMatchers("/template/forgot-password", "/template/change-password", "/template/getAllFlights", "/template/getFlightDetails", "/template/register").permitAll()
//		.and()
//		.authorizeRequests()
//		.antMatchers("/template/change-password").permitAll()
//		.and()
//		.authorizeRequests()
//		.antMatchers("/template/getAllFlights").permitAll()
//		.and()
//		.authorizeRequests()
//		.antMatchers("/template/getFlightDetails").permitAll()
//		.and()
//		.authorizeRequests()
//		.antMatchers("/template/register").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers("/template/flightRegister").hasRole("ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/template/updateFlight").hasRole("ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/template/updateUser").hasRole("ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/template/getAllUsers").hasRole("ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/template/deleteFlight").hasRole("ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/template/getUser").hasRole("ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/template/getAllFlights").hasRole("ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/template/bookingFlights").hasRole("USER")
		.and()
		.authorizeRequests()
		.antMatchers("/template/getTicket").hasRole("USER")
		.and()
		.authorizeRequests()
		.antMatchers("/template/deleteTicket").hasRole("USER")
		.and()
		.addFilterBefore(getUsernamePasswordAuthenticationFilter(), CustomUsernamePasswordAuthenticationFilter.class)
		.logout()
		.logoutSuccessHandler(userLogoutSuccessHandler)
		.and()
		.cors();
		
	}
}
