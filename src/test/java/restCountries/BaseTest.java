package restCountries;

import io.restassured.response.Response;
import restCountries.utils.Constants;
import java.util.List;
import static utils.ConfigManager.getProperty;
import static request.SendingRequest.httpGetRequestWithQueryParameters;

public abstract class BaseTest {

    protected Response sendGetRequest(String paramName, String countryCode) {
        return httpGetRequestWithQueryParameters(getProperty(Constants.BASE_URL_VALUE), paramName, List.of(countryCode));
    }
}
