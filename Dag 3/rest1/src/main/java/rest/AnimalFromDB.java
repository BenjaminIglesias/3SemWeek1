/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import entity.Animal;
import java.util.Random;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author Benjamin
 */
@Path("animals_db")
public class AnimalFromDB {
private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AnimalFromDB
     */
    public AnimalFromDB() {
    }

    /**
     * Retrieves representation of an instance of rest.AnimalFromDB
     * @return an instance of java.lang.String
 
 
 * 
 */
@GET
@Produces(MediaType.APPLICATION_JSON)
public String getAnimals() {
  EntityManager em = emf.createEntityManager();
  try{
      TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
      List<Animal> animals = query.getResultList();
      return new Gson().toJson(animals);
   } finally {
          em.close();
   }
}
@Path("animalbyid/{id}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public String getAnimal(@PathParam("id") int id) {
  EntityManager em = emf.createEntityManager();
  try{
      Animal query = em.find(Animal.class,id);
      return new Gson().toJson(query);
   } finally {
          em.close();
   }
}
@Path("animalbytype/{type}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public String getAnimalType(@PathParam("type") String type) {
  EntityManager em = emf.createEntityManager();
  try{
       TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a where a.type = :type", Animal.class);
       query.setParameter("type", type);
       List<Animal> animals = query.getResultList();
      
      return new Gson().toJson(animals);
   } finally {
          em.close();
   }
}
@Path("/random_animal")
@GET
@Produces(MediaType.APPLICATION_JSON)
public String getRandAnimals() {
  EntityManager em = emf.createEntityManager();
  try{
      TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
      List<Animal> animals = query.getResultList();
      int size = animals.size();
      Random rand = new Random(); 
      int randomId = rand.nextInt(size) + 1;
      String returnedJSON = getAnimal(randomId);
      return returnedJSON;
   } finally {
          em.close();
   }

 
  
}
}