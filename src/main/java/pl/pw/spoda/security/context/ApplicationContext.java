package pl.pw.spoda.security.context;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApplicationContext {
    private String username;
    private String discordId;
    private List<Role> roleList;
}
