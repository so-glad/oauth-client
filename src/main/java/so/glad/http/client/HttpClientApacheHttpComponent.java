package so.glad.http.client;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cartoon Zhang
 * @since 2017/3/29 下午1:03
 */
public class HttpClientApacheHttpComponent implements HttpClient {
    @Getter
    @Setter
    private org.apache.http.client.HttpClient httpClient;
}
