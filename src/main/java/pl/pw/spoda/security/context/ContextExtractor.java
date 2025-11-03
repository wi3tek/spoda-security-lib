package pl.pw.spoda.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class ContextExtractor {

    public ApplicationContext fromPrincipal(Principal principal) {
        if (principal instanceof Authentication auth &&
                auth.getPrincipal() instanceof ContextPrincipal ctxPrincipal) {
            return ctxPrincipal.getContext();
        }
        throw new IllegalStateException("Invalid principal type: " + principal);
    }
}
