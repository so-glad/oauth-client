package so.glad.oauth.client;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cartoon Zhang
 * @since 2017/3/29 下午1:25
 */
public class ResponseModel<E extends Enum> {
    @Getter
    @Setter
    private E type;
    @Getter
    @Setter
    private ResponseBean result;
    @Getter
    @Setter
    private String error;
}
