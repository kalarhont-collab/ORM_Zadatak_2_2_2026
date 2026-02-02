/*
Kreirati novi Java projekt koji će koristiti radne okvire Hibernate i JPA i implementirati primjer veze „1:N” s
entitetima „Jelo” (engl. Meal) i sastojci (engl. Ingredients).
Klasa „Meal” mora sadržavati identifikator „Long id” i naziv „String name” te listu objekata klase „Ingredient”
te anotaciju „@OneToMany”.
Klasa „Ingredient” mora sadržavati identifikator „Long id” i naziv „String name” i objekt klase „Meal” kojem
pripada taj sastojak označen s anotacijama „@ManyToOne” i „@JoinColumn”.
Kreirati i glavnu klasu koja sprema objekt klase „Meal” zajedno s nekoliko sastojaka.
Napisati upit koji će dohvaćati podatke o svim jelima.
 */


package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.Meal;
import org.example.model.Ingredient;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Meal meal1 = new Meal();
        meal1.setName("Hamburger");
        em.persist(meal1);

        Meal meal2 = new Meal();
        meal2.setName("Sarma");
        em.persist(meal2);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Junetina");
        ingredient1.setMeal(meal1);
        em.persist(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Salata");
        ingredient2.setMeal(meal1);
        em.persist(ingredient2);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setName("Rajčica");
        ingredient3.setMeal(meal1);
        em.persist(ingredient3);

        Ingredient ingredient4 = new Ingredient();
        ingredient4.setName("Glavica kupusa");
        ingredient4.setMeal(meal2);
        em.persist(ingredient4);

        Ingredient ingredient5 = new Ingredient();
        ingredient5.setName("Mljeveno meso");
        ingredient5.setMeal(meal2);
        em.persist(ingredient5);

        Ingredient ingredient6 = new Ingredient();
        ingredient6.setName("Riža");
        ingredient6.setMeal(meal2);
        em.persist(ingredient6);

        // Dohvat jela
        List<Meal> meals = em.createQuery("select m from Meal m", Meal.class).getResultList();

        for (Meal m : meals) {
            System.out.println("Jelo: " + m.getName());
            for (Ingredient i : m.getIngredients()) {
                System.out.println("Sastojak: " + i.getName());
            }
        }

        tx.commit();
        em.close();
        emf.close();
    }
}
