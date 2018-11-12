package com.github.tophatcroat.kotlinspringbootstrap


import com.github.tophatcroat.kotlinspringbootstrap.service.UserService
import com.github.tophatcroat.kotlinspringbootstrap.web.auth.JwtAuthFilter
import com.github.tophatcroat.kotlinspringbootstrap.web.auth.JwtTokenAuthFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class AuthConfig @Autowired
constructor(val userService: UserService,
            @Value("\${security.signing-key}") val signingKey: String) : WebSecurityConfigurerAdapter() {

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userService)
                .passwordEncoder(BCryptPasswordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint { req, rsp, e -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
                .and()
                // Add a filter to validate the tokens with every request
                .addFilterBefore(JwtTokenAuthFilter(userService, signingKey), UsernamePasswordAuthenticationFilter::class.java)
                .addFilterAfter(JwtAuthFilter(authenticationManager(), signingKey), UsernamePasswordAuthenticationFilter::class.java)
                // authorization requests config
                .authorizeRequests()
                // allow all who are accessing "auth" service
                .antMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                // must be an admin if trying to access admin area (authentication is also required here)
                .antMatchers("/admin/**").hasRole("ADMIN")
                // Any other request must be authenticated
                .anyRequest().authenticated()
    }
}
