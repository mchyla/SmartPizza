package com.my.app.repository;

import com.my.app.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Marcin on 24.11.2016.
 */

@Repository
public class PersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(User person){
        entityManager.persist(person);
    }

    public List<User> findAll(){
        TypedQuery query = entityManager.createQuery("select p from User p", User.class);
        return query.getResultList();
    }

    @Transactional
    public void removeOne(long id){
        User p = entityManager.find(User.class, id);
        entityManager.remove(p);
    }
}
