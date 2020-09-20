package za.co.richarde.services.valr;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ValrSigningSolution implements ValrSigningService {
	public String signRequest(final String apiKeySecret, final String timestamp, final String verb, final String path,
			final String body) {
		try {
			final Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
			final SecretKeySpec secretKeySpec = new SecretKeySpec(apiKeySecret.getBytes(), "HmacSHA512");
			hmacSHA512.init(secretKeySpec);
			hmacSHA512.update(timestamp.getBytes());
			hmacSHA512.update(verb.toUpperCase().getBytes());
			hmacSHA512.update(path.getBytes());
			hmacSHA512.update(body.getBytes());
			final byte[] digest = hmacSHA512.doFinal();

			return toHexString(digest);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException("Unable to sign request", e);
		}
	}

	private String toHexString(final byte[] a) {
		final StringBuilder sb = new StringBuilder(a.length * 2);
		for (final byte b : a)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}
}
