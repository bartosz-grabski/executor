package agh.bit.ideafactory.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import agh.bit.ideafactory.model.Car;
import agh.bit.ideafactory.service.CarService;

@Controller
@RequestMapping("/")
public class CarController {

	@Autowired
    private CarService carService;
 
    @RequestMapping("/list")
    public String listCars(Map<String, Object> map) {
 
        map.put("car", new Car());
        map.put("carList", carService.listCar());
 
        return "car";
    }
 
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCar(@ModelAttribute("Car") Car car, BindingResult result) {
 
        carService.addCar(car);
 
        return "redirect:/list";
    }
 

}
