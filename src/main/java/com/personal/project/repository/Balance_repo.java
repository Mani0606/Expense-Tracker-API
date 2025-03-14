package com.personal.project.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.personal.project.config.UserUtil;
import com.personal.project.model.CustomPurchase;
import com.personal.project.model.Income;
import com.personal.project.model.Purchase;

@Repository
public class Balance_repo {

    @Autowired
    private JdbcTemplate jdbctemp;

    @Autowired
    private UsersRepo user_repo;
    int user_id;

    public int add_info_of_Purchase(Purchase purchase) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        purchase.setUser_id(user_id);
        String query = "INSERT INTO Purchase (User_id,Purchase_name,Purchase_date,Purchase_description,Purchase_Cost) VALUES(?,?,?,?,?)";
        int rows = jdbctemp.update(query, purchase.getUser_id(), purchase.getPurchase_name(),
                purchase.getPurcahse_date(), purchase.getDecription(),
                purchase.getPurchase_cost());
        return rows;
    }

    public int add_info_of_income(Income income) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        income.setUser_id(user_id);
        String query = "INSERT INTO Income (User_id,Credit_name,Credit_date,Credit_description,Credit_amount) VALUES(?,?,?,?,?)";
        int rows = jdbctemp.update(query, income.getUser_id(), income.getCredit_name(), income.getCredit_date(),
                income.getDecription(), income.getCredit_cost());
        return rows;
    }

    @SuppressWarnings("deprecation")
    public int get_balance() {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query1 = "select SUM(Purchase_Cost) from Purchase where User_id=(?)";
        String query2 = "select SUM(Credit_amount) from Income where User_id=(?)";

        List<Integer> purchase = jdbctemp.queryForList(query1, new Object[] { this.user_id }, Integer.class);
        List<Integer> income = jdbctemp.queryForList(query2, new Object[] { this.user_id }, Integer.class);
        int sum1 = 0, sum2 = 0;

        for (int a : purchase) {
            sum1 = a + sum1;
        }
        for (int a : income) {
            sum2 = a + sum2;
        }
        return sum2 - sum1;

    }

    public List<CustomPurchase> Last_7_days_purchase() {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        int days = 7;
        LocalDate today = LocalDate.now();
        LocalDate lastDate = IntStream.rangeClosed(1, days)
                .mapToObj(today::minusDays)
                .min(LocalDate::compareTo)
                .orElse(null);

        String query = "Select Purchase_name,Purchase_date,Purchase_description,Purchase_Cost FROM Purchase where User_id = (?) AND Purchase_date>=(?)";
        @SuppressWarnings("deprecation")
        List<CustomPurchase> last_7 = jdbctemp.query(query, new Object[] { this.user_id, lastDate },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Purchase_name"),
                        rs.getObject("Purchase_date", LocalDate.class),
                        rs.getString("Purchase_description"),
                        rs.getInt("Purchase_Cost")));
        return last_7;
    }

    public List<CustomPurchase> Last_7_days_incomee() {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        int days = 7;
        LocalDate today = LocalDate.now();
        LocalDate lastDate = IntStream.rangeClosed(1, days)
                .mapToObj(today::minusDays)
                .min(LocalDate::compareTo)
                .orElse(null);

        String query = "Select Credit_name,Credit_date,Credit_description,Credit_amount FROM Income where User_id = (?) AND Credit_date>=(?)";
        @SuppressWarnings("deprecation")
        List<CustomPurchase> last_7 = jdbctemp.query(query, new Object[] { this.user_id, lastDate },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Credit_name"),
                        rs.getObject("Credit_date", LocalDate.class),
                        rs.getString("Credit_description"),
                        rs.getInt("Credit_amount")));
        return last_7;
    }

    public List<CustomPurchase> View_Purchases() {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "Select Purchase_name,Purchase_date,Purchase_description,Purchase_Cost FROM Purchase where User_id = (?)";
        @SuppressWarnings("deprecation")
        List<CustomPurchase> Purchases = jdbctemp.query(query, new Object[] { this.user_id },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Purchase_name"),
                        rs.getObject("Purchase_date", LocalDate.class),
                        rs.getString("Purchase_description"),
                        rs.getInt("Purchase_Cost")));

        return Purchases;

    }

    public List<CustomPurchase> View_Income() {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "Select Credit_name,Credit_date,Credit_description,Credit_amount FROM Income where User_id = (?)";
        @SuppressWarnings("deprecation")
        List<CustomPurchase> Purchases = jdbctemp.query(query, new Object[] { this.user_id },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Credit_name"),
                        rs.getObject("Credit_date", LocalDate.class),
                        rs.getString("Credit_description"),
                        rs.getInt("Credit_amount")));

        return Purchases;

    }

    @SuppressWarnings("deprecation")
    public CustomPurchase Spefici_Purchase(int id) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "Select Purchase_name,Purchase_date,Purchase_description,Purchase_Cost FROM Purchase where User_id = (?) AND Purchase_id=(?)";
        CustomPurchase purchase = jdbctemp.queryForObject(query, new Object[] { this.user_id, id },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Purchase_name"),
                        rs.getObject("Purchase_date", LocalDate.class),
                        rs.getString("Purchase_description"),
                        rs.getInt("Purchase_Cost")));

        return purchase;
    }

    @SuppressWarnings("deprecation")
    public CustomPurchase Spefici_Income(int id) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "Select Credit_name,Credit_date,Credit_description,Credit_amount FROM Income where User_id = (?) AND Credit_id=(?)";
        @SuppressWarnings("deprecation")
        CustomPurchase income = jdbctemp.queryForObject(query, new Object[] { this.user_id, id },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Credit_name"),
                        rs.getObject("Credit_date", LocalDate.class),
                        rs.getString("Credit_description"),
                        rs.getInt("Credit_amount")));

        return income;
    }

    public int update_purchase(CustomPurchase customPurchase, int id) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "Update Purchase SET Purchase_name=(?),Purchase_date=(?),Purchase_description=(?),Purchase_Cost=(?) WHERE User_id=(?) AND Purchase_id=(?)";
        int rows = jdbctemp.update(query, customPurchase.getPurchase_name(), customPurchase.getPurchase_date(),
                customPurchase.getDecription(), customPurchase.getPurchase_cost(), this.user_id, id);
        return rows;
    }

    public int delete_purhcase(int id) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "DELETE FROM Purchase WHERE User_id = (?) AND Purchase_id=(?)";
        int rows = jdbctemp.update(query, this.user_id, id);
        return rows;
    }

    public int update_income(CustomPurchase customPurchase, int id) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "Update Income SET Credit_name=(?),Credit_date=(?),Credit_description=(?),Credit_amount=(?) WHERE User_id=(?) AND Credit_id=(?)";
        int rows = jdbctemp.update(query, customPurchase.getPurchase_name(), customPurchase.getPurchase_date(),
                customPurchase.getDecription(), customPurchase.getPurchase_cost(), this.user_id, id);
        return rows;
    }

    public int delete_income(int id) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "DELETE FROM Income WHERE User_id = (?) AND Credit_id=(?)";
        int rows = jdbctemp.update(query, this.user_id, id);
        return rows;
    }

    @SuppressWarnings("deprecation")
    public List<CustomPurchase> ParticularDate(LocalDate date) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "SELECT Credit_name,Credit_date,Credit_description,Credit_amount FROM income WHERE User_id=(?) AND Credit_date=(?)";
        List<CustomPurchase> income = jdbctemp.query(query, new Object[] { this.user_id, date },
                (rs, rsNum) -> new CustomPurchase(
                        rs.getString("Credit_name"),
                        rs.getObject("Credit_date", LocalDate.class),
                        rs.getString("Credit_description"),
                        rs.getInt("Credit_amount")));

        return income;
    }

    @SuppressWarnings("deprecation")
    public List<CustomPurchase> ParticularDate_Purchase(LocalDate date) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "Select Purchase_name,Purchase_date,Purchase_description,Purchase_Cost FROM Purchase where User_id = (?) AND Purchase_date=(?)";
        List<CustomPurchase> purchase = jdbctemp.query(query, new Object[] { this.user_id, date },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Purchase_name"),
                        rs.getObject("Purchase_date", LocalDate.class),
                        rs.getString("Purchase_description"),
                        rs.getInt("Purchase_Cost")));

        return purchase;
    }

    @SuppressWarnings("deprecation")
    public List<CustomPurchase> start_to_end_purchase(LocalDate start, LocalDate end) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "SELECT Purchase_name,Purchase_date,Purchase_description,Purchase_Cost FROM Purchase WHERE User_id=(?) AND Purchase_date>=(?) AND Purchase_date<=(?)";
        List<CustomPurchase> purchase = jdbctemp.query(query, new Object[] { this.user_id, start, end },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Purchase_name"),
                        rs.getObject("Purchase_date", LocalDate.class),
                        rs.getString("Purchase_description"),
                        rs.getInt("Purchase_Cost")));
        return purchase;
    }

    @SuppressWarnings("deprecation")
    public List<CustomPurchase> start_to_end_income(LocalDate start, LocalDate end) {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "SELECT Credit_name,Credit_date,Credit_description,Credit_amount FROM income WHERE User_id=(?) AND Credit_date>=(?) AND Credit_date<=(?)";
        List<CustomPurchase> income = jdbctemp.query(query, new Object[] { this.user_id, start, end },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Credit_name"),
                        rs.getObject("Credit_date", LocalDate.class),
                        rs.getString("Credit_description"),
                        rs.getInt("Credit_amount")));
        return income;
    }

    @SuppressWarnings("deprecation")
    public List<CustomPurchase> all_the_income() {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "SELECT Credit_name,Credit_date,Credit_description, SUM(Credit_amount) FROM income WHERE User_id=(?) GROUP BY Credit_name";
        List<CustomPurchase> income = jdbctemp.query(query, new Object[] { this.user_id },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Credit_name"),
                        rs.getObject("Credit_date", LocalDate.class),
                        rs.getString("Credit_description"),
                        rs.getInt("Credit_amount")));
        return income;
    }

    @SuppressWarnings("deprecation")
    public List<CustomPurchase> all_the_purchase() {
        this.user_id = user_repo.extract_user_id(UserUtil.getLoggedInUserEmail());
        String query = "SELECT Purchase_name,Purchase_date,Purchase_description,Purchase_Cost FROM Purchase WHERE User_id=(?) GROUP BY Purchase_name";
        List<CustomPurchase> purchase = jdbctemp.query(query, new Object[] { this.user_id },
                (rs, rowNum) -> new CustomPurchase(
                        rs.getString("Purchase_name"),
                        rs.getObject("Purchase_date", LocalDate.class),
                        rs.getString("Purchase_description"),
                        rs.getInt("Purchase_Cost")));
        return purchase;
    }

}
