package org.example;/*
Kreirati novi Java projekt koji će sadržavati perzistentnu klasu „User” s varijablama „id” (Long), „username” (String) i
 „email” (String) te odgovarajuću konfiguraciju u datoteci „persistence.xml”
Kreirati novu klasu s „main” metodom koja će sadržavati metode „persistUser”, „detachUser”, „reattachUser” i „deleteUser”
Metoda „persistUser” mora korištenjem EntityManager i EntityTransaction klasa pozivati metodu „persist” i
perzistirati jedan primjer objekta klase „User”
Metoda „detachUser” mora na sličan način pozvati metodu „detach”, a metoda „reattach” mora pozivom metode „merge”
ponovno povezati odvojeni entitet
Metodom „remove” na sličan način i obrisati objekt klase „User”
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.User1;


public class Main {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");

    public static void main(String[] args) {

        User1 user = new User1();
        user.setUsername("Marko");
        user.setEmail("marko@gmail.com");

        //persist
        persistUser(user);

        //detach
        detachUser(user);

        //reattach
        user.setEmail("darko@gmail.com");
        reattachUser(user);

        //delete
        deleteUser(user);

        // Zatvaranje EntityManagerFactory
        emf.close();
    }

    private static void persistUser(User1 user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
            System.out.println("User persisted: " + user);
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void detachUser(User1 user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User1 detachedUser = em.find(User1.class, user.getId());
            if (detachedUser != null) {
                em.detach(detachedUser);
                System.out.println("User detached: " + detachedUser);
            } else {
                System.out.println("User not found for detach!");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void reattachUser(User1 user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User1 reattachedUser = em.merge(user);
            transaction.commit();
            System.out.println("User reattached: " + reattachedUser);
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void deleteUser(User1 user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User1 deletedUser = em.find(User1.class, user.getId());
            if (deletedUser != null) {
                em.remove(deletedUser);
                System.out.println("User removed: " + deletedUser);
            } else {
                System.out.println("User not found for delete!");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
