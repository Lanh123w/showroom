package com.example.forddealer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.forddealer.model.Car;
import com.example.forddealer.model.CarColor;
import com.example.forddealer.service.CarColorService;
import com.example.forddealer.service.CarService;

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
            case "đen", "đen bóng", "đen nhám" -> "#000000";
            case "trắng", "trắng ngọc trai", "trắng sứ" -> "#f8f8f8";
            case "xanh rêu" -> "#556b2f";
            case "xanh lá", "xanh lá cây" -> "#228b22";
            case "xanh dương", "xanh biển", "xanh navy" -> "#1e90ff";
            case "xanh da trời" -> "#87ceeb";
            case "xanh ngọc" -> "#00ced1";
            case "đỏ", "đỏ thể thao", "đỏ tươi", "đỏ đô" -> "#ff0000";
            case "đỏ mận" -> "#800000";
            case "xám", "xám bạc", "xám lông chuột" -> "#808080";
            case "vàng", "vàng cát", "vàng đồng" -> "#ffd700";
            case "cam", "cam đất" -> "#ff8c00";
            case "nâu", "nâu đất" -> "#8b4513";
            case "hồng", "hồng pastel" -> "#ffc0cb";
            case "tím", "tím than", "tím pastel" -> "#800080";
            case "bạc" -> "#c0c0c0";
            case "be", "màu kem", "kem sữa" -> "#f5f5dc";
            case "xanh lục bảo" -> "#50c878";
            default -> "#cccccc"; // màu mặc định nếu không khớp
        };
    }
    
}
