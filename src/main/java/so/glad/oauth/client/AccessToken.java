package so.glad.oauth.client;

import so.glad.oauth.Const;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Cartoon Zhang
 * @since 2017/3/15 下午1:08
 */

@ToString
@EqualsAndHashCode
public class AccessToken {

    @Getter @Setter @JsonProperty("access_token")
    private String accessToken;
    @Getter @Setter @JsonProperty("refresh_token")
    private String refreshToken;
    @Getter @Setter @JsonProperty("expires_in")
    private Integer expiresIn;
    @Getter @JsonProperty("token_type")
    private String type = Const.OAuth.TOKEN_TYPE_BAERER;
    @Getter
    private Date timestamp = new Date();

    public Boolean isExpired() {
        return (System.currentTimeMillis() - timestamp.getTime() > expiresIn * 1000);
    }

    public Long beforeExpiredTime() {
        return timestamp.getTime() + (expiresIn * 1000) - System.currentTimeMillis()  ;
    }
}
