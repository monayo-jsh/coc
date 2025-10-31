package open.api.coc.clans.clean.presentation.auth;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.auth.persistence.repository.JpaSecretCodeRepository;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Boolean> verifyAuthCode(@PathVariable String code) {
        return ResponseEntity.ok()
                             .body(secretCodeRepository.findByCode(code).isPresent());
    }

}
