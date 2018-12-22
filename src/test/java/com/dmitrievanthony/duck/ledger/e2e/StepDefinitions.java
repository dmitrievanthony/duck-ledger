package com.dmitrievanthony.duck.ledger.e2e;

import com.dmitrievanthony.duck.ledger.Application;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.IOUtils;

import static org.junit.Assert.assertEquals;

/**
 * Step definitions required to maintains Cucumber tests.
 */
public class StepDefinitions {
    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(StepDefinitions.class);

    /** Web server port used in tests. */
    private static final int PORT = 8181;

    /** Application instance. */
    private Application app = new Application(PORT);

    /** Http client. */
    private CloseableHttpClient client;

    /** Response on a last HTTP request. */
    private CloseableHttpResponse response;

    @Before
    public void init() {
        log.info("Test initialization");

        app.start();
    }

    @When("^I create account$")
    public void createAccount() throws IOException {
        post("account");
    }

    @When("^I transfer (\\d+) from (\\d+) to (\\d+)$")
    public void transfer(long amount, long fromId, long toId) throws IOException {
        post("transfer" + "?fromId=" + fromId + "&toId=" + toId + "&amount=" + amount);
    }

    @When("^I withdraw (\\d+) from (\\d+)$")
    public void withdraw(long amount, long id) throws IOException {
        post("withdraw/" + id + "?amount=" + amount);
    }

    @When("^I deposit (\\d+) on (\\d+)$")
    public void deposit(long amount, long id) throws IOException {
        post("deposit/" + id + "?amount=" + amount);
    }

    @When("^I request balance of (\\d+)$")
    public void balane(long id) throws IOException {
        get("balance/" + id);
    }

    @Then("^Response status code is (\\d+)$")
    public void responseStatusCode(int code) {
        assertEquals("Status code is wrong", code, getResponseStatus());
    }

    @Then("^Response body is empty$")
    public void responseBodyIsEmpty() throws IOException {
        assertEquals("Response body is not empty", "", getResponseBody());
    }

    @Then("^Response body is \"([^\"]*)\"$")
    public void responseBodyIs(String body) throws IOException {
        assertEquals(body, getResponseBody());
    }

    @After
    public void destroy() throws IOException, InterruptedException {
        log.info("Test clean up");

        if (client != null) {
            client.close();
            client = null;
        }

        app.stop();
    }

    /**
     * Sends HTTP GET request and sets up {@link #response}.
     *
     * @param path Path to be requested.
     * @throws IOException If connection fails (server is unavailable).
     */
    private void get(String path) throws IOException {
        reinitializeClient();
        response = client.execute(new HttpGet("http://localhost:" + PORT + "/" + path));
    }

    /**
     * Sends HTTP POST request and sets up {@link #response}.
     *
     * @param path Path to be requested.
     * @throws IOException If connection fails (server is unavailable).
     */
    private void post(String path) throws IOException {
        reinitializeClient();
        response = client.execute(new HttpPost("http://localhost:" + PORT + "/" + path));
    }

    /**
     * Closes previous client if exists and initializes a new one.
     *
     * @throws IOException If connection fails (server is unavailable).
     */
    private void reinitializeClient() throws IOException {
        if (client != null) {
            client.close();
            client = null;
        }

        client = HttpClients.createDefault();
    }

    /**
     * Returns response status.
     *
     * @return Response status.
     */
    private int getResponseStatus() {
        return response.getStatusLine().getStatusCode();
    }

    /**
     * Returns response body.
     *
     * @return Response body.
     * @throws IOException If connection fails (server is unavailable).
     */
    private String getResponseBody() throws IOException {
        try (InputStream is = response.getEntity().getContent()) {
            return new String(IOUtils.toByteArray(is));
        }
    }
}
