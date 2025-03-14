package com.personal.project.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.project.model.CustomPurchase;
import com.personal.project.model.Income;
import com.personal.project.service.Income_Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/income")
public class IncomeController {
    @Autowired
    private Income_Service income_service;

    @PostMapping("/add_credit")
    public String postMethodName(@RequestBody CustomPurchase custom_purchase) {
        int rows = income_service.record_income(custom_purchase);
        return Integer.toString(rows) + " rows has been effected";
    }

    @GetMapping("/PresentWeek_income")
    public List<CustomPurchase> PresentWeek_income() {
        return income_service.Last_7_days();
    }

    @GetMapping("/View_income")
    public List<CustomPurchase> ViewIncome() {
        return income_service.ViewIncome();
    }

    @GetMapping("/{id}")
    public CustomPurchase Spefici_Income(@PathVariable("id") int id) {
        return income_service.Spefici_Income(id);
    }

    @PutMapping("/Update_income/{id}")
    public ResponseEntity<String> UpdateIncome(@PathVariable("id") int id, @RequestBody CustomPurchase income) {
        int rows = income_service.UpdateIncome(income, id);
        if (rows > 0) {
            return new ResponseEntity<>(Integer.toString(rows) + " rows has been updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Data Not Found", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/Delete_income/{id}")
    public ResponseEntity<String> DeleteIncome(@PathVariable("id") int id) {
        int rows = income_service.DeleteIncome(id);
        if (rows > 0) {
            return new ResponseEntity<>(Integer.toString(rows) + " rows has been updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Data Not Found", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/theseDate")
    public ResponseEntity<?> ParticularDate(@RequestParam("date") LocalDate date) {
        if (income_service.isCorrectdate(date)) {
            List<CustomPurchase> income = income_service.ParticulatDate(date);
            if (income != null) {
                return new ResponseEntity<>(income, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        }

        else {
            return new ResponseEntity<>("Date is Invalid", HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/fromto")
    public ResponseEntity<?> ParticularDate(@RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {
        if (income_service.isCorrectdate(start) && income_service.isCorrectdate(end)) {
            List<CustomPurchase> income = income_service.Start_to_end(start, end);
            if (income != null) {
                return new ResponseEntity<>(income, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        }

        else {
            return new ResponseEntity<>("Date is Invalid", HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/all_income")
    public ResponseEntity<?> all_income() {
        List<CustomPurchase> income = income_service.all_the_income();
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

}
