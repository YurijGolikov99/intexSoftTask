package restCountries.utils;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import utils.WorkWithResponseBody;
import java.util.List;
import static logger.CustomLog.info;

public class CustomAssertions {

    public static void checkForStatusCodeEquivalence(Response response, int expectedStatusCode) {
        info("Check that {%s} code is equal to {%s}", String.valueOf(response.getStatusCode()), String.valueOf(expectedStatusCode));
        Assertions.assertEquals(response.getStatusCode(), expectedStatusCode, String.format("Actual status code is %d and it's not equal to expected: %d", response.getStatusCode(), expectedStatusCode));
    }

    public static void checkThatResponseBodyIsNotEmpty(Response response) {
        info("Check that {%s} response body is not empty", String.valueOf(WorkWithResponseBody.getResponseBodyAsString(response).isEmpty()));
        Assertions.assertFalse(WorkWithResponseBody.getResponseBodyAsString(response).isEmpty(), "Response body is empty");
    }

    public static void checkThatAllElementsContainsValue(List<List<String>> elements, String value) {
        info("Check that value {%s} presented in the collection" + elements);
        Assertions.assertTrue(elements.stream().allMatch(element -> element.contains(value)), "Elements do not contain value: " + value);
    }

    public static void checkOnMessageEquivalence(Response response, String value) {
        info("Check that body {%s} has {%s}", WorkWithResponseBody.getResponseBodyAsString(response), value);
        Assertions.assertEquals(WorkWithResponseBody.getBodyValueWithJsonPath(response, "$.message"), value);
    }
}
