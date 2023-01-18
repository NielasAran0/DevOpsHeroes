package me.escoffier.workshop;

import java.time.temporal.ChronoUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "second-service")
@Produces(MediaType.TEXT_PLAIN)
public interface SecondServiceClient {

	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Timeout(value = 1, unit = ChronoUnit.SECONDS) // <---- Added
	@Fallback(fallbackMethod = "getFallbackQuote") // <---- Added
	@Path("/quote")
	@GET
	String getQuote();

	@Path("/crash")
	@GET
	String crash();

	// A simple fallback
	default String getFallbackQuote() {
		return "I am fascinated by air. If you remove the air from the sky, all the birds would fall to the ground. And all the planes, too.";
	}

}