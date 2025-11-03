package pl.pw.spoda.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.pw.spoda.security.context.ApplicationContext;
import pl.pw.spoda.security.context.ContextPrincipal;
import pl.pw.spoda.security.service.JwtService;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtContextFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader( "Authorization" );
        if (authHeader == null || !authHeader.startsWith( "Bearer " )) {
            filterChain.doFilter( request, response );
            return;
        }

        String token = jwtService.unwrapToken( authHeader );
        if (!jwtService.validateToken( token )) {
            filterChain.doFilter( request, response );
            return;
        }

        ApplicationContext context = jwtService.extractContext( token );

        var principal = new ContextPrincipal( context );
        var authentication = new UsernamePasswordAuthenticationToken( principal, null, List.of() );
        SecurityContextHolder.getContext().setAuthentication( authentication );

        filterChain.doFilter( request, response );
    }
}
