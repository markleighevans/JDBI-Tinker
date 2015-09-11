package com.mle.tinker.resources;

/**
 * Created by mark on 05/09/15.
 */

import com.mle.tinker.dao.PersonDAO;
import com.mle.tinker.representaions.Person;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.skife.jdbi.v2.DBI;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/person")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})

public class PersonResource {
    PersonDAO personDAO;

    public PersonResource(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GET
    @Path("/query/{name}")
    public Response GetAll(@PathParam("name") String name)
    {

        return Response
                .ok(personDAO.getAll(name))
                .build();
    }


    @GET
    @Path("/{id}")
    public Person get(@PathParam("id") Integer id){
        return personDAO.findById(id);
    }

    @POST
    public Person add(@Valid Person person) {
        int newId = personDAO.insert(person);

        return person.setId(newId);
    }

    @PUT
    @Path("/{id}")
    public Person update(@PathParam("id") Integer id, @Valid Person person) {
        person = person.setId(id);
        personDAO.update(person);

        return person;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        personDAO.deleteById(id);
    }
}
