package pl.pw.spoda.security.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContextPrincipal {

    private final ApplicationContext context;
}
