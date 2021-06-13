package de.agentlab.ds.pivot;

import de.agentlab.ds.common.StringUtils;
import de.agentlab.ds.table.Table;
import de.agentlab.ds.tree.Tree;
import de.agentlab.ds.tuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static de.agentlab.ds.common.StringUtils.compareWithCollator;

public class PivotTable<T> {

    private Tree<PivotDataItem<String, List<T>>> dataTree;
    private Tree<PivotDataItem<String, Double>> sumTree;
    private Table<String, String, String> table;

    public PivotTable() {
    }

    public Tree<PivotDataItem<String, List<T>>> getDataTree() {
        return dataTree;
    }

    public Tree<PivotDataItem<String, Double>> getSumTree() {
        return sumTree;
    }

    public void partition(List<T> l, Function<T, String>... categoryFns) {
        class local {
            Map<String, List<T>> partitionOnProperty(List<T> l, Function<T, String> pFn) {
                Map<String, List<T>> result = new HashMap<>();

                for (T dataItem : l) {
                    String value = pFn.apply(dataItem);
                    List<T> dataItemList = result.get(value);
                    if (dataItemList == null) {
                        dataItemList = new ArrayList<>();
                        result.put(value, dataItemList);
                    }
                    dataItemList.add(dataItem);
                }
                return result;
            }

            void add(PivotDataItem<String, List<T>> entry, Tree<PivotDataItem<String, List<T>>> result, List<Function<T, String>> pFnList) {
                if (pFnList.size() > 0) {
                    Map<String, List<T>> partition = this.partitionOnProperty(entry.getRight(), pFnList.get(0));

                    for (Map.Entry<String, List<T>> nestedEntry : partition.entrySet()) {
                        PivotDataItem<String, List<T>> data = new PivotDataItem<>(nestedEntry.getKey(), nestedEntry.getValue());
                        result.addChild(entry, data);
                        add(data, result, pFnList.subList(1, pFnList.size()));
                    }
                }
            }
        }
        local local = new local();

        this.dataTree = new Tree<>();
        List<Function<T, String>> pFnList = Arrays.asList(categoryFns);

        if (pFnList.size() > 0) {

            Map<String, List<T>> partition = local.partitionOnProperty(l, pFnList.get(0));

            for (Map.Entry<String, List<T>> entry : partition.entrySet()) {
                PivotDataItem<String, List<T>> data = new PivotDataItem<>(entry.getKey(), entry.getValue());
                this.dataTree.add(data);
                local.add(data, this.dataTree, pFnList.subList(1, pFnList.size()));
            }
        }
    }

    public void sum(Function<T, Double> sumFn) {
        this.sumTree = this.dataTree.copy().map(entry -> {
            double sum = entry.getRight().stream().map(vo -> sumFn.apply(vo)).mapToDouble(Double::doubleValue).sum();
            return new PivotDataItem<>(entry.getLeft(), sum);
        });

        this.toTable();
    }

    public void addSums() {
        class local {
            public String sum(List<String> l) {
                double sum = 0.0;
                boolean oneFound = false;
                for (String val : l) {
                    try {
                        if (val != null) {
                            sum += Double.parseDouble(val);
                            oneFound = true;
                        }
                    } catch (NumberFormatException e) {
                        //ignore
                    }
                }
                if (oneFound) {
                    return String.valueOf(sum);
                } else {
                    return "zzz_<NaN>";
                }
            }
        }
        local local = new local();

        Table<String, String, String> result = new Table<>(this.table);

        for (String rowKey : result.getRowKeys()) {
            List<String> row = result.getRow(rowKey);
            result.put(rowKey, "zzz_Sum", local.sum(row));
        }

        for (String colKey : result.getColKeys()) {
            List<String> col = result.getCol(colKey);
            result.put("zzz_Sum", colKey, local.sum(col));
        }

        this.table = result;
    }

