package za.co.richarde.exchange;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import za.co.richarde.exchange.exceptions.ExchangeException;
import za.co.richarde.exchange.models.Currency;
import za.co.richarde.exchange.models.OrderBook;
import za.co.richarde.exchange.models.OrderValue;

public class ExchangeServiceTest {
	private ExchangeService service;
	private DataService mockedVALRService;
	
	@Before
	public void setup() {
		this.mockedVALRService = Mockito.mock(DataService.class);
		this.service = new ExchangeService(this.mockedVALRService);
	}
	
	@Test
	public void testGetCurrencies() throws ExchangeException {
		// given
		final List<Currency> expectedResponse = new ArrayList<Currency>();
		final Currency currency1 = new Currency();
		currency1.setCode("CY1");
		currency1.setName("currency 1");
		currency1.setSymbol("A");
		
		final Currency currency2 = new Currency();
		currency2.setCode("CY2");
		currency2.setName("currency 2");
		currency2.setSymbol("B");
		
		expectedResponse.add(currency1);
		expectedResponse.add(currency2);
		
		Mockito.when(mockedVALRService.getCurrencyList(Mockito.anyString())).thenReturn(expectedResponse);
		
		// when
		final List<Currency> response = service.getCurrencies("madeupid");
		
		// then
		Assert.assertNotNull("The response  should not be null", response);
		Assert.assertTrue("The response should contain 2 entries", response.size() == 2);
		final Currency entry1 = response.get(0);
		Assert.assertEquals("The first entry should have a name of 'currency 1'", "currency 1", entry1.getName());
		Assert.assertEquals("The first entry should have a code of 'CY1'", "CY1", entry1.getCode());
		Assert.assertEquals("The first entry should have a symbol of 'A'", "A", entry1.getSymbol());
		
		final Currency entry2 = response.get(1);
		Assert.assertEquals("The second entry should have a name of 'currency 2'", "currency 2", entry2.getName());
		Assert.assertEquals("The second entry should have a code of 'CY2'", "CY2", entry2.getCode());
		Assert.assertEquals("The second entry should have a symbol of 'B'", "B", entry2.getSymbol());
		
		Mockito.verify(mockedVALRService, Mockito.times(1)).getCurrencyList(Mockito.anyString());
	}
	
	@Test
	public void testGetOrderBook() throws ExchangeException {
		// given
		final String fromCurrency = "CURR";
		final String toCurrency = "OTH";
		final OrderBook expectedResponse = new OrderBook();
		final List<OrderValue> asks = new ArrayList<OrderValue>();
		final List<OrderValue> bids = new ArrayList<OrderValue>();
		expectedResponse.setAsks(asks);
		expectedResponse.setBids(bids);
		
		OrderValue value = new OrderValue();
		value.setOrderCount(1);
		value.setPrice(12345);
		value.setQuantity(0.1212);
		asks.add(value);
		
		value = new OrderValue();
		value.setOrderCount(2);
		value.setPrice(12456);
		value.setQuantity(0.1200);
		asks.add(value);
		
		value = new OrderValue();
		value.setOrderCount(1);
		value.setPrice(12340);
		value.setQuantity(0.1313);
		bids.add(value);
		
		value = new OrderValue();
		value.setOrderCount(2);
		value.setPrice(12350);
		value.setQuantity(0.1300);
		bids.add(value);
		
		Mockito.when(mockedVALRService.getOrderBook(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(expectedResponse);
		
		// when
		final OrderBook response = service.getOrderBook("my api key", fromCurrency, toCurrency);
		
		// then
		Assert.assertNotNull("The response should not be null", response);
		final List<OrderValue> bidListResponse = response.getBids();
		Assert.assertNotNull("The bids in the order book should not be null", bidListResponse);
		Assert.assertEquals("There should be 2 bids in the list", 2, bidListResponse.size());
		
		OrderValue orderValue = bidListResponse.get(0);
		Assert.assertEquals("The order count should 1", 1, orderValue.getOrderCount());
		Assert.assertTrue("The price should 12340", Long.compare(12340L, orderValue.getPrice()) == 0);
		Assert.assertTrue("The quantity does not match", Double.compare(0.1313d, orderValue.getQuantity()) == 0);
		
		orderValue = bidListResponse.get(1);
		Assert.assertEquals("The order count should 2", 2, orderValue.getOrderCount());
		Assert.assertTrue("The price should 12350", Long.compare(12350L, orderValue.getPrice()) == 0);
		Assert.assertTrue("The quantity does not match", Double.compare(0.13d, orderValue.getQuantity()) == 0);
		
		final List<OrderValue> askListResponse = response.getAsks();
		Assert.assertNotNull("The asks in the order book should not be null", askListResponse);
		Assert.assertEquals("There should be 2 asks in the list", 2, askListResponse.size());
		
		orderValue = askListResponse.get(0);
		Assert.assertEquals("The order count should 1", 1, orderValue.getOrderCount());
		Assert.assertTrue("The price should 12345", Long.compare(12345L, orderValue.getPrice()) == 0);
		Assert.assertTrue("The quantity does not match", Double.compare(0.1212d, orderValue.getQuantity()) == 0);
		
		orderValue = askListResponse.get(1);
		Assert.assertEquals("The order count should 2", 2, orderValue.getOrderCount());
		Assert.assertTrue("The price should 12456", Long.compare(12456L, orderValue.getPrice()) == 0);
		Assert.assertTrue("The quantity does not match", Double.compare(0.12d, orderValue.getQuantity()) == 0);
		
		Mockito.verify(mockedVALRService, Mockito.times(1)).getOrderBook(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
}
