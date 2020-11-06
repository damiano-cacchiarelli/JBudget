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
public class JavaFXAddSaveBoxController extends JavaFXStageController {
	
    @FXML
    private TextField SaveBoxName;

    @FXML
    private TextField SaveBoxBalance;

    @FXML
    private TextField SaveBoxGoal;
	
	@Override
	protected void handleItemAdd() {
		try {
			String name = SaveBoxName.getText();
			double balance = Double.valueOf(SaveBoxBalance.getText());
			double goal = Double.valueOf(SaveBoxGoal.getText());
			controller.addSaveBox(name, balance, goal);
			super.handleItemCancel();
		} catch (NullPointerException e) {
			ViewJavaFXJBudget.generateAlert("Inserisci tutti i campi");
		}catch(IllegalArgumentException e1) {
			ViewJavaFXJBudget.generateAlert(e1.getMessage());
		} catch (ExistingElementException e2) {
			ViewJavaFXJBudget.generateAlert(e2.getMessage());
		}
		
	}

}
