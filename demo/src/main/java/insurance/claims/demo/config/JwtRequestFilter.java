package insurance.claims.demo.config;

import insurance.claims.demo.dto.AppUser;
import insurance.claims.demo.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//class verifies whether a jwt  is valid or not

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private JwtUtil jwtUtil;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username=null;

        String jwt= null;

        String authorizationHeader = request.getHeader("Authorization");

        //pulling email from jwt token

        if(authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")) {

            jwt = authorizationHeader.substring(7);

            try {
                username = jwtUtil.extractUsername(jwt);
            } catch(Exception e) {
                throw new IllegalStateException("Error extracting username");
            }
        }


        //providing authentication if there is none
        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {

            AppUser user = (AppUser) this.appUserService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt, (UserDetails) user)) {
                UsernamePasswordAuthenticationToken uPAT = new UsernamePasswordAuthenticationToken(
                        user, null, ((UserDetails) user).getAuthorities()
                );

                uPAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(uPAT);

            }
        }

        filterChain.doFilter(request, response);
    }
}
