package agh.bit.ideafactory.dao;

import agh.bit.ideafactory.model.Car;

import java.util.List;

public interface CarDao {
	
    public void addCar(Car car);

    public List<Car> listCar();
    
}
