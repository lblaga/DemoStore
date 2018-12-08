package com.lblaga.demostore.repository;

import com.lblaga.demostore.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * CRUD repository for {@link Order}
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{
    List<Order> findByCreatedDateBetweenOrderByCreatedDateDesc(ZonedDateTime from, ZonedDateTime to);

    @Query(value = "select o, sum(oi.price * oi.amount) as totalPrice from Order o JOIN o.orderItems oi WHERE o.createdDate BETWEEN :starting and :ending GROUP BY o ORDER by o.createdDate DESC")
    List<Object[]> findTotalPricedCreateDateBetween(@Param("starting") ZonedDateTime from, @Param("ending") ZonedDateTime to);

}
