package insurance.claims.demo.config;

import insurance.claims.demo.dto.AppUser;
import insurance.claims.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtDecompiler {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public AppUser getUserFromJwtToken(HttpServletRequest request) {

        String jwt = request.getHeader("Authorization").substring(7);

        AppUser user = accountRepository.findByEmail(jwtUtil.extractUsername(jwt)).get();

        return user;
    }
 }
