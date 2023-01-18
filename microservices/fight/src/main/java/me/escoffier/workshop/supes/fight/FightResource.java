package me.escoffier.workshop.supes.fight;

import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@Path("/")
public class FightResource {

    private static final Logger LOGGER = Logger.getLogger(FightResource.class);

    @Inject
    @RestClient
    HeroServiceClient heroClient;

    @Inject
    @RestClient
    VillainServiceClient villainClient;

    private final Random random = new Random();

    public Hero getRandomHero() throws JsonMappingException, JsonProcessingException {

        String json = heroClient.getRandomHero();
        ObjectMapper mapper = new ObjectMapper();
        Hero hero = mapper.readValue(json, Hero.class);
        LOGGER.debug("Found random hero " + hero);
        return hero;
    }

    public Villain getRandomVillain() throws JsonMappingException, JsonProcessingException {
        String json = villainClient.getRandomVillain();
        ObjectMapper mapper = new ObjectMapper();
        Villain villain = mapper.readValue(json, Villain.class);
        LOGGER.debug("Found random villain " + villain);
        return villain;
    }

    @GET
    @Path("/fight")
    @Counted("fight.print.invocations") // <--- Added to keep track of the number of invocations
    @Produces(MediaType.APPLICATION_JSON)
    public Fight fight() throws JsonMappingException, JsonProcessingException {
        return fightWin(
                getRandomHero(),
                getRandomVillain());
    }

    private Fight fightWin(Hero hero, Villain villain) {
        int heroAdjust = random.nextInt(20);
        int villainAdjust = random.nextInt(20);

        if ((hero.level + heroAdjust) >= (villain.level + villainAdjust)) {
            return new Fight(hero, villain, hero.name);
        } else {
            return new Fight(hero, villain, villain.name);
        }
    }

}
