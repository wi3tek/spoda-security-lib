package pl.pw.spoda.security.context;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class JwtContextFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader( "Authorization" );

        if (header != null && jwtService.validateToken( header )) {
            SpodaApplicationContext ctx = jwtService.extractContext( header );
            ContextPrincipal principal = new ContextPrincipal( ctx );

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken( principal, null, Collections.emptyList() );

            SecurityContextHolder.getContext().setAuthentication( auth );
            log.debug( "Authentication context set for user: {}", ctx.getUsername() );
        }

        filterChain.doFilter( request, response );
    }
}
