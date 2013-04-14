package agh.bit.ideafactory.daoimpl;

import agh.bit.ideafactory.dao.CarDao;
import agh.bit.ideafactory.model.Car;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarDaoImpl implements CarDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCar(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Car> listCar() {
        return sessionFactory.getCurrentSession().createQuery("from Car").list();
    }


}
