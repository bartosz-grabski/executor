package agh.bit.ideafactory.daoimpl;

import agh.bit.ideafactory.dao.CarDao;
import agh.bit.ideafactory.model.Car;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@Repository
public class CarDaoImpl implements CarDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    @PersistenceContext
    private EntityManager em;
    
    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    
    @Override
    public void addCar(Car car) {
        //sessionFactory.getCurrentSession().save(car); // Hibernate version without transaction
    	EntityManager entityManager = emf.createEntityManager();
    	entityManager.getTransaction().begin();
    	entityManager.persist(car);
    	entityManager.flush();
    	entityManager.getTransaction().commit();
    	entityManager.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Car> listCar() {
    	
    	EntityManager entityManager = emf.createEntityManager();
    	entityManager.getTransaction().begin();
    	List<Car> cars = entityManager.createQuery("from Car", Car.class).getResultList();
    	entityManager.getTransaction().commit();
    	entityManager.close();
    	return cars;
        //return sessionFactory.getCurrentSession().createQuery("from Car").list(); // Hibernate version without transaction
    }


}
