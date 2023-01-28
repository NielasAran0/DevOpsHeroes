package me.escoffier.workshop.supes;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;

@RegisterRestClient(configKey = "villain-service")
public interface VillainServiceClient {

    @Timeout(value = 1, unit = ChronoUnit.SECONDS) // <---- Added
    @Fallback(fallbackMethod = "getFallbackQuote") // <---- Added
    @Path("/villains/random")
    @GET
    Villain getRandomVillain();
    
    // A simple fallback
    default Villain getFallbackQuote() {
        return new Villain();
    }

}