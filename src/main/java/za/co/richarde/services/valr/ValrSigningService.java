package za.co.richarde.services.valr;

public interface ValrSigningService {
	public String signRequest(final String apiKeySecret, final String timestamp, final String verb, final String path, final String body);
}
