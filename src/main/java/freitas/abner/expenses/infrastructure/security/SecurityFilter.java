package freitas.abner.expenses.infrastructure.security;

import freitas.abner.expenses.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// This class control a filter that occurs once per request and before security authorizations
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    // This method verify JWT Token before the request reach the controller
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var jwtToken = getToken(request); // get token from request header
        if(jwtToken != null) {
            var subject = tokenService.getSubject(jwtToken); // get user from token subject
            var user = userRepository.findByUsername(subject); // verify if user exists
            var auth = new UsernamePasswordAuthenticationToken( // spring auth token
                    user,
                    null,
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(auth); // force authentication
        }
        filterChain.doFilter(request, response); // go to next filter until reach controller
    }

    private String getToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
