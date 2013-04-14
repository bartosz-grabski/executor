package agh.bit.ideafactory.serviceimpl;

import agh.bit.ideafactory.dao.CarDao;
import agh.bit.ideafactory.model.Car;
import agh.bit.ideafactory.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarDao carDao;

    @Transactional
    public void addCar(Car c) {
        carDao.addCar(c);
    }

    @Transactional
    public List<Car> listCar() {
        return carDao.listCar();
    }


}
