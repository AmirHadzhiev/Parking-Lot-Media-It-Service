package de.bonprix.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import de.bonprix.model.QCar;
import de.bonprix.repository.jpa.CarRepositoryCustom;

import de.bonprix.model.Car;



import de.bonprix.service.car.CarFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CarRepositoryImpl implements CarRepositoryCustom {

    @Resource
    private EntityManager entityManager;

    @Override
    public List<Car> findAll(CarFilter carFilter) {
        if (carFilter == null) {
            return Collections.emptyList();
        }
       final QCar car = QCar.car;
        final JPAQuery<Car> jpaQuery = createJpaQuery(car, carFilter);
        final List<Car> cars = jpaQuery.offset(carFilter.getPage())
                .limit(carFilter.getPageSize())
                .fetch()
                .stream()
                .distinct()
                .collect(Collectors.toList());

        return cars;
    }
    private JPAQuery<Car> createJpaQuery(final QCar qCar,
                                                            final CarFilter carFilter) {
        /* Initial Query */
        JPAQuery<Car> jpaQuery = getInitialQuery(qCar);

        /* Handle where clause */
        final BooleanBuilder builder = handleWhereClause(carFilter, qCar);

        /* Final Query Execution and Result Set Extraction */
        jpaQuery = jpaQuery.where(builder);

        return jpaQuery;

    }

    private JPAQuery<Car> getInitialQuery(final QCar qCar) {
        JPAQuery<Car> jpaQuery = new JPAQuery<>(this.entityManager);
        jpaQuery = jpaQuery.from(qCar);
        return jpaQuery;
    }

    private BooleanBuilder handleWhereClause(CarFilter filter, QCar qCar) {
        final BooleanBuilder builder = new BooleanBuilder();
        if (filter != null && !filter.getCarIds()
                .isEmpty()) {
            builder.and(qCar.id.in(filter.getCarIds()));
        }
        return builder;
    }

    @Override
    public Car findOne(CarFilter carFilter) {

        final QCar car = QCar.car;
        final JPAQuery<Car> jpaQuery = createJpaQuery(car, carFilter);
        final List<Car> cars = jpaQuery.fetch()
                .stream()
                .distinct()
                .collect(Collectors.toList());
        if (cars == null || cars.isEmpty()) {
            throw new NotFoundException();
        }
        return cars.get(0);

    }

    @Override
    public int count(CarFilter carFilter) {
        final QCar car = QCar.car;
        return Math.toIntExact(createJpaQuery(car, carFilter).fetchCount());

    }
}
