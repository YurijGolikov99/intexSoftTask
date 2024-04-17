package request;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;
import static logger.CustomLog.info;

public class SendingRequest {

    public static Response httpGetRequestWithQueryParameters(String URI, String paramName, List<String> queryParams) {
        info("Sending GET request to resource {%s} with parameter name: {%s} and query parameters: " + queryParams,
                URI,
                paramName);
        return RestAssured.given()
                .when()
                .queryParam(paramName, queryParams.toArray())
                .get(URI);
    }
}
