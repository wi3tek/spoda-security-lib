package pl.pw.spoda.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class ContextExtractor {

    public SpodaApplicationContext fromPrincipal(Principal principal) throws NoAccessException {
        if (principal instanceof Authentication auth && auth.getPrincipal() instanceof ContextPrincipal(SpodaApplicationContext context)) {
            return context;
        }
        throw new NoAccessException( String.format( "Access denied [principal = %s]", principal ) );
    }
}
