//package vertice.edsbe.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
//
//@Configuration
//public class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
//    private @Autowired
//    AccountManager accounts;
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(getUserDetailsService());
//        authProvider.setPasswordEncoder(getPasswordEncoder());
//        return authProvider;
//    }
//    @Bean
//    public UserDetailsService getUserDetailsService() {
//        return username - > accounts.stream()
//                .filter(Account.USERNAME.equal(username))
//                .findAny()
//                .orElseThrow(() - > new UsernameNotFoundException("Could not find the user '" + username + "'"));
//    }
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(getUserDetailsService())
//                .passwordEncoder(getPasswordEncoder());
//    }
//}
