package com.mle.tinker;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import com.mle.tinker.dao.PersonDAO;
import com.mle.tinker.resources.PersonResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;

public class TinkerApplication extends Application<TinkerConfiguration> {
    public static void main(String[] args) throws Exception {
        new TinkerApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-jdbi";
    }

    @Override
    public void initialize(Bootstrap<TinkerConfiguration> bootstrap) {
    }

    @Override
    public void run(TinkerConfiguration configuration, Environment environment) throws ClassNotFoundException {
        //**********************************************************************************************
        // Enable CORS headers
        //*************ADDED TO WORK AROUND No 'Access-Control-Allow-Origin' header issue************
        //***********************************************************************************************
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        // *************************************************************
        // *************************************************************

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

        final PersonDAO personDAO = jdbi.onDemand(PersonDAO.class);
        final PersonResource personResource = new PersonResource(personDAO);

        environment.jersey().register(personResource);
    }
}