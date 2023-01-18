
package me.escoffier.workshop.supes.fight;

// import org.eclipse.microprofile.faulttolerance.Timeout;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.time.temporal.ChronoUnit;

import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.micrometer.core.annotation.Timed;

@RegisterRestClient(configKey = "hero")
public interface HeroServiceClient {

	@Timeout(value = 5, unit = ChronoUnit.SECONDS) // <---- Added
	@Retry(retryOn = TimeoutException.class, maxRetries = 4, maxDuration = 10, durationUnit = ChronoUnit.SECONDS)
	@Fallback(fallbackMethod = "getFallbackHero") // <---- Added
	@Timed("fight.printWithHero.time") // <-- Added to measure the time spent
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/heroes/random")
	@GET
	String getRandomHero();

	// A simple fallback
	default String getFallbackHero() {
		return new Hero().toString();
	}

}