package za.co.richarde.services.valr;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import za.co.richarde.exchange.DataService;
import za.co.richarde.exchange.exceptions.ExchangeException;
import za.co.richarde.exchange.models.Currency;
import za.co.richarde.exchange.models.OrderBook;
import za.co.richarde.exchange.models.OrderValue;
import za.co.richarde.services.valr.exceptions.VALRException;
import za.co.richarde.services.valr.rest.models.v1.ValrOrderBook;

public class VALRRESTServiceTest {
	private DataService service;
	private ExchangeMapper mockedMapper;
	private ValrSigningService mockedSigningService;
	private JSONRESTClientService mockedRESTService;
	
	@Before
	public void setup() {
		mockedMapper = Mockito.mock(ExchangeMapper.class);
		mockedSigningService = Mockito.mock(ValrSigningService.class);
		mockedRESTService = Mockito.mock(JSONRESTClientService.class);
		service = new ValrRESTService(mockedMapper, mockedSigningService, mockedRESTService);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetCurrencyList() throws ExchangeException, VALRException {
		// given
		final String apiKey = "my api key";
		
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
		
		final String expectedJSONResponse = "[{\"symbol\":\"A\", \"shortName\":\"CY1\", \"longName\":\"currency 1\"}]";
		
		Mockito.when(mockedMapper.toCurrencyList(Mockito.anyList())).thenReturn(expectedResponse);
		Mockito.when(mockedRESTService.doGet(Mockito.anyString(), Mockito.anyString(), Mockito.anyMap())).thenReturn(expectedJSONResponse);
		
		// when
		final List<Currency> response = service.getCurrencyList(apiKey);
		
		// then
		Assert.assertNotNull("The response should not be null", response);
		Assert.assertEquals("There should be 2 entries in the response", 2, response.size());
		
		final Currency entry1 = response.get(0);
		Assert.assertEquals("The first entry should have a name of 'currency 1'", "currency 1", entry1.getName());
		Assert.assertEquals("The first entry should have a code of 'CY1'", "CY1", entry1.getCode());
		Assert.assertEquals("The first entry should have a symbol of 'A'", "A", entry1.getSymbol());
		
		final Currency entry2 = response.get(1);
		Assert.assertEquals("The second entry should have a name of 'currency 2'", "currency 2", entry2.getName());
		Assert.assertEquals("The second entry should have a code of 'CY2'", "CY2", entry2.getCode());
		Assert.assertEquals("The second entry should have a symbol of 'B'", "B", entry2.getSymbol());
		
		Mockito.verify(mockedMapper, Mockito.times(1)).toCurrencyList(Mockito.anyList());
		Mockito.verify(mockedSigningService, Mockito.times(0)).signRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		Mockito.verify(mockedRESTService, Mockito.times(1)).doGet(Mockito.anyString(), Mockito.anyString(), Mockito.anyMap());
	}
	
	@Test
	public void testGetOrderBook() throws ExchangeException, VALRException {
		// given
		final String apiKey = "my api key";
		final String fromCurrency = "MYC";
		final String toCurrency = "OTHC";
		final String combinedPair = "MYCOTHC";
		final String expectedJSONResponse = "{\"Asks\":[{\"side\":\"sell\", \"quantity\":\"0.101\", \"price\":\"9000\", \"currencyPair\":\"BTCZAR\",\"orderCount\":1}],\"Bids\":[{\"side\":\"buy\", \"quantity\":\"0.101\", \"price\":\"9000\", \"currencyPair\":\"BTCZAR\",\"orderCount\":1}]}";
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
		
		Mockito.when(mockedMapper.toCurrencyPair(Mockito.anyString(), Mockito.anyString())).thenReturn(combinedPair);
		Mockito.when(mockedMapper.toOrderBook(Mockito.any(ValrOrderBook.class))).thenReturn(expectedResponse);
		Mockito.when(mockedRESTService.doGet(Mockito.anyString(), Mockito.anyString(), Mockito.anyMap())).thenReturn(expectedJSONResponse);
		
		// when
		final OrderBook response = service.getOrderBook(apiKey, fromCurrency, toCurrency);
		
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
		
		Mockito.verify(mockedMapper, Mockito.times(1)).toCurrencyPair(Mockito.anyString(), Mockito.anyString());
		Mockito.verify(mockedMapper, Mockito.times(1)).toOrderBook(Mockito.any(ValrOrderBook.class));
		Mockito.verify(mockedSigningService, Mockito.times(0)).signRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		Mockito.verify(mockedRESTService, Mockito.times(1)).doGet(Mockito.anyString(), Mockito.anyString(), Mockito.anyMap());
	}
}
