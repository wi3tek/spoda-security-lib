package pl.pw.spoda.security.context;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Role {

    PRESIDENT( "Prezes", 100 ),
    VICE_PRESIDENT( "Wiceprezes", 50 ),
    DELEGATE( "Delegat", 30 ),
    COORDINATOR("Koordynator", 20),
    OBSERVER( "Działacz", 10 ),
    REPRESENTATIVE("Śpoders",1);

    private final String title;
    private final int level;

    static Role getByTitle(String roleTitle) {
        return Arrays.stream( values() )
                .filter( role -> role.title.equals( roleTitle ) )
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException( "There is no role with title: " + roleTitle ) );
    }

    boolean isAtLeast(Role required) {
        return this.level >= required.level;
    }

}
