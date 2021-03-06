package de.agentlab.ds.pivot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Receipt {

    private String employee;
    private String dateAndTime;
    private String pizza;
    private Double ammount;

    public Receipt(String employee, String dateAndTime, String pizza, Double ammount) {
        this.employee = employee;
        this.dateAndTime = dateAndTime;
        this.pizza = pizza;
        this.ammount = ammount;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getPizza() {
        return pizza;
    }

    public void setPizza(String pizza) {
        this.pizza = pizza;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public int getMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma", Locale.US);
        LocalDateTime parse = LocalDateTime.parse(this.dateAndTime, formatter);
        return parse.getMonth().getValue();
    }

    public int getWeekday() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma", Locale.US);
        LocalDateTime parse = LocalDateTime.parse(this.dateAndTime, formatter);
        return parse.getDayOfWeek().getValue();
    }

    public String getFoodCategory() {
        if (this.pizza.equals("Margherita")) {
            return "Veg";
        }
        if (this.pizza.equals("Quattro Stagioni") || this.pizza.equals("Salami")) {
            return "Meat";
        }
        if (this.pizza.equals("Tuna")) {
            return "Fish";
        }
        return "?";
    }

    @Override
    public String toString() {
        return "Receipt{" +
            "employee='" + employee + '\'' +
            ", dateAndTime='" + dateAndTime + '\'' +
            ", pizza='" + pizza + '\'' +
            ", ammount=" + ammount +
            '}';
    }

    public static List<Receipt> getData() {
        List<Receipt> result = new ArrayList<>();
        result.add(new Receipt("Melissa", "2019/05/26 01:17PM", "Margherita", 6.03));
        result.add(new Receipt("Melissa", "2019/05/26 01:17PM", "Margherita", 6.03));
        result.add(new Receipt("Sylvia", "2019/05/27 01:19PM", "Quattro Stagioni", 6.74));
        result.add(new Receipt("Juliette", "2019/05/28 02:23PM", "Salami", 6.38));
        result.add(new Receipt("Melissa", "2019/05/29 02:36PM", "Tuna", 6.91));
        result.add(new Receipt("Sylvia", "2019/06/01 02:41PM", "Margherita", 6.03));
        result.add(new Receipt("Juliette", "2019/06/10 02:49PM", "Quattro Stagioni", 6.74));
        result.add(new Receipt("Melissa", "2019/06/11 02:57PM", "Salami", 6.38));
        result.add(new Receipt("Sylvia", "2019/06/12 03:01PM", "Tuna", 6.91));
        result.add(new Receipt("Juliette", "2019/06/26 03:02PM", "Margherita", 6.03));
        result.add(new Receipt("Sylvia", "2019/07/16 03:11PM", "Quattro Stagioni", 6.74));
        result.add(new Receipt("Juliette", "2019/07/17 03:26PM", "Salami", 6.38));
        result.add(new Receipt("Melissa", "2019/07/18 03:28PM", "Tuna", 6.91));
        result.add(new Receipt("Sylvia", "2019/07/19 03:31PM", "Quattro Stagioni", 6.74));

        return result;
    }
}

