/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author Damiano
 *
 */
public class JavaFXSetBudgetController extends JavaFXStageController{

    @FXML
    private TextField Budget;
    
	@Override
	protected void handleItemAdd() {
		try {
			double budget = Double.valueOf(Budget.getText());
			controller.setBudget(budget);
			dialogStage.close();
		}catch(NullPointerException e){
			ViewJavaFXJBudget.generateAlert("Devi inserire un valore");
		}catch(IllegalArgumentException e1) {
			ViewJavaFXJBudget.generateAlert(e1.getMessage());
		}
		
		
	}
	
}
