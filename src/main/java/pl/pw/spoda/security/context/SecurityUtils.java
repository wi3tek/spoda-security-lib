package pl.pw.spoda.security.context;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;

@UtilityClass
public class SecurityUtils {

    public static boolean hasRole(SpodaApplicationContext context, Role required) {
        if(context.getRoleList() == null) {
            context.setRoleList( new ArrayList<>() );
        }
        return context.getRoleList().stream().anyMatch(role -> role.isAtLeast(required));
    }

    public static boolean hasAnyRole(Collection<Role> userRoles, Role... allowed) {
        for (Role r : allowed) {
            if (userRoles.contains(r)) return true;
        }
        return false;
    }
}
