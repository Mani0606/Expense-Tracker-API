package com.personal.project.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.personal.project.model.CustomPurchase;
import com.personal.project.model.Purchase;
import com.personal.project.service.Purchase_Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    Purchase_Service purchase_Service;

    @PostMapping("/purchase_info")
    public String postMethodName(@RequestBody CustomPurchase custom_purchase) {
        int rows = purchase_Service.Save_Purchase(custom_purchase);
        return Integer.toString(rows) + " rows has been effected";
    }

    @GetMapping("/PresentWeek")
    public List<CustomPurchase> present_week() {
        return purchase_Service.Last_7_days();
    }

    @GetMapping("/View_purchases")
    public ResponseEntity<List<CustomPurchase>> View_Purchases() {
        return new ResponseEntity<>(purchase_Service.View_Purchases(), HttpStatus.OK);
    }

    @GetMapping("/Purchase/{id}")
    public ResponseEntity<CustomPurchase> Spefici_Purchase(@PathVariable("id") int id) {
        CustomPurchase purchase = purchase_Service.Spefici_Purchase(id);
        return new ResponseEntity<>(purchase, HttpStatus.OK);

    }

    @PutMapping("/UpdatePurchase/{id}")
    public ResponseEntity<String> putMethodName(@PathVariable int id, @RequestBody CustomPurchase customPurchase) {
        CustomPurchase purchase = purchase_Service.Spefici_Purchase(id);
        if (purchase != null) {
            int rows = purchase_Service.Update_Purchase(customPurchase, id);
            return new ResponseEntity<>(Integer.toString(rows) + " row has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product Not Found", HttpStatus.CONFLICT);
    }

    @DeleteMapping("/deletePurchase/{id}")
    public ResponseEntity<String> deletePurchase(@PathVariable("id") int id) {
        CustomPurchase purchase = purchase_Service.Spefici_Purchase(id);
        if (purchase != null) {
            int rows = purchase_Service.delete_purhcase(id);
            return new ResponseEntity<>(Integer.toString(rows) + " row has been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product Not Found", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/theseDate")
    public ResponseEntity<?> ParticularDate(@RequestParam("date") LocalDate date) {
        if (purchase_Service.isCorrectdate(date)) {
            List<CustomPurchase> purchase = purchase_Service.ParticulatDate(date);
            if (purchase != null) {
                return new ResponseEntity<>(purchase, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Empty Set", HttpStatus.OK);
            }
        }

        else {
            return new ResponseEntity<>("Date is Invalid", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/fromto")
    public ResponseEntity<?> start_to_end(@RequestParam("start") LocalDate startDate,
            @RequestParam("end") LocalDate endDate) {
        if (purchase_Service.isCorrectdate(startDate) && purchase_Service.isCorrectdate(endDate)) {
            List<CustomPurchase> purchases = purchase_Service.start_to(startDate, endDate);
            if (purchases != null) {
                return new ResponseEntity<>(purchases, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Empty Set", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Date is Invalid", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/allPurchase")
    public List<CustomPurchase> all_Purchase(@RequestParam String param) {
        return purchase_Service.all_the_purchase();
    }

}
