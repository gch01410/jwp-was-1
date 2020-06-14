package webserver;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@AllArgsConstructor
public class HttpResponse {

    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public static HttpResponse of(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeaders responseHeaders = ResponseHeaders.of();
        ResponseBody responseBody = ResponseBody.of(httpRequest.getRequestLine());

        responseHeaders.addHeader("Content-Type", responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader("Content-Length", responseBody.getLength());
        return new HttpResponse(responseHeaders, responseBody);
    }

    public byte[] getBody() {
        return responseBody.getFile();
    }

    public String response200() {
        return "HTTP/1.1 200 OK \r\n";
    }

    public Map<String, ResponseHeader> getResponseHeaders() {
        return responseHeaders.getResponseHeaders();
    }
}
