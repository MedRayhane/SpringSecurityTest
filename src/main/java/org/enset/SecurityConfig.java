package org.enset;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void globalConfig(AuthenticationManagerBuilder auth,DataSource dataSource)
			throws Exception {
		// specier quel type des utulisiateur est ce que les utulisateur se
		// trouvent dans SGBD,LDAP...
		auth.jdbcAuthentication().
		dataSource(dataSource).usersByUsernameQuery("select username as principal,password as credentials,true from user where username=? ")
		.authoritiesByUsernameQuery("select user_username as principal,roles_role as role,true from user_role where user_username=? ")
		.rolePrefix("ROLE_");
/*		auth.inMemoryAuthentication().withUser("admin").password("root")
				.roles("ADMIN");// les utulisateur se trouvent au memoir au
								// demmmarage
		auth.inMemoryAuthentication().withUser("prof1").password("root")
				.roles("PROF");
		auth.inMemoryAuthentication().withUser("etu1").password("root")
				.roles("ETUDIANT");
		auth.inMemoryAuthentication().withUser("scol").password("root")
				.roles("SCOLARITE");
*/
		
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().//dire a spring que est de utuliser CSRF(le jeton d'authenfication ) donc on uulise les session 
		authorizeRequests().
		antMatchers("/css/**","/js/**").permitAll().//autoriser certain resource sans avoir etre authentfier
		anyRequest().
		authenticated().// toute les requets doit etre athentfier
		and().//sauf login ne peut pas etre authentfier 
		formLogin().loginPage("/login")//une fois detect qui napa le droit il redirect vers page login
		.permitAll().
		defaultSuccessUrl("/index.html");
	}
}
