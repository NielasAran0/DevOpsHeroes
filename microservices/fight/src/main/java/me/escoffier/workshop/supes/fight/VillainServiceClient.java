
package me.escoffier.workshop.supes.fight;

import java.time.temporal.ChronoUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.micrometer.core.annotation.Timed;

@RegisterRestClient(configKey = "villain")
@Produces(MediaType.APPLICATION_JSON)
public interface VillainServiceClient {

	@Timeout(value = 5, unit = ChronoUnit.SECONDS) // <---- Added
	@Retry(retryOn = TimeoutException.class, maxRetries = 4, maxDuration = 10, durationUnit = ChronoUnit.SECONDS)
	@Fallback(fallbackMethod = "getFallbackVillain") // <---- Added
	@Produces(MediaType.APPLICATION_JSON)
	@Timed("fight.printWithVillain.time")
	@Path("/villains/random")
	@GET
	String getRandomVillain();

	// A simple fallback
	default String getFallbackVillain() {
		return new Villain().toString();
	}

}
