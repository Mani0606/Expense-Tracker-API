package com.personal.project.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.personal.project.model.CustomPurchase;
import com.personal.project.model.Purchase;
import com.personal.project.repository.Balance_repo;

@Service
public class Purchase_Service {

    @Autowired
    Balance_repo balance_repo;

    public int Save_Purchase(CustomPurchase custom_purchase) {
        // Add Validation Code.
        Purchase purchase = new Purchase();
        purchase.setPurchase_name(custom_purchase.getPurchase_name());
        purchase.setDecription(custom_purchase.getDecription());
        purchase.setPurcahse_date(custom_purchase.getPurchase_date());
        purchase.setPurchase_cost(custom_purchase.getPurchase_cost());
        int rows = balance_repo.add_info_of_Purchase(purchase);
        return rows;
    }

    public List<CustomPurchase> Last_7_days() {
        return balance_repo.Last_7_days_purchase();
    }

    public List<CustomPurchase> View_Purchases() {
        return balance_repo.View_Purchases();
    }

    public CustomPurchase Spefici_Purchase(int id) {

        if ((id > 0 && id < Integer.MAX_VALUE)) {
            return balance_repo.Spefici_Purchase(id);
        }
        return null;
    }

    public int Update_Purchase(CustomPurchase customPurchase, int id) {
        return balance_repo.update_purchase(customPurchase, id);
    }

    public int delete_purhcase(int id) {
        return balance_repo.delete_purhcase(id);
    }

    public boolean isCorrectdate(LocalDate date) {
        try {
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public List<CustomPurchase> ParticulatDate(LocalDate date) {

        return balance_repo.ParticularDate_Purchase(date);

    }

    public List<CustomPurchase> start_to(LocalDate start, LocalDate end) {
        return balance_repo.start_to_end_purchase(start, end);
    }

    public List<CustomPurchase> all_the_purchase() {
        return balance_repo.all_the_purchase();
    }

}
