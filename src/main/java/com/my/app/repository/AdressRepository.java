package com.my.app.repository;

import com.my.app.model.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Marcin on 26/12/2016.
 */
public interface AdressRepository  extends JpaRepository<Adress, Long> {

    List<Adress> findAll();

}
