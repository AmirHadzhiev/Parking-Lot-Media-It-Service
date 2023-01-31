package de.bonprix.service.car;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.bonprix.dto.CarDTO;

@Path("/car")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CarService {

    /**
     * find all cars with filter
     *
     * @param filter
     * @param fetchOptions
     * @return cars
     */
    @GET
    @Path("/")
    List<CarDTO> findAll(@BeanParam @Valid CarFilter filter);

    /**
     * find applicationGroup by id
     */
    @GET
    @Path("/{id : \\d+}")
    CarDTO findById(@PathParam("id") @NotNull Long id);

    /**
     * delete  CarDTO  by id
     *
     * @errorResponse 404 not found
     */
    @DELETE
    @Path("/{id : \\d+}")
    void deleteById(@PathParam("id") @NotNull Long id);

    /**
     * create  CarDTO
     *
     * @errorResponse 400 bad request (can't create  CarDTO  with id)
     */
    @POST
    @Path("/")
    long create(@NotNull CarDTO carDTO);

    /**
     * update applicationGroup
     *
     * @errorResponse 400 bad request (can't update applicationGroup without id)
     * @errorResponse 404 not found
     */
    @PUT
    @Path("/")
    void update(@NotNull CarDTO carDTO);
}