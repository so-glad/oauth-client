package so.glad.oauth.client;

import lombok.Data;

/**
 * @author Cartoon Zhang
 * @since 2017/3/29 下午1:30
 */
@Data
public class Error extends ResponseBean {

    private String code;

    private String message;

}
