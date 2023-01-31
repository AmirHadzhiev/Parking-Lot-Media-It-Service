package de.bonprix.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import de.bonprix.model.QParkingZone;
import de.bonprix.repository.jpa.ParkingZoneRepositoryCustom;

import de.bonprix.model.ParkingZone;


import de.bonprix.service.ParkingZone.ParkingZoneFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingZoneRepositoryImpl implements ParkingZoneRepositoryCustom {
    @Resource
    private EntityManager entityManager;

    @Override
    public List<ParkingZone> findAll(ParkingZoneFilter parkingZoneFilter) {
        if (parkingZoneFilter == null) {
            return Collections.emptyList();
        }
        final QParkingZone parkingZone = QParkingZone.parkingZone;
        final JPAQuery<ParkingZone> jpaQuery = createJpaQuery(parkingZone, parkingZoneFilter);
        final List<ParkingZone> parkingZones = jpaQuery.offset(parkingZoneFilter.getPage())
                .limit(parkingZoneFilter.getPageSize())
                .fetch()
                .stream()
                .distinct()
                .collect(Collectors.toList());

        return parkingZones;
    }

    @Override
    public ParkingZone findOne(ParkingZoneFilter parkingZoneFilter) {
        final QParkingZone parkingZone = QParkingZone.parkingZone;
        final JPAQuery<ParkingZone> jpaQuery = createJpaQuery(parkingZone, parkingZoneFilter);
        final List<ParkingZone> parkingZones = jpaQuery.fetch()
                .stream()
                .distinct()
                .collect(Collectors.toList());
        if (parkingZones == null || parkingZones.isEmpty()) {
            throw new NotFoundException();
        }
        return parkingZones.get(0);
    }

    @Override
    public int count(ParkingZoneFilter parkingZoneFilter) {
        final QParkingZone parkingZone = QParkingZone.parkingZone;
        return Math.toIntExact(createJpaQuery(parkingZone, parkingZoneFilter).fetchCount());
    }
    private BooleanBuilder handleWhereClause(ParkingZoneFilter parkingZoneFilter,QParkingZone parkingZone) {
        final BooleanBuilder builder = new BooleanBuilder();
        if (parkingZoneFilter != null && !parkingZoneFilter.getParkingZoneIds()
                .isEmpty()) {
            builder.and(parkingZone.id.in(parkingZoneFilter.getParkingZoneIds()));
        }
        return builder;
    }

    private JPAQuery<ParkingZone> getInitialQuery(final QParkingZone parkingZone) {
        JPAQuery<ParkingZone> jpaQuery = new JPAQuery<>(this.entityManager);
        jpaQuery = jpaQuery.from(parkingZone);
        return jpaQuery;
    }

    private JPAQuery<ParkingZone> createJpaQuery(final QParkingZone parkingZone, final ParkingZoneFilter parkingZoneFilter) {
        /* Initial Query */
        JPAQuery<ParkingZone> jpaQuery = getInitialQuery(parkingZone);

        /* Handle where clause */
        final BooleanBuilder builder = handleWhereClause(parkingZoneFilter, parkingZone);

        /* Final Query Execution and Result Set Extraction */
        jpaQuery = jpaQuery.where(builder);

        return jpaQuery;

    }
}
