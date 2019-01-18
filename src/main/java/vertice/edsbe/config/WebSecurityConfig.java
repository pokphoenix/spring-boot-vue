package vertice.edsbe.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
                http.csrf().disable()
                    .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic();

             //.antMatchers("api/**").authenticated()
                //        http
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/foos").authenticated()
//                .antMatchers("/api/admin/**").hasRole("ADMIN")
//                .and()
//                .formLogin()
//                .successHandler(mySuccessHandler)
//               // .failureHandler(myFailureHandler)
//                .and()
//                .logout();
        }
}


