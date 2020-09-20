package za.co.richarde.services.valr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VALRSigningServiceTest {
	private ValrSigningService service;
	
	@Before
	public void setup() {
		service = new ValrSigningSolution();
	}
	
	@Test
	public void testSignRequestWithNoBody() {
		// given
		final String timestamp = "1558014486185";
		final String verb = "GET";
		final String path = "/v1/account/balances";
		final String body = "";
		final String apiSecret = "4961b74efac86b25cce8fbe4c9811c4c7a787b7a5996660afcc2e287ad864363";
		
		// when
		final String response = service.signRequest(apiSecret, timestamp, verb, path, body);
		
		// then
		Assert.assertNotNull("The response must not be null", response);
		Assert.assertEquals("The expected value was not returned", "9d52c181ed69460b49307b7891f04658e938b21181173844b5018b2fe783a6d4c62b8e67a03de4d099e7437ebfabe12c56233b73c6a0cc0f7ae87e05f6289928", response);
	}
	
	@Test
	public void testSignRequestWithBody() {
		// given
		final String timestamp = "1558017528946";
		final String verb = "POST";
		final String path = "/v1/orders/market";
		final String body = "{\"customerOrderId\":\"ORDER-000001\",\"pair\":\"BTCZAR\",\"side\":\"BUY\",\"quoteAmount\":\"80000\"}";
		final String apiSecret = "4961b74efac86b25cce8fbe4c9811c4c7a787b7a5996660afcc2e287ad864363";
		
		// when
		final String response = service.signRequest(apiSecret, timestamp, verb, path, body);
		
		// then
		Assert.assertNotNull("The response must not be null", response);
		Assert.assertEquals("The expected value was not returned", "be97d4cd9077a9eea7c4e199ddcfd87408cb638f2ec2f7f74dd44aef70a49fdc49960fd5de9b8b2845dc4a38b4fc7e56ef08f042a3c78a3af9aed23ca80822e8", response);
	}
}
