package com.irv.cursoyoutube.controllers;

import com.irv.cursoyoutube.dao.UserDao;
import com.irv.cursoyoutube.models.User;
import com.irv.cursoyoutube.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login",method =  RequestMethod.POST)
    public String login(User user){
        User userLogged = userDao.getUser(user);
       if(userLogged != null){
           //   return "OK"; se tiene que retornar el token no nada mas el ok

           String token =
                   //en los argumentos se manda key=id & value=email
           jwtUtil.create(String.valueOf(userLogged.getId()),userLogged.getEmail());
    return token;

       }
       return "FAIL"; //se cambio el codigo para poder obtener el id del usuario y crear un JWT
    }
}
