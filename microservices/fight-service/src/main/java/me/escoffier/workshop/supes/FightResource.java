package me.escoffier.workshop.supes;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FightResource {

    private static final Logger LOGGER = Logger.getLogger(FightResource.class);
    
    @Inject @RestClient HeroServiceClient heroServiceClient;
    @Inject @RestClient VillainServiceClient villainServiceClient;

    @GET
    @Path("/heroes/random")
    public Hero getRandomHero() {
        Hero hero = heroServiceClient.getRandomHero();
        LOGGER.debug("Found random hero " + hero);
        return hero;
    }

    @GET
    @Path("/villains/random")
    public Villain getRandomVillain() {
        Villain villain = villainServiceClient.getRandomVillain();
        LOGGER.debug("Found random villain " + villain);
        return villain;
    }

    @GET
    @Path("/fight")
    public Fight fight() {
        return fight(
                getRandomHero(),
                getRandomVillain()
        );
    }

    private final Random random = new Random();

    private Fight fight(Hero hero, Villain villain) {
        int heroAdjust = random.nextInt(20);
        int villainAdjust = random.nextInt(20);

        if ((hero.level + heroAdjust) >= (villain.level + villainAdjust)) {
            return new Fight(hero, villain, hero.name);
        } else {
            return new Fight(hero, villain, villain.name);
        }
    }

}
