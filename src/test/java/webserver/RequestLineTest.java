package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @DisplayName("[GET] HTTP 요청의 requestLine 파싱")
    @Test
    void parse_http_request_get() {

        // given
        String requestLineText = "GET /docs/index.html HTTP/1.1";

        // when
        RequestLine requestLine = new RequestLine(requestLineText);

        // then
        assertThat(requestLine)
                .isEqualTo(new RequestLine("GET", "/docs/index.html", new Protocol("HTTP", "1.1")));
    }

    @DisplayName("[POST] HTTP 요청의 requestLine 파싱")
    @Test
    void parse_http_request_post() {

        // given
        String requestLineText = "POST /docs/index.html HTTP/1.1";

        // when
        RequestLine requestLine = new RequestLine(requestLineText);

        // then
        assertThat(requestLine)
                .isEqualTo(new RequestLine("POST", "/docs/index.html", new Protocol("HTTP", "1.1")));
    }

    @DisplayName("HTTP 요청의 query string 파싱 - query string이 있을 경우")
    @Test
    void parse_query_string() {

        // given
        String requestLineText = "GET /users?userId=dowon&password=password&name=DoWonLee HTTP/1.1";
        Map<String, String> params = new HashMap<>();
        params.put("userId", "dowon");
        params.put("password", "password");
        params.put("name", "DoWonLee");

        // when
        RequestLine requestLine = new RequestLine(requestLineText);

        // then
        assertThat(requestLine.getQueryParameters()).isEqualTo(params);
    }

    @DisplayName("HTTP 요청의 query string 파싱 - query string이 없을 경우")
    @Test
    void parse_empty_query_string_() {

        // given
        String requestLineText = "GET /users HTTP/1.1";

        // when
        RequestLine requestLine = new RequestLine(requestLineText);

        // then
        assertThat(requestLine.getQueryParameters()).isEmpty();
    }
}
