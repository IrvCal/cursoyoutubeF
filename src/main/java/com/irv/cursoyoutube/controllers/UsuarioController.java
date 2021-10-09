package com.irv.cursoyoutube.controllers;

import com.irv.cursoyoutube.dao.UserDao;
import com.irv.cursoyoutube.models.User;
import com.irv.cursoyoutube.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//hace como el manejo de la logica de los endpoints
@RestController
public class UsuarioController {

    @Autowired // ya hace referencia a la dependency injection
    private UserDao userDao;

    @Autowired
    JWTUtil jwtUtil;

    @RequestMapping(value = "api/user/{id}")
    public User getUser(@PathVariable Long id) {
        return new User(id,"Irving", "Calderon", "irv@mail.com", "1234");
    }

    /**
     * Esta funcion se modifico para poder hacer una verificacion del
     * token
     * @return
     */
    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> getUsers(@RequestHeader(value = "Authorization") String token) {
       String idUser =
        jwtUtil.getKey(token);
        return userDao.getUsers();
    }

    @RequestMapping(value = "api/user/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        userDao.deleteUser(id);
    }

    @RequestMapping(value = "api/user", method = RequestMethod.POST)
    public void registerUser(@RequestBody User user) { // en este caso el request body pasa directementeun JSON que le llega a un objeto del tipo User
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashedPassword =
        argon2.hash(1, 1024,1,user.getPassword());
        user.setPassword(hashedPassword);
        userDao.regrister(user);
    }
}
/*
Esta calse esta destinada a ser un controller por lo tanto,
se tiene que poner una etiqueta que especifique que esta lo sera,
 */