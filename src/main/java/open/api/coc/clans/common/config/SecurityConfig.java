package open.api.coc.clans.common.config;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.*;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${cms.admin-id}")
    private String username;

    @Value("${cms.admin-password}")
    private String password;

    private final DataSource dataSource;

    private static final String[] BLACK_LIST = {"/clan/cms/**"};

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin(login -> login.loginPage("/clan/cms/login").permitAll())
                .rememberMe(remember -> remember
                        .key("AcademyCMS")
                        .rememberMeParameter("academy-cms-remember-me")
                        .rememberMeCookieName("academy-cms-remember-me")
                        .tokenRepository(tokenRepository())
                        .userDetailsService(users()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(BLACK_LIST).authenticated()
                                .requestMatchers(HttpMethod.GET, "/**").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/**").permitAll()
//                                .requestMatchers(HttpMethod.PUT, "/**").permitAll()
//                                .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
                                .anyRequest().authenticated()).build();

    }


    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.builder()
                .username(username)
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
