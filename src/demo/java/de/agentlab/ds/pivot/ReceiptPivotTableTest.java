package de.agentlab.ds.pivot;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ReceiptPivotTableTest {

    // https://www.lumeer.io/pivot-table-complete-guide/

    public static void main(String[] args) {
        who_sold_how_many_pizzas();
    }

    public static void who_sold_how_many_pizzas() {

        PivotTable<Receipt> pivotTable = new PivotTable<>();

        Pivot<Receipt> foodCategory = new Pivot<>("Category",
            d -> d.getFoodCategory(),
            d -> d);
        Pivot<Receipt> monthCategory = new Pivot<>("Month",
            d -> "Mon_" + d.getMonth(),
            d -> {
                if (d.startsWith("Mon_")) {
                    return Month.of(Integer.parseInt(d.substring(4))).toString();
                }
                return d;
            });
        Pivot<Receipt> weekdayCategory = new Pivot<>("Weekday",
            d -> "Day_" + d.getWeekday(),
            d -> {
                if (d.startsWith("Day_")) {
                    return DayOfWeek.of(Integer.parseInt(d.substring(4))).toString();
                }
                return d;
            });

        List<Pivot<Receipt>> pivotList = new ArrayList<>();
        pivotList.add(foodCategory);
        pivotList.add(monthCategory);
        pivotList.add(weekdayCategory);

        pivotTable.partition(Receipt.getData(), pivotList);
        pivotTable.sum(d -> 1.0);

        pivotTable.addSums();

        System.out.println(pivotTable.toPrettyTable(pivotList));

        List<Receipt> melissa = pivotTable.get("Melissa", "Tuna");
        System.out.println(melissa);
    }
}
