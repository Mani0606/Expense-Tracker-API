package com.personal.project.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.project.model.CustomPurchase;
import com.personal.project.model.Income;
import com.personal.project.repository.Balance_repo;

@Service
public class Income_Service {

    @Autowired
    private Balance_repo balance_repo;

    public int record_income(CustomPurchase custom_purchase) {
        Income income = new Income();
        income.setCredit_name(custom_purchase.getPurchase_name());
        income.setCredit_date(custom_purchase.getPurchase_date());
        income.setDecription(custom_purchase.getDecription());
        income.setCredit_cost(custom_purchase.getPurchase_cost());
        int rows = balance_repo.add_info_of_income(income);
        return rows;
    }

    public List<CustomPurchase> Last_7_days() {
        return balance_repo.Last_7_days_incomee();
    }

    public List<CustomPurchase> ViewIncome() {
        return balance_repo.View_Income();
    }

    public CustomPurchase Spefici_Income(int id) {
        if ((id > 0 && id < Integer.MAX_VALUE)) {
            return balance_repo.Spefici_Income(id);
        }
        return null;
    }

    public int UpdateIncome(CustomPurchase customPurchase, int id) {
        CustomPurchase income = balance_repo.Spefici_Income(id);
        if (income != null) {
            return balance_repo.update_income(customPurchase, id);
        } else {
            return 0;
        }
    }

    public int DeleteIncome(int id) {
        CustomPurchase income = balance_repo.Spefici_Income(id);
        if (income != null) {
            return balance_repo.delete_income(id);
        } else {
            return 0;
        }
    }

    public boolean isCorrectdate(LocalDate date) {
        try {
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public List<CustomPurchase> ParticulatDate(LocalDate date) {

        return balance_repo.ParticularDate(date);

    }

    public List<CustomPurchase> Start_to_end(LocalDate start, LocalDate end) {
        YearMonth currentMonth = YearMonth.of(2024, Month.AUGUST);
        return balance_repo.start_to_end_income(start, end);
    }

    public List<CustomPurchase> all_the_income() {
        return balance_repo.all_the_income();
    }

}
