package com.my.app.repository;

import com.my.app.model.OrderPizza;
import com.my.app.model.Pizza;
import com.my.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Marcin on 26/12/2016.
 */
public interface OrderRepository extends JpaRepository<OrderPizza, Long> {

    List<OrderPizza> findByUser(User user);
    List<OrderPizza> findByDate(Date date);

}
