package agh.bit.ideafactory.DAOImpl;

import java.util.List;

import agh.bit.ideafactory.DAO.CarDAO;
import agh.bit.ideafactory.model.*;
 
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CarDAOImpl implements CarDAO{

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
