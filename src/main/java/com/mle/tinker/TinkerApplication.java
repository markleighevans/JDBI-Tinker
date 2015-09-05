package com.mle.tinker;

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
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

        final PersonDAO personDAO = jdbi.onDemand(PersonDAO.class);
        final PersonResource personResource = new PersonResource(personDAO);

        environment.jersey().register(personResource);
    }
}