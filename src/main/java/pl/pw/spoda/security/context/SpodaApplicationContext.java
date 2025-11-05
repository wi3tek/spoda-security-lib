package pl.pw.spoda.security.context;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SpodaApplicationContext {
    private String username;
    private String discordId;
    private List<Role> roleList;
    private String displayName;
    private String avatar;
}
