package de.agentlab.ds.pivot;

public class ReceiptPivotTableTest {

    // https://www.lumeer.io/pivot-table-complete-guide/

    public static void main(String[] args) {
        who_sold_how_many_pizzas();
    }

    public static void who_sold_how_many_pizzas() {

        PivotTable<Receipt> pivotTable = new PivotTable<>();

//        pivotTable.partition(Receipt.getData(), d -> d.getEmployee(), d -> d.getPizza(), d -> d.getMonth());
        pivotTable.partition(Receipt.getData(), d -> d.getEmployee(), d -> d.getPizza(), d -> d.getMonth());
        pivotTable.sum(d -> 1.0);

        pivotTable.addSums();

        System.out.println(pivotTable.toPrettyTable());
    }
}
