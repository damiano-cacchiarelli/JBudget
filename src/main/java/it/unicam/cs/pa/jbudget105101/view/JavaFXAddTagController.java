package it.unicam.cs.pa.jbudget105101.view;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class JavaFXAddTagController extends JavaFXStageController {

	@FXML
	private void initialize() {
		App.logger.config("Inizializzo il controller di AddTag");
		choiceBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
		choiceBox.getSelectionModel().select("1");
	}

	@FXML
	private ChoiceBox<String> choiceBox;

	@FXML
	private TextField NameTag;

	@FXML
	protected void handleItemAdd() {
		try {
			String name = NameTag.getText();
			int priority = Integer.valueOf(choiceBox.getSelectionModel().getSelectedItem());
			controller.addTag(name, priority);
			super.handleItemCancel();
		} catch (NullPointerException e) {
			ViewJavaFXJBudget.generateAlert("Completa tutti i campi");
		} catch (ExistingElementException e1) {
			ViewJavaFXJBudget.generateAlert(e1.getMessage());
		//} catch (NumberFormatException e2) {
			//ViewJavaFXJBudget.generateAlert("Generic Error");
		}
	}

}
