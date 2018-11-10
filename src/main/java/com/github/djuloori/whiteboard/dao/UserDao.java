package com.github.djuloori.whiteboard.dao;

import com.github.djuloori.whiteboard.model.UserEO;
import com.github.djuloori.whiteboard.rest.UserRO;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class UserDao {

    //@Huh - Shouldn't do in this way [Change in the next tag]
    EntityManagerFactory emf =  Persistence.createEntityManagerFactory("PersistenceUnit");
    EntityManager em = emf.createEntityManager();

    public String findUser(UserRO userRO){
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("UserEntity.Validation", UserEO.class);
            String hashedPassword = getMD5(userRO.getPassword());
            query.setParameter("username",userRO.getUsername());
            query.setParameter("password",hashedPassword);
            em.getTransaction().commit();
            UserEO user = (UserEO) query.getSingleResult();
            if(userRO.getUsername().equals(user.getUsername()) && hashedPassword.equals(user.getPassword())){
                return user.getUsertype();
            }else{
                return "failed";
            }
        }catch(Exception e){
            return "failed";
        }
    }

    public String createUser(UserRO userRO){
        try {
            UserEO user = new UserEO();
            user.setUsername(userRO.getUsername());
            String hashedPassword = getMD5(userRO.getPassword());
            user.setPassword(hashedPassword);
            user.setUsertype(userRO.getUsertype());
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return "Success";
        }catch (Exception e){
            return "failed";
        }
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
