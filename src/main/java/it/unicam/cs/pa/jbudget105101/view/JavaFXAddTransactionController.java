/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.view;

import java.time.LocalDate;
import java.util.HashSet;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.model.Category;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * @author Damiano
 *
 */
public class JavaFXAddTransactionController extends JavaFXStageController {

	HashSet<Category> tempSetOfTag = new HashSet<>();

	@FXML
	protected TextField Description;

	@FXML
	private ListView<String> Tags;

	@FXML
	protected DatePicker Date;

	@FXML
	protected TextField Value;

	@FXML
	protected ChoiceBox<String> SelectBox;

	@FXML
	void addTag(ActionEvent event) {
		String tTag = Tags.getSelectionModel().getSelectedItem();
		if (tTag != null) {
			tempSetOfTag.add(controller.getTag(tTag));
			Tags.getItems().remove(tTag);
		} else {
			ViewJavaFXJBudget.generateAlert("Seleziona un Tag");
		}

	}

	@FXML
	void resetTagList(ActionEvent event) {
		tempSetOfTag.clear();
		setTags();
	}

	private void setTags() {
		Tags.setItems(FXCollections.observableArrayList(controller.generateStringList(controller.getTagList())));
	}

	public void init() {
		App.logger.config("Inizializzo il controller di AddTransaction");
		setTags();
		SelectBox.setItems(
				FXCollections.observableArrayList(controller.generateStringList(controller.getCashBoxList())));
		SelectBox.getSelectionModel().select(0);
		Date.setValue(LocalDate.now());
	}

	@Override
	protected void handleItemAdd() {
		try {
			String description = Description.getText();
			String box = SelectBox.getSelectionModel().getSelectedItem();
			double value = Double.valueOf(Value.getText());
			LocalDate date = Date.getValue();
			if(!controller.addTransaction(box, value, date, description, tempSetOfTag))
				ViewJavaFXJBudget.generateAlert("Qualcosa è andato storto. non è stato possibile aggiungere la transazione");
			super.handleItemCancel();
		} catch (NullPointerException e) {
			ViewJavaFXJBudget.generateAlert(e.getMessage());
		} catch(IllegalArgumentException e1) {
			ViewJavaFXJBudget.generateAlert(e1.getMessage());
		}
	}

}
