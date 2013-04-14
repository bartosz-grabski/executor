package agh.bit.ideafactory.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.model.Car;
import agh.bit.ideafactory.service.CarService;
import agh.bit.ideafactory.DAO.*;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
    private CarDAO CarDAO;
     
    @Transactional
    public void addCar(Car c) {
        CarDAO.addCar(c);
    }
 
    @Transactional
    public List<Car> listCar() {
 
        return CarDAO.listCar();
    }

 
}
