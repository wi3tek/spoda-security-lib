package pl.pw.spoda.security.util;

import lombok.experimental.UtilityClass;
import pl.pw.spoda.security.context.Role;

import java.util.Collection;

@UtilityClass
public class SecurityUtils {

    public static boolean hasRole(Collection<Role> userRoles, Role required) {
        return userRoles.stream().anyMatch(role -> role.isAtLeast(required));
    }

    public static boolean hasAnyRole(Collection<Role> userRoles, Role... allowed) {
        for (Role r : allowed) {
            if (userRoles.contains(r)) return true;
        }
        return false;
    }
}
