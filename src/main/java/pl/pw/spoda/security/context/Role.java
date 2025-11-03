package pl.pw.spoda.security.context;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Role {

    ADMIN( "Prezes", 100 ),
    MODERATOR( "Wiceprezes", 50 ),
    MAINTAINER( "Baron", 30 ),
    VIEWER( "Działacz", 10 ),
    PUBLIC("Śpoders",1);

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
