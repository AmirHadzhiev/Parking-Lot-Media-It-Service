package de.bonprix.service.ParkingPlace;



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


import de.bonprix.dto.ParkingPlaceDTO;

@Path("/parkingplace")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ParkingPlaceService {

    /**
     * find all cars with filter
     *
     * @param filter
     * @param fetchOptions
     * @return cars
     */
    @GET
    @Path("/")
    List<ParkingPlaceDTO> findAll(@BeanParam @Valid ParkingPlaceFilter filter);

    /**
     * find ParkingZoneDTO by id
     */
    @GET
    @Path("/{id : \\d+}")
    ParkingPlaceDTO findById(@PathParam("id") @NotNull Long id);

    /**
     * delete  ParkingZoneDTO  by id
     *
     * @errorResponse 404 not found
     */
    @DELETE
    @Path("/{id : \\d+}")
    void deleteById(@PathParam("id") @NotNull Long id);

    /**
     * create  ParkingZoneDTO
     * @errorResponse 400 bad request (can't create ParkingZoneDTO  with id)
     */
    @POST
    @Path("/")
    long create(@NotNull ParkingPlaceDTO parkingPlaceDTO);

    /**
     * update ParkingZoneDTO
     *
     * @errorResponse 400 bad request (can't update ParkingZoneDTO without id)
     * @errorResponse 404 not found
     */
    @PUT
    @Path("/")
    void update(@NotNull ParkingPlaceDTO parkingPlaceDTO);
}