/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.view;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.controller.FamilyBankController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * @author Damiano
 *
 */
public abstract class JavaFXStageController {

	protected FamilyBankController controller;
	protected Stage dialogStage;

	public void setdialogStage(Stage s) {
		this.dialogStage = s;
	}

	public void setController(FamilyBankController c) {
		this.controller = c;
	}

	@FXML
	protected abstract void handleItemAdd();

	@FXML
	void handleItemCancel() {
		App.logger.config("Chiudo lo stage : "+dialogStage);
		dialogStage.close();
	}

	protected void init() {
	}
}
