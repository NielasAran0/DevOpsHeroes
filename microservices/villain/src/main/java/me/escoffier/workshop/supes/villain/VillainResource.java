package me.escoffier.workshop.supes.villain;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/")
public class VillainResource {

    private static final Logger LOGGER = Logger.getLogger(VillainResource.class);

    @GET
    @Path("/villains/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Villain getRandomVillain() {
        Villain villain = Villain.findRandom();
        LOGGER.debug("Found random villain " + villain);
        return villain;
    }

}
