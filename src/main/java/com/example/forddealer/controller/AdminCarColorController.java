package com.example.forddealer.controller;

import com.example.forddealer.model.Car;
import com.example.forddealer.model.CarColor;
import com.example.forddealer.service.CarColorService;
import com.example.forddealer.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/colors")
public class AdminCarColorController {

    private final CarColorService carColorService;
    private final CarService carService;

    @Autowired
    public AdminCarColorController(CarColorService carColorService, CarService carService) {
        this.carColorService = carColorService;
        this.carService = carService;
    }

    @GetMapping
    public String showColorPage(Model model) {
        model.addAttribute("color", new CarColor());
        model.addAttribute("colors", carColorService.getAllColors());
        model.addAttribute("cars", carService.getAllCars());
        return "admin/admin-color";
    }

    @GetMapping("/edit/{id}")
    public String editColor(@PathVariable Long id, Model model) {
        CarColor color = carColorService.getColorById(id).orElse(new CarColor());
        model.addAttribute("color", color);
        model.addAttribute("colors", carColorService.getAllColors());
        model.addAttribute("cars", carService.getAllCars());
        return "admin/admin-color";
    }

    @PostMapping("/save")
    public String saveColor(@ModelAttribute CarColor color) {
        color.setColorCode(mapColorNameToCode(color.getName()));
        carColorService.saveColor(color);
        return "redirect:/admin/colors";
    }

    @GetMapping("/delete/{id}")
    public String deleteColor(@PathVariable Long id) {
        CarColor color = carColorService.getColorById(id).orElse(null);
        if (color != null) {
            for (Car car : carService.getAllCars()) {
                if (car.getColors().contains(color)) {
                    car.getColors().remove(color);
                    carService.save(car);
                }
            }
            carColorService.deleteColor(id);
        }
        return "redirect:/admin/colors";
    }

    @PostMapping("/mapping/save")
    public String saveMapping(@RequestParam Long carId, @RequestParam Long colorId) {
        carService.addColorToCar(carId, colorId);
        return "redirect:/admin/colors";
    }

    @GetMapping("/mapping/delete")
    public String deleteMapping(@RequestParam Long carId, @RequestParam Long colorId) {
        carService.removeColorFromCar(carId, colorId);
        return "redirect:/admin/colors";
    }

    private String mapColorNameToCode(String name) {
        if (name == null) return "#cccccc";
        String lower = name.trim().toLowerCase();
        return switch (lower) {
            case "đen", "đen bóng" -> "#000000";
            case "trắng", "trắng ngọc trai" -> "#f8f8f8";
            case "xanh rêu" -> "#556b2f";
            case "đỏ", "đỏ thể thao" -> "#ff0000";
            case "xám" -> "#808080";
            case "xanh dương" -> "#1e90ff";
            case "vàng" -> "#ffd700";
            default -> "#cccccc";
        };
    }
}
