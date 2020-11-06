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
public class JavaFXAddDebitBoxController extends JavaFXStageController {

	

	@FXML
	private TextField Name;

	@FXML
	private TextField Debit;

	@Override
	protected void handleItemAdd() {
		try {
			String name = Name.getText();
			double debit = Double.valueOf(Debit.getText());
			controller.addDebitBox(name, debit);
			super.handleItemCancel();
		
		} catch (NullPointerException e1) {
			ViewJavaFXJBudget.generateAlert(" Non sono stati inseriti correttamente i dati nei campi ");
		}catch(IllegalArgumentException e) {
			ViewJavaFXJBudget.generateAlert(e.getMessage()+" : " + Debit.getText());
		} catch (ExistingElementException e2) {
			ViewJavaFXJBudget.generateAlert(e2.getMessage());
		}
		
	}


}
