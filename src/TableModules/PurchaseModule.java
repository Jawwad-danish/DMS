/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModules;

import DataGateway.ProductDataGateway;
import DataGateway.PurchaseDataGateway;
import DataGateway.VendorDataGateway;
import static TableModules.SaleModule.COLUMN_EXTRA_DISC;
import static TableModules.SaleModule.COLUMN_FIXED_DISC;
import static TableModules.SaleModule.COLUMN_ITEM_ID;
import static TableModules.SaleModule.COLUMN_RATE;
import static TableModules.SaleModule.COLUMN_REPLACE;
import static TableModules.SaleModule.COLUMN_SOLD_QTY;
import Utilities.TableService;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jdk.nashorn.internal.scripts.JO;

/**
 *
 * @author kna
 */
public class PurchaseModule {

    public PurchaseModule() {

        purchaseDataGateway_ = PurchaseDataGateway.getInstance();
        vendorDataGateway_ = VendorDataGateway.getInstance();
        productDataGateway_ = ProductDataGateway.getInstance();

    }

    public static PurchaseModule getInstance() {

        if (purchaseModule_ == null) {
            purchaseModule_ = new PurchaseModule();
        }
        return purchaseModule_;
    }

    public void submitPurchase(JTable tablePurchaseItem,
            Double Amount,
            Date purchaseDate,
            String vendorName) {

        int vendorID;
        int purchaseID;
        int productID;
        int quantity;
        int replace;
        double amount;

        try{
        vendorID = getVendorID(vendorName);

        // Inserting purchase
        purchaseDataGateway_.insertPurchase(purchaseDate, vendorID, Amount);

        purchaseID = getLastPurchaseID();

        // Inserting purchaseDetail
        for (int i = 0; i < tablePurchaseItem.getRowCount(); i++) {
            productID = Integer.valueOf(tablePurchaseItem.getValueAt(i, COLUMN_ITEM_ID).toString());
            quantity = Integer.valueOf(tablePurchaseItem.getValueAt(i, COLUMN_SOLD_QTY).toString());
            replace = Integer.valueOf(tablePurchaseItem.getValueAt(i, COLUMN_REPLACE).toString());
            amount = quantity * Double.valueOf(tablePurchaseItem.getValueAt(i, COLUMN_RATE).toString());
            purchaseDataGateway_.insertPurchaseDetail(purchaseID, productID, quantity, replace, amount);
        }

        JOptionPane.showMessageDialog(null, "Purchase Submission SUCCESSFULL !");
        
        purchaseDataGateway_.commit();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Purchase Submission FAILED !");
        }
        
    }

    public void fillVendorCbox(JComboBox<String> cboxVendorName) {

        try {
            result_ = vendorDataGateway_.getAll();

            while (result_.next()) {
                cboxVendorName.addItem(result_.getString("VENDOR_NAME"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void fetchProductDetailsToPurchaseItemTable(String vendorName, JTable tablePurchaseItem) {

        int vendorID = -1;
        DefaultTableModel tableModel = (DefaultTableModel) tablePurchaseItem.getModel();
        Vector<Integer> productIDs = new Vector();

        try {
            result_ = vendorDataGateway_.getVendor(vendorName);

            if (result_.next()) {
                vendorID = result_.getInt("VENDOR_ID");
            }

            result_ = productDataGateway_.getProduct(vendorID);

            while (result_.next()) {
                productIDs.add(result_.getInt("PRODUCT_ID"));
            }

            TableService.deleteAllRows(tableModel);

            for (int i = 0; i < productIDs.size(); i++) {
                result_ = productDataGateway_.getProductDetail(productIDs.elementAt(i));
                if (result_.next()) {
                    tableModel.addRow(new Object[]{result_.getInt(1),
                        result_.getString(2), null, null, result_.getDouble(5)});
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    private int getLastPurchaseID() {
        try {
            result_ = purchaseDataGateway_.getLastPurchase();
            if (result_.next()) {
                return result_.getInt(1);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace()[0].getLineNumber() + "\n" + ex);
        }
        return -1;
    }

    private int getVendorID(String vendorName) {
        try {
            result_ = vendorDataGateway_.getVendor(vendorName);
            if (result_.next()) {
                return result_.getInt(1);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace()[0].getLineNumber() + "\n" + ex);
        }
        return -1;
    }
    
    private ResultSet result_;
    private VendorDataGateway vendorDataGateway_;
    private ProductDataGateway productDataGateway_;
    private PurchaseDataGateway purchaseDataGateway_;

    private static PurchaseModule purchaseModule_ = null;
}
