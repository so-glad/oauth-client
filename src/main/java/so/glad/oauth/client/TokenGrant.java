package so.glad.oauth.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import so.glad.oauth.Const;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Cartoon Zhang
 * @since 2017/3/25 上午3:08
 */
@Accessors(chain = true)
public class TokenGrant {
    @Getter
    @Setter
    @JsonProperty(Const.OAuth.KEY_GRANT_TYPE)
    private String grantType;
    @Getter
    @Setter
    @JsonProperty(Const.OAuth.KEY_CODE)
    private String code;
    @Getter
    @Setter
    @JsonProperty(Const.OAuth.KEY_REDIRECT_URI)
    private String redirectUri;
    @Getter
    @Setter
    @JsonProperty(Const.OAuth.KEY_CLIENT_ID)
    private String clientId;
    @Getter
    @Setter
    @JsonProperty(Const.OAuth.KEY_CLIENT_SECRET)
    private String clientSecret;
    @Getter
    @Setter
    @JsonProperty(Const.OAuth.KEY_USERNAME)
    private String username;
    @Getter
    @Setter
    @JsonProperty(Const.OAuth.KEY_PASSWORD)
    private String password;
    @Getter
    @Setter
    @JsonProperty(Const.OAuth.KEY_REFRESH_TOKEN)
    private String refreshToken;
    @Getter
    @Setter
    @JsonProperty(Const.OAuth.KEY_SCOPE)
    private String scope = "*";

    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder("{");
        stringBuilder = this.buildJsonProperty(stringBuilder, Const.OAuth.KEY_GRANT_TYPE, grantType);
        stringBuilder = this.buildJsonProperty(stringBuilder, Const.OAuth.KEY_CODE, code);
        stringBuilder = this.buildJsonProperty(stringBuilder, Const.OAuth.KEY_REDIRECT_URI, redirectUri);
        stringBuilder = this.buildJsonProperty(stringBuilder, Const.OAuth.KEY_CLIENT_ID, clientId);
        stringBuilder = this.buildJsonProperty(stringBuilder, Const.OAuth.KEY_CLIENT_SECRET, clientSecret);
        stringBuilder = this.buildJsonProperty(stringBuilder, Const.OAuth.KEY_USERNAME, username);
        stringBuilder = this.buildJsonProperty(stringBuilder, Const.OAuth.KEY_PASSWORD, password);
        stringBuilder = this.buildJsonProperty(stringBuilder, Const.OAuth.KEY_REFRESH_TOKEN, refreshToken);
        stringBuilder = this.buildJsonProperty(stringBuilder, Const.OAuth.KEY_SCOPE, scope);
        return stringBuilder.append("}").toString();
    }

    private StringBuilder buildJsonProperty(StringBuilder stringBuilder, String key, String prop) {
        if (stringBuilder == null || prop == null || "".equals(prop.trim())) {
            return stringBuilder;
        }
        stringBuilder.append("\"")
                .append(key)
                .append("\": \"")
                .append(prop);
        if (Const.OAuth.KEY_SCOPE.equals(key)) {
            stringBuilder.append("\"");
        } else {
            stringBuilder.append("\",");
        }

        return stringBuilder;
    }
}