    public List<T> get(String... keys) {
        class local {
            public List<T> find(List<PivotDataItem<String, List<T>>> data, List<String> keyList, Tree<PivotDataItem<String, List<T>>> tree) {
                if (keyList.size() > 0) {
                    String key = keyList.get(0);
                    if (keyList.size() == 1) {
                        for (PivotDataItem<String, List<T>> datum : data) {
                            if (datum.getLeft().equals(key)) {
                                return datum.getRight();
                            }
                        }
                    } else {
                        for (PivotDataItem<String, List<T>> datum : data) {
                            if (datum.getLeft().equals(key)) {
                                return find(tree.getChildren(datum), keyList.subList(1, keyList.size()), tree);
                            }
                        }
                    }
                }

                return null;
            }
        }
        local local = new local();

        List<String> keyList = Arrays.asList(keys);

        List<PivotDataItem<String, List<T>>> roots = this.dataTree.getRoots();

        return local.find(roots, keyList, this.dataTree);
    }

    private void toTable() {
        this.table = new Table<>();
        List<List<PivotDataItem<String, Double>>> branches = this.sumTree.getBranches();

        for (int i = 0; i < branches.size(); i++) {
            List<PivotDataItem<String, Double>> branch = branches.get(i);
            if (branch.size() > 1) {
                for (int j = 0; j < branch.size() - 1; j++) {
                    this.table.put(String.valueOf(i), "____" + j, branch.get(j).getLeft());
                }
                this.table.put(String.valueOf(i), branch.get(branch.size() - 1).getLeft(), String.valueOf(branch.get(branch.size() - 1).getRight()));
            } else {
                this.table.put(String.valueOf(i), "____" + 0, branch.get(0).getLeft());
                this.table.put(String.valueOf(i), "____" + 1, String.valueOf(branch.get(0).getRight()));
            }

        }

        this.squeezeRows();
    }

    public void squeezeRows() {

        class local {

            public Pair<String, Map<String, String>> findMerge(Map<String, String> rowMap, Table<String, String, String> target) {

                for (String targetRowKey : target.getRowKeys()) {
                    Map<String, String> targetRow = target.getRowMap(targetRowKey);
                    boolean matches = true;
                    for (String targetRowMapKey : targetRow.keySet()) {
                        String targetRowValue = targetRow.get(targetRowMapKey);
                        String rowMapValue = rowMap.get(targetRowMapKey);
                        if (rowMapValue != null) {
                            if (!rowMapValue.equals(targetRowValue)) {
                                matches = false;
                            }
                        }
                    }
                    if (matches) {
                        Pair<String, Map<String, String>> merge = new Pair<>(targetRowKey, targetRow);
                        return merge;
                    }
                }

                return null;
            }

            public Map<String, String> merge(Map<String, String> rowMap, Map<String, String> merge) {
                Map<String, String> result = new HashMap<>(merge);
                result.putAll(rowMap);
                return result;
            }
        }
        local local = new local();

        Table<String, String, String> result = new Table<>();

        Set<String> rowKeys = this.table.getRowKeys();

        for (String rowKey : rowKeys) {
            Map<String, String> rowMap = this.table.getRowMap(rowKey);
            Pair<String, Map<String, String>> merge = local.findMerge(rowMap, result);

            if (merge != null) {
                result.putRow(merge.getLeft(), local.merge(rowMap, merge.getRight()));
            } else {
                result.putRow(rowKey, rowMap);
            }
        }

        this.table = result;
    }


    public Comparator<String> getGroupingRowComparator(Table<String, String, String> table) {
        return (rowKey1, rowKey2) -> {
            Map<String, String> rowMap1 = table.getRowMap(rowKey1);
            Map<String, String> rowMap2 = table.getRowMap(rowKey2);
            int i = 0;
            String value1 = rowMap1.get("____" + i);
            String value2 = rowMap2.get("____" + i);
            while (value1.equals(value2)) {
                i++;
                value1 = rowMap1.get("____" + i);
                value2 = rowMap2.get("____" + i);
            }
            return compareWithCollator(value1, value2);
        };
    }

    public String toPrettyTable() {
        return this.table.format(Object::toString, Object::toString, Object::toString, this.getGroupingRowComparator(table), StringUtils::compareWithCollator);
    }
}
