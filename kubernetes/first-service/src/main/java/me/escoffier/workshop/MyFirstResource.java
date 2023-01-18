package me.escoffier.workshop;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.Calendar;
import java.text.SimpleDateFormat;

@Path("/")
public class MyFirstResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted("first-service.print.invocations") // <--- Added to keep track of the number of invocations
    public String print() {
        return "hello from " + System.getenv("HOSTNAME") + ", it's " + now();
    }

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    @Inject
    @RestClient
    SecondServiceClient client;

    @GET
    @Path("/quote")
    @Produces(MediaType.TEXT_PLAIN)
    @Timed("first-service.printWithQuote.time") // <-- Added to measure the time spent in this method
    public String printWithQuote() {
        return "hello from " + System.getenv("HOSTNAME") + ", " + client.getQuote();
    }

    @GET
    @Path("/crash")
    @Produces(MediaType.TEXT_PLAIN)
    public String crashSecondService() {
        return client.crash();
    }
}