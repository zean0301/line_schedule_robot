package notify.wooper.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notify.wooper.service.JwtService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/user/user_login",
            "/line/webhook"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String uri = request.getRequestURI();

        if (!uri.startsWith("/api/")) {
            return true;
        }
        return PUBLIC_PATHS.contains(uri);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
                {
                    "success": false,
                    "message": "Authorization token is required"
                }
                """);

            return;
        }

        String token = authorization.substring(7);

        try {
            Claims claims = jwtService.validateToken(token);
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {

            log.warn("JWT expired");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
                {
                    "success": false,
                    "message": "Token expired"
                }
                """);

        } catch (JwtException | IllegalArgumentException e) {

            log.warn("Invalid JWT: {}", e.getMessage());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
                {
                    "success": false,
                    "message": "Invalid token"
                }
                """);
        }
    }
}