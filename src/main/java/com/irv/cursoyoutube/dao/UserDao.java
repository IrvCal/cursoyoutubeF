package com.irv.cursoyoutube.dao;

import com.irv.cursoyoutube.models.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    void deleteUser(Long id);

    void regrister(User user);

    User getUser(User user);
}
