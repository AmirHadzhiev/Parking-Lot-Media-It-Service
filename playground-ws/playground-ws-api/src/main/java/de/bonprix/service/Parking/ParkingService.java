package de.bonprix.service.Parking;


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

import de.bonprix.dto.ParkingDTO;

@Path("/parking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ParkingService {

    /**
     * find all parkings with filter
     *
     * @param filter
     * @param fetchOptions
     * @return list of parkings
     */
    @GET
    @Path("/")
    List<ParkingDTO> findAll(@BeanParam @Valid ParkingFilter filter);

    /**
     * find applicationType by id
     */
    @GET
    @Path("/{id : \\d+}")
    ParkingDTO findById(@PathParam("id") @NotNull Long id);

    /**
     * delete parking by id
     *
     * @errorResponse 404 not found
     */
    @DELETE
    @Path("/{id : \\d+}")
    void deleteById(@PathParam("id") @NotNull Long id);

    /**
     * create parking
     *
     * @errorResponse 400 bad request (can't create parking with id)
     */
    @POST
    @Path("/")
    long create(@NotNull ParkingDTO parkingDTO);

    /**
     * update parking
     *
     * @errorResponse 400 bad request (can't update parking without id)
     * @errorResponse 404 not found
     */
    @PUT
    @Path("/")
    void update(@NotNull ParkingDTO parkingDTO);
}
