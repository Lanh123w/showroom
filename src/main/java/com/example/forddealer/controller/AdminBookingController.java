package com.example.forddealer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forddealer.model.Booking;
import com.example.forddealer.service.BookingService;

@Controller
@RequestMapping("/admin/bookings")
public class AdminBookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAll();
        model.addAttribute("bookings", bookings);
        return "admin/booking-list";
    }

    @GetMapping("/edit/{id}")
    public String editBookingForm(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getById(id);
        model.addAttribute("booking", booking);
        return "admin/edit-booking";
    }

    @PostMapping("/update")
    public String updateBooking(@ModelAttribute Booking booking) {
        Booking existing = bookingService.getById(booking.getId());
        existing.setDate(booking.getDate());
        bookingService.save(existing);
        return "redirect:/admin/bookings";
    }
    

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return "redirect:/admin/bookings";
    }
}
