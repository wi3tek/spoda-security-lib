package pl.pw.spoda.security.context;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Role {

    ADMIN( "Prezes", 100 ),
    MODERATOR( "Wiceprezes", 50 ),
    MAINTAINER( "Baron", 30 ),
    VIEWER( "Działacz", 10 );

    private final String title;
    private final int level;

    public static Role getByTitle(String roleTitle) {
        return Arrays.stream( values() )
                .filter( role -> role.title.equals( roleTitle ) )
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException( "There is no role with title: " + roleTitle ) );
    }

    public boolean isAtLeast(Role required) {
        return this.level >= required.level;
    }

}
