package dev.aisandbox.client;

import dev.aisandbox.client.scenarios.TestRequest;
import dev.aisandbox.client.scenarios.TestResponse;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class AgentTest {

    @Test
    public void testGetJSON() {
        Agent a = new Agent();
        a.setTarget("http://localhost/getJSON");
        a.setEnableXML(false);
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/getJSON"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"number\":\"4\"}", MediaType.APPLICATION_JSON));
        TestResponse r = a.getRequest("", TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 4, r.getNumber());
    }

    @Test
    public void testGetXML() {
        Agent a = new Agent();
        a.setTarget("http://localhost/getXML");
        a.setEnableXML(true);
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/getXML"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testResponse><number>5</number></testResponse>", MediaType.APPLICATION_XML));
        TestResponse r = a.getRequest("", TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 5, r.getNumber());
    }

    @Test
    public void testPostJSON() {
        Agent a = new Agent();
        a.setTarget("http://localhost/postJSON");
        a.setEnableXML(false);
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/postJSON"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("name").value("Betty"))
                .andRespond(withSuccess("{\"number\":\"4\"}", MediaType.APPLICATION_JSON));
        // run request
        TestRequest req = new TestRequest();
        req.setName("Betty");
        TestResponse r = a.postRequest(req, TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 4, r.getNumber());
    }

    @Test
    public void testPostXML() throws Exception {
        Agent a = new Agent();
        a.setTarget("http://localhost/postXML");
        a.setEnableXML(true);
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/postXML"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/testRequest/name").string("Fred"))
                .andRespond(withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testResponse><number>4</number></testResponse>", MediaType.APPLICATION_XML));
        // run request
        TestRequest req = new TestRequest();
        req.setName("Fred");
        TestResponse r = a.postRequest(req, TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 4, r.getNumber());
    }

    @Test
    public void testGetBasicAuthXML() {
        Agent a = new Agent();
        a.setTarget("http://localhost/getXML");
        a.setEnableXML(true);
        a.setBasicAuth(true);
        a.setBasicAuthUsername("Aladdin");
        a.setBasicAuthPassword("OpenSesame");
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/getXML"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "Basic QWxhZGRpbjpPcGVuU2VzYW1l"))
                .andRespond(withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testResponse><number>5</number></testResponse>", MediaType.APPLICATION_XML));
        TestResponse r = a.getRequest("", TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 5, r.getNumber());
    }

    @Test
    public void testGetKeyAuthXML() {
        Agent a = new Agent();
        a.setTarget("http://localhost/getXML");
        a.setEnableXML(true);
        a.setApiKey(true);
        a.setApiKeyHeader("APIKey");
        a.setApiKeyValue("OpenSesame");
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/getXML"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("APIKey", "OpenSesame"))
                .andRespond(withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testResponse><number>5</number></testResponse>", MediaType.APPLICATION_XML));
        TestResponse r = a.getRequest("", TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 5, r.getNumber());
    }

    @Test
    public void testValidURL() {
        Agent a = new Agent();
        a.setTarget("http://localhost/api");
        assertTrue("Valid URL", a.getValidProperty().get());
    }

    @Test
    public void testInvalidURL() {
        Agent a = new Agent();
        a.setTarget("xxx://localhost/api");
        assertFalse("Inalid URL", a.getValidProperty().get());
    }
}
