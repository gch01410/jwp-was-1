package webserver.http.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import webserver.http.HttpHeader;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ResponseHeader {

    private HttpHeader name;
    private List<String> values;

    public static ResponseHeader of(HttpHeader name, String... values) {
        List<String> headerValues = Arrays.stream(values)
                .collect(toList());
        return new ResponseHeader(name, headerValues);
    }

    @Override
    public String toString() {
        return String.join(name.getDelimiter(), values) + "\r\n";
    }
}
