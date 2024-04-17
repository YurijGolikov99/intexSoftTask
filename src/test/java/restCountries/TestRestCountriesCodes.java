package restCountries;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import restCountries.utils.Constants;
import java.util.List;
import static request.SendingRequest.httpGetRequestWithQueryParameters;
import static utils.ConfigManager.getProperty;
import static utils.WorkWithResponseBody.*;
import static restCountries.utils.CustomAssertions.*;

public class TestRestCountriesCodes extends BaseTest {

    @ParameterizedTest
    @DisplayName("Test - Borders between countries returned are mutual")
    @CsvSource(value = {"codes:RUS", "codes:RuS", "codes:rus"}, delimiter = ':')
    public void testBorderAndCountryOwnershipWithCountryISOCode(String paramName, String countryCode) {
        Response responseForOneCountry = sendGetRequest(paramName, countryCode);
        List<String> allBordersOfOneCountry = getBodyValuesWithJsonPathSingle(responseForOneCountry,
                getProperty(Constants.PATH_TO_GET_ALL_BORDERS));
        Response responseForMultipleCountries = httpGetRequestWithQueryParameters(getProperty(Constants.BASE_URL_VALUE),
                getProperty(Constants.CODES_PARAMTER_NAME), allBordersOfOneCountry);
        List<List<String>> collectionsOfBordersOfMultipleCountries = getBodyValuesWithJsonPathMultiple(responseForMultipleCountries,
                getProperty(Constants.PATH_TO_GET_COLLECTION_OF_BORDERS));
        checkForStatusCodeEquivalence(responseForOneCountry, Constants.SUCCESSFUL_STATUS_CODE);
        checkForStatusCodeEquivalence(responseForMultipleCountries, Constants.SUCCESSFUL_STATUS_CODE);
        checkThatResponseBodyIsNotEmpty(responseForOneCountry);
        checkThatResponseBodyIsNotEmpty(responseForMultipleCountries);
        checkThatAllElementsContainsValue(collectionsOfBordersOfMultipleCountries, countryCode.toUpperCase());
    }

    @ParameterizedTest
    @DisplayName("Test - Sending request with wrong codes parameter")
    @CsvSource(value = {"codes:2", "codes:-53", "codes:@", "codes: RUSS"}, delimiter = ':')
    public void testBorderAndCountryOwnershipWithWrongData(String paramName, String countryCode) {
        Response response = sendGetRequest(paramName, countryCode);
        checkForStatusCodeEquivalence(response, Constants.BAD_REQUEST_STATUS_CODE);
        checkThatResponseBodyIsNotEmpty(response);
        checkOnMessageEquivalence(response, Constants.BAD_REQUEST_MESSAGE);
    }

    @ParameterizedTest
    @DisplayName("Test - Request with wrong parameter name")
    @CsvSource(value = {"f:RUS", "g:UKR"}, delimiter = ':')
    public void testBorderAndCountryOwnerShipWithWrongParameter(String paramName, String countryCode) {
        Response response = sendGetRequest(paramName, countryCode);
        checkForStatusCodeEquivalence(response, Constants.BAD_REQUEST_STATUS_CODE);
        checkThatResponseBodyIsNotEmpty(response);
        checkOnMessageEquivalence(response, Constants.ARGUMENT_IS_NOT_SPECIFIED);
    }

    @ParameterizedTest
    @DisplayName("Test - Wrong country code passed as parameter")
    @CsvSource(value = {"codes:AAA"}, delimiter = ':')
    public void testBorderAndCountryOwnerShipWithWrongData1(String paramName, String countryCode) {
        Response response = sendGetRequest(paramName, countryCode);
        checkForStatusCodeEquivalence(response, Constants.NOT_FOUND_STATUS_CODE);
        checkThatResponseBodyIsNotEmpty(response);
        checkOnMessageEquivalence(response, Constants.NOT_FOUND_MESSAGE);
    }
}
