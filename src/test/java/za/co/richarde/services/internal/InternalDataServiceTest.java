package za.co.richarde.services.internal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import za.co.richarde.exchange.DataService;
import za.co.richarde.exchange.exceptions.ExchangeException;
import za.co.richarde.exchange.models.Order;
import za.co.richarde.exchange.models.OrderStatus;

public class InternalDataServiceTest {
	private DataService service;
	private ExchangeMapper mockedMapper;
	
	@Before
	public void setup() {
		mockedMapper = Mockito.mock(ExchangeMapper.class);
		
		service = new InternalDataService(mockedMapper);
	}
	
	@Test
	public void testLimitSellOrderWithSuccess() throws ExchangeException {
		// given
		((InternalDataService)service).setOwned("ZAR", 1d);
		((InternalDataService)service).setOwned("BTC", 1d);
		final Order order = new Order();
		order.setFromCurrency("BTC");
		order.setPrice("1");
		order.setQuantity("1");
		order.setToCurrency("ZAR");
		Mockito.when(mockedMapper.createCounterOrder(order)).thenReturn(order);
		
		// when
		OrderStatus response = service.placeLimitSellOrder("madeup key", "made up secret key", order);
		
		// then
		Assert.assertNotNull("The response should not be null", response);
		
		double zarValue = ((InternalDataService)service).getOwned("ZAR");
		double btcValue = ((InternalDataService)service).getOwned("BTC");
		
		Assert.assertTrue("The zar amount is not correct", Double.compare(2d, zarValue) == 0);
		Assert.assertTrue("The btc amount is not correct", Double.compare(0d, btcValue) == 0);
	}
	
	@Test
	public void testLimitBuyOrderWithSuccess() throws ExchangeException {
		// given
		((InternalDataService)service).setOwned("ZAR", 1d);
		((InternalDataService)service).setOwned("BTC", 1d);
		final Order order = new Order();
		order.setFromCurrency("ZAR");
		order.setPrice("0.25");
		order.setQuantity("1");
		order.setToCurrency("BTC");
		Mockito.when(mockedMapper.createCounterOrder(order)).thenReturn(order);
		
		// when
		OrderStatus response = service.placeLimitBuyOrder("madeup key", "made up secret key", order);
		
		// then
		Assert.assertNotNull("The response should not be null", response);
		
		double zarValue = ((InternalDataService)service).getOwned("ZAR");
		double btcValue = ((InternalDataService)service).getOwned("BTC");
		
		Assert.assertTrue("The zar amount is not correct", Double.compare(0.75d, zarValue) == 0);
		Assert.assertTrue("The btc amount is not correct", Double.compare(2d, btcValue) == 0);
	}
}
