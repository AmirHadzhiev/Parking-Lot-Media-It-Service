package de.bonprix.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.model.QParkingPlace;
import de.bonprix.repository.jpa.ParkingPlaceRepositoryCustom;

import de.bonprix.model.ParkingPlace;


import de.bonprix.service.ParkingPlace.ParkingPlaceFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingPlaceRepositoryImpl implements ParkingPlaceRepositoryCustom {
    @Resource
    private EntityManager entityManager;

    @Override
    public List<ParkingPlace> findAll(ParkingPlaceFilter filter) {
        if (filter == null) {
            return Collections.emptyList();
        }

        final QParkingPlace parkingPlace = QParkingPlace.parkingPlace;
        final JPAQuery<ParkingPlace> jpaQuery = createJpaQuery(parkingPlace, filter);
        final List<ParkingPlace> parkingPlaces = jpaQuery.offset(filter.getPage())
                .limit(filter.getPageSize())
                .fetch()
                .stream()
                .distinct()
                .collect(Collectors.toList());

        return parkingPlaces;
    }

    @Override
    public ParkingPlace findOne(ParkingPlaceFilter filter) {
        final QParkingPlace parkingPlace = QParkingPlace.parkingPlace;
        final JPAQuery<ParkingPlace> jpaQuery = createJpaQuery(parkingPlace, filter);
        final List<ParkingPlace> parkingPlaces = jpaQuery.fetch()
                .stream()
                .distinct()
                .collect(Collectors.toList());
        if (parkingPlaces == null || parkingPlaces.isEmpty()) {
            throw new NotFoundException();
        }
        return parkingPlaces.get(0);

    }

    @Override
    public int count(ParkingPlaceFilter filter) {
        final QParkingPlace parkingPlace = QParkingPlace.parkingPlace;
        return Math.toIntExact(createJpaQuery(parkingPlace, filter).fetchCount());
    }

    private JPAQuery<ParkingPlace> createJpaQuery(final QParkingPlace parkingPlace,
                                                            final ParkingPlaceFilter parkingPlaceFilter) {
        /* Initial Query */
        JPAQuery<ParkingPlace> jpaQuery = getInitialQuery(parkingPlace);

        /* Handle where clause */
        final BooleanBuilder builder = handleWhereClause(parkingPlaceFilter, parkingPlace);

        /* Final Query Execution and Result Set Extraction */
        jpaQuery = jpaQuery.where(builder);

        return jpaQuery;

    }

    private BooleanBuilder handleWhereClause(ParkingPlaceFilter parkingPlaceFilter, QParkingPlace parkingPlace) {
        final BooleanBuilder builder = new BooleanBuilder();
        if (parkingPlaceFilter != null && !parkingPlaceFilter.getParkingPlaceIds()
                .isEmpty()) {
            builder.and(parkingPlace.id.in(parkingPlaceFilter.getParkingPlaceIds()));
        }
        return builder;
    }

    private JPAQuery<ParkingPlace> getInitialQuery(final QParkingPlace parkingPlace) {
        JPAQuery<ParkingPlace> jpaQuery = new JPAQuery<>(this.entityManager);
        jpaQuery = jpaQuery.from(parkingPlace);
        return jpaQuery;
    }


    public void specialUpdate(ParkingPlaceDTO parkingPlaceDTO) {
      parkingPlaceDTO.setCar(null);

    }


    @Override
    public <S extends ParkingPlace> S saveAndFlush(S entity) {
        return null;
    }


    public void addPLace(ParkingPlace parkingPlace) {
        saveAndFlush(parkingPlace);
    }
}
