package Dao;

import Model.ScheduleEO;
import Rest.ScheduleRO;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Component
public class ScheduleDao{
    EntityManagerFactory emf =  Persistence.createEntityManagerFactory("PersistenceUnit");
    EntityManager em = emf.createEntityManager();

    public List getAllschedule(){
        em.getTransaction().begin();
        Query q = em.createNamedQuery("ScheduleEntity.findAll", ScheduleEO.class);
        List<ScheduleEO> se;
        se = q.getResultList();
        return se;
    }

    public String addSchedule(ScheduleRO scheduleRO){
        ScheduleEO se = new ScheduleEO();
        se.setTimings(scheduleRO.getTimings());
        se.setDay(scheduleRO.getDay());
        se.setLocation(scheduleRO.getLocation());
        se.setCLASS_ID(scheduleRO.getCLASS_ID());
        se.setScheduleId(scheduleRO.getScheduleId());
        em.getTransaction().begin();
        em.persist(se);
        try {
            em.getTransaction().commit();
            em.close();
            return "Schedule Added";
        }catch (Exception e){
            return "not done";
        }
    }

    public String editSchedule(ScheduleRO scheduleRO){
        ScheduleEO se1 = new ScheduleEO();
        se1.setTimings(scheduleRO.getTimings());
        se1.setDay(scheduleRO.getDay());
        se1.setLocation(scheduleRO.getLocation());
        se1.setCLASS_ID(scheduleRO.getCLASS_ID());
        se1.setScheduleId(scheduleRO.getScheduleId());
        em.getTransaction().begin();
        em.merge(se1);
        try {
            em.getTransaction().commit();
            em.close();
            return "Schedule Edited";
        }catch (Exception e){
            return "not done";
        }
    }

    public String removeSchedule(String schedule_id){
        em.getTransaction().begin();
        ScheduleEO se2 = em.find(ScheduleEO.class,schedule_id);
        em.remove(se2);
        try {
            em.getTransaction().commit();
            em.close();
            emf.close();
            return "Schedule Removed";
        }catch (Exception e){
            return "Not Removed";
        }
    }
}