package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;

import java.util.List;

public interface UserDao {

    void create(User user);

    User findById(long id);

    void update(User user);

    void remove(User user);

    List<User> findAll();
}
