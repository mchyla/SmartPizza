package com.my.app.repository;

import com.my.app.model.OrderPizza;
import com.my.app.model.User;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Created by Marcin on 25/12/2016.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();
    User findByLogin(String login);
    List<OrderPizza> findAllOrderPizzaByLogin(String login);
    User findOneByEmail(String email);

}
