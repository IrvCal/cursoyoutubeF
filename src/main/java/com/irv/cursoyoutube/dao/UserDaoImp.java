package com.irv.cursoyoutube.dao;

import com.irv.cursoyoutube.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao{

    @PersistenceContext // creo que funciona como el HQL para obtener como el contexto para la db
    private EntityManager entityManager; // hace las conexiones para la db

    @Override
    public List<User> getUsers() {
        String query = "FROM User";// estas ya son las consultas pero con hibernate
        //NOTA** hace referencia al MODELO User de mi proyecto no a la tabla el modelo hace referencia a la tabla
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        User user =
        entityManager.find(User.class,id); // busca al usuario con su id, pero se le pasa la clase porque me imagino que es por que ahi especificamos en nombre de la tabla
        entityManager.remove(user);
    }

    @Override
    public void regrister(User user) {
        entityManager.merge(user); // este merge hace un insert
    }

    @Override
    public User getUser(User user) {
        String query =  "FROM User WHERE email = :email"; //NOTA
        List<User> userResponse = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();

        if(userResponse.isEmpty()){
            return null; // la lista de 1 esta vacia por lo que no se encontro ningun usuario con este correo
        }

        String passwordHashed = userResponse.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if( argon2.verify(passwordHashed, user.getPassword())) //compara (constrasenia hasheada CON, string ingresado)
        {
            return userResponse.get(0);
        }
        return null;
    }

}
//NOTA: en la parte del login
/*
Se tiene que hacer una autenticacion de manrea un poquito mas segura porque si se dejara
         String query =  "FROM User WHERE email = '"+user.email+"' AND password = '"+user.password+"' ";

ES PELIGROSO porque de esta forma le pueden inyectar codigo SQL para hackear
ya que se podria mandar una cadena de texto como la siguiente

    String email = " ' OR 1=1 --";
primero una comilla simple para cerrar el query que teniamos y no mandar ningun email
luego 1=1 para que de true
y los giones dobles hace que se omita lo demas de la consulta


La ultima parte es este if pero simplificado
     if(userResponse.isEmpty()){
            return false;
        }else{
            //se esta mandando la lista con un usuario
            return true;
        }



Se tenia el metodo login de esta forma

    @Override
    public boolean login(User user) {
        String query =  "FROM User WHERE email = :email AND password = :password"; //NOTA
        List<User> userResponse = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .setParameter("password", user.getPassword())
                .getResultList();
        return !userResponse.isEmpty();
    }

pero se cambio debido a que se implemento una libreria para hacer un hash a la contrasenia: Argon2
entonces esto ya nos da la capacidad de hacer este proceso "Mas seguro" y evitar aun mas la inyeccion
SQL

ACORDARSE QUE LAS CONTRASENIAS YA CON ARGON2 SE GUARDAN DE MANERA ENCRIPTADA POR LO QUE SE TIENE QUE
HACER LA COMPARACION DE LA CAONTRASENIA YA ENCRIPTADA CON ESTA LIBRERIA

 */

