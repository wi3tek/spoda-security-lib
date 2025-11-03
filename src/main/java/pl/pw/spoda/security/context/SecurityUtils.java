package pl.pw.spoda.security.context;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;

@UtilityClass
@Slf4j
public class SecurityUtils {

    public static boolean hasRole(SpodaApplicationContext context, Role required) {
        if(context.getRoleList() == null) {
            context.setRoleList( new ArrayList<>() );
        }


        boolean result = context.getRoleList().stream().anyMatch( role -> role.isAtLeast( required ) );
        log.info( "Result: {}", result  );
        return result;
    }

    public static boolean hasAnyRole(Collection<Role> userRoles, Role... allowed) {
        for (Role r : allowed) {
            if (userRoles.contains(r)) return true;
        }
        return false;
    }
}
