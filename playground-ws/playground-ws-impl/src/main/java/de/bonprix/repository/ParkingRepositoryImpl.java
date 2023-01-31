package de.bonprix.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import de.bonprix.model.QParking;
import de.bonprix.repository.jpa.ParkingRepositoryCustom;
import de.bonprix.model.Parking;

import de.bonprix.service.Parking.ParkingFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingRepositoryImpl implements ParkingRepositoryCustom {
    @Resource
    private EntityManager entityManager;

    @Override
    public List<Parking> findAll(ParkingFilter parkingFilter) {
        if (parkingFilter == null) {
            return Collections.emptyList();
        }

        final QParking parking = QParking.parking;
        final JPAQuery<Parking> jpaQuery = createJpaQuery(parking, parkingFilter);
        final List<Parking> parkings = jpaQuery.offset(parkingFilter.getPage())
                .limit(parkingFilter.getPageSize())
                .fetch()
                .stream()
                .distinct()
                .collect(Collectors.toList());
        return parkings;
    }

    @Override
    public Parking findOne(ParkingFilter parkingFilter) {
        final QParking parking = QParking.parking;
        final JPAQuery<Parking> jpaQuery = createJpaQuery(parking, parkingFilter);
        final List<Parking> parkings = jpaQuery.fetch()
                .stream()
                .distinct()
                .collect(Collectors.toList());
        if (parkings == null || parkings.isEmpty()) {
            throw new NotFoundException();
        }
        return parkings.get(0);
    }

    @Override
    public int count(ParkingFilter parkingFilter) {
        final QParking parking = QParking.parking;
        return Math.toIntExact(createJpaQuery(parking, parkingFilter).fetchCount());
    }
    private BooleanBuilder handleWhereClause(ParkingFilter filter, QParking qParking) {
        final BooleanBuilder builder = new BooleanBuilder();
        if (filter != null && !filter.getParkingIds()
                .isEmpty()) {
            builder.and(qParking.id.in(filter.getParkingIds()));
        }
        return builder;
    }

    private JPAQuery<Parking> getInitialQuery(final QParking qParking) {
        JPAQuery<Parking> jpaQuery = new JPAQuery<>(this.entityManager);
        jpaQuery = jpaQuery.from(qParking);
        return jpaQuery;
    }

    private JPAQuery<Parking> createJpaQuery(final QParking qParking, final ParkingFilter parkingFilter) {
        /* Initial Query */
        JPAQuery<Parking> jpaQuery = getInitialQuery(qParking);

        /* Handle where clause */
        final BooleanBuilder builder = handleWhereClause(parkingFilter, qParking);

        /* Final Query Execution and Result Set Extraction */
        jpaQuery = jpaQuery.where(builder);

        return jpaQuery;

    }
}
