/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kna
 */
public class TableService {
    
    public static boolean isRowEmpty(DefaultTableModel tableModel, int row) {
        for (int i = tableModel.getColumnCount()- 1; i >= 0; i--) {
            if (tableModel.getValueAt(row, i) != null) {
                return false;
            }
        }
        return true;
    }

    public static double getSumOfColumn(JTable table , int column){
        double sum = 0;
        for(int i = table.getRowCount() - 1 ; i>=0; i--){
            sum += Double.valueOf(table.getValueAt(i, column).toString());
        }
        return sum;
    }
    public static boolean isValuePresentAtColumn(DefaultTableModel tableModel, int column, String value) {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            if (value.equals(tableModel.getValueAt(i, column).toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValuePresentInUnselectedRowsAtColumn(JTable table, int column, String value) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            if (tableModel.getValueAt(i, column) != null) {
                if (value.equals(tableModel.getValueAt(i, column).toString()) && table.getSelectedRow() != i) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValuePresent(DefaultTableModel tableModel, Object value) {

        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                if (value.equals(tableModel.getValueAt(i, j).toString())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void setValueToEmptyColumn(DefaultTableModel tableModel, int column, Object value) {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            if (tableModel.getValueAt(i, column) == null) {
                tableModel.setValueAt(value, i, column);
            }
        }
    }

    public static void deleteRowWithEmptyColumn(DefaultTableModel tableModel, int column) {
      
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            if (tableModel.getValueAt(i, column) == null) {
                tableModel.removeRow(i);
            }
        }
    }

    public static void deleteRowWithEmptyColumns(DefaultTableModel tableModel, int[] columns) {

        boolean isRemovable = true;
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            for (int j = 0; j < columns.length; j++) {
                if (!(tableModel.getValueAt(i, columns[j]) == null)) {
                    isRemovable = false;
                }
            }
            if (isRemovable) {
                tableModel.removeRow(i);
            }
            isRemovable = true;
        }
    }

    public static void deleteRowWithColumnValue(DefaultTableModel tableModel, int column, Object value) {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            if (value.equals(tableModel.getValueAt(i, column))) {
                tableModel.removeRow(i);
            }

        }
    }

    public static void deleteRowWithColumnsValue(DefaultTableModel tableModel, int[] columns, Object value) {

        boolean isRemovable = true;
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            for (int j = 0; j < columns.length; j++) {
                if (!value.equals(tableModel.getValueAt(i, columns[j]))) {
                    isRemovable = false;
                }
            }
            if (isRemovable) {
                tableModel.removeRow(i);
            }
            isRemovable = true;
        }
    }

    public static void deleteAllRows(DefaultTableModel tableModel) {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    public static void deleteSelectedRows(JTable table) {
        int[] selectedRows = table.getSelectedRows();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        if (!table.getSelectionModel().isSelectionEmpty()) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                tableModel.removeRow(selectedRows[i]);
            }
        }
    }

    public static void setSelectedRowEmpty(JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (int i = tableModel.getColumnCount() - 1; i >= 0; i--) {
            tableModel.setValueAt(null, table.getSelectedRow(), i);
        }
    }

    public static boolean isTableEmpty(JTable table) {
        if (table.getRowCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
