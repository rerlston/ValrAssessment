package za.co.richarde;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import za.co.richarde.api.models.Currency;
import za.co.richarde.api.models.OrderBookRequest;
import za.co.richarde.api.models.OrderBookResponse;
import za.co.richarde.api.models.OrderRequest;
import za.co.richarde.api.models.OrderStatusResponse;
import za.co.richarde.exchange.DataService;
import za.co.richarde.exchange.ExchangeService;
import za.co.richarde.exchange.exceptions.ExchangeException;
import za.co.richarde.exchange.models.OrderStatus;
import za.co.richarde.services.internal.ExchangeMapper;
import za.co.richarde.services.internal.InternalDataService;
//import za.co.richarde.services.valr.JSONRESTClientService;
//import za.co.richarde.services.valr.ValrRESTService;
//import za.co.richarde.services.valr.ValrSigningService;
//import za.co.richarde.services.valr.ValrSigningSolution;
//import za.co.richarde.services.valr.ExchangeMapper

@Path("/exchange")
public class ExchangeServer {
	private static final RESTMapper restMapper = new RESTMapper();
	private static final ExchangeMapper mapper = new ExchangeMapper();
	//private static final ValrSigningService signingService = new ValrSigningSolution();
	//private static final JSONRESTClientService restService = new JSONRESTClientService();
	//private static final DataService dataService1 = new ValrRESTService(mapper, signingService, restService);
	private static final DataService dataService = new InternalDataService(mapper);
	private static final ExchangeService exchange = new ExchangeService(dataService);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<Currency> getCurrencies(@QueryParam("apiKey") String apiKey) throws ExchangeException {
		final List<za.co.richarde.exchange.models.Currency> currencies = exchange.getCurrencies(apiKey);
		return restMapper.toCurrencyResponse(currencies);
	}
	
	@POST
	@Path("/orderbook")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public OrderBookResponse getOrderBook(final OrderBookRequest request) throws ExchangeException {
		final String apiKey = request.getApiKey();
		final String fromCurrency = request.getFromCurrency();
		final String toCurrency = request.getToCurrency();
		
		final za.co.richarde.exchange.models.OrderBook orderBook = exchange.getOrderBook(apiKey, fromCurrency, toCurrency);
		return restMapper.toOrderBookResponse(orderBook);
	}
	
	@POST
	@Path("/placelimitedorder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderStatusResponse placeLimitedOrder(final OrderRequest order) throws ExchangeException {
		final String apiKey = order.getApiKey();
		final String apiSecret = order.getApiSecret();
		OrderStatus orderStatus = null;
		if(order.isSell()) {
			orderStatus = exchange.placeSellOrder(apiKey, apiSecret, mapper.toOrder(order));
		} else {
			orderStatus = exchange.placeBuyOrder(apiKey, apiSecret, mapper.toOrder(order));
		}
		return restMapper.toOrderStatus(orderStatus);
	}
	
	@GET
	@Path("/history")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<OrderStatusResponse> getHistory(@QueryParam("apiKey") String apiKey) throws ExchangeException {
		final List<OrderStatus> history = exchange.getHistory(apiKey);
		return restMapper.toHistory(history);
	}
}
