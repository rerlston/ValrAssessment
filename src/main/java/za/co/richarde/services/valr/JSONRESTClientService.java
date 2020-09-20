package za.co.richarde.services.valr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import za.co.richarde.Configuration;
import za.co.richarde.services.valr.exceptions.VALRException;

public class JSONRESTClientService {
	public String doGet(final String baseURL, final String path, final Map<String, String> headers)
			throws VALRException {
		String fullURL = baseURL;
		if (!baseURL.endsWith("/") && !path.startsWith("/")) {
			fullURL += "/";
		}
		fullURL += path;

		HttpsURLConnection connection = null;
		try {
			final URL url = new URL(fullURL);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", Configuration.APPLICATION_JSON);
			if (headers != null) {
				for (final String headerKey : headers.keySet()) {
					final String headerValue = headers.get(headerKey);
					connection.setRequestProperty(headerKey, headerValue);
				}
			}
			connection.connect();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			final StringBuilder response = new StringBuilder();
			String input = "";
			while (input != null) {
				input = reader.readLine();
				if (input != null) {
					response.append(input);
				}
			}
			return response.toString();
		} catch (MalformedURLException exception) {
			throw new VALRException("The URL value [" + fullURL + "] is not a valid URL", exception);
		} catch (ProtocolException exception) {
			throw new VALRException("The REST call resulted in a protocol exception", exception);
		} catch (IOException exception) {
			throw new VALRException("The REST call response failed to be retrieved", exception);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public String doPost(final String baseURL, final String path, final Map<String, String> headers, final String body) throws VALRException {
		String fullURL = baseURL;
		if(!baseURL.endsWith("/") && !path.startsWith("/")) {
			fullURL += "/";
		}
		fullURL += path;
		
		HttpsURLConnection connection = null;
		try {
			final URL url = new URL(fullURL);
			connection = (HttpsURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty(Configuration.HEADER_CONTENT_TYPE, Configuration.APPLICATION_JSON);
			connection.setRequestProperty("Accept", Configuration.APPLICATION_JSON);
			connection.setDoOutput(true);
			if(headers != null) {
				for(final String headerKey : headers.keySet()) {
					final String headerValue = headers.get(headerKey);
					connection.setRequestProperty(headerKey, headerValue);
				}
			}
			
			connection.connect();
			
			try (OutputStream os = connection.getOutputStream()) {
			    byte[] output = body.getBytes("utf-8");
			    os.write(output, 0, output.length);
			}
			
			final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			final StringBuilder response = new StringBuilder();
			String input = "";
			while(input != null) {
				input = reader.readLine();
				if(input != null) {
					response.append(input);
				}
			}
			return response.toString();
		} catch (MalformedURLException exception) {
			throw new VALRException("The URL value [" + fullURL + "] is not a valid URL", exception);
		} catch (ProtocolException exception) {
			throw new VALRException("The REST call resulted in a protocol exception", exception);
		} catch (IOException exception) {
			throw new VALRException("The REST call response failed to be retrieved", exception);
		}
		finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
	}
}
