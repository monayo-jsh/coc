package open.api.coc.clans.clean.presentation.auth;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.auth.persistence.repository.JpaSecretCodeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JpaSecretCodeRepository secretCodeRepository;

    @PostMapping("/verify/{code}")
    public ResponseEntity<Boolean> verifyAuthCode(HttpServletRequest request, @PathVariable String code) {

        boolean verified = secretCodeRepository.findByCode(code).isPresent();

        if (verified) {
            // 인증정보 생성
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            UserDetails user = User.withUsername("monayo").password("1").authorities(authorities).build();
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(user, null, authorities);

            // SecurityContext 설정
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        }

        return ResponseEntity.ok()
                             .body(verified);
    }

}
