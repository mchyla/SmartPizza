package com.my.app.repository;

import com.my.app.model.Adress;

/**
 * Created by Marcin on 26/12/2016.
 */
public interface AdressRepository {

    Adress findByUser();
}
