/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.view;

import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author Damiano
 *
 */
public class JavaFXAddWalletController extends JavaFXStageController{

	
    @FXML
    private TextField WalletBalance;

    @FXML
    private TextField WalletName;
	
	@FXML
	protected void handleItemAdd() {
		try {
			String name = WalletName.getText();
			double balance = Double.valueOf(WalletBalance.getText());
			controller.addWallet(name, balance);
			super.handleItemCancel();
		} catch (NullPointerException e) {
			ViewJavaFXJBudget.generateAlert(e.getMessage());
		} catch (ExistingElementException e1) {
			ViewJavaFXJBudget.generateAlert(e1.getMessage());
		}
	}


}
