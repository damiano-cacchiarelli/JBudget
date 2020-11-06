/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.view;

import java.time.LocalDate;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeScheduler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

/**
 * @author Damiano
 *
 */
public class JavaFXTemporalSchedulerController extends JavaFXAddTransactionController {

	@FXML
	private ChoiceBox<String> Template;

	@FXML
	private TextField TimeToRepeat;

	@FXML
	private ProgressIndicator ProcessIndicator;

	@FXML
	private Label ProcessStatus;

	@Override
	public void init() {
		App.logger.config("Inizializzo il controller di TemporalScheduler");
		super.init();
		Template.setItems(FXCollections.observableArrayList("DAILY", "WEEKLY", "MONTHLY"));

	}

	@Override
	protected void handleItemAdd() {
		try {
			String description = Description.getText();
			String box = SelectBox.getSelectionModel().getSelectedItem();
			double value = Double.valueOf(Value.getText());
			LocalDate date = Date.getValue();
			int ttr = Integer.valueOf(TimeToRepeat.getText());
			String template = Template.getSelectionModel().getSelectedItem();
			controller.schedulerTransaction(TypeScheduler.TEMPORAL, box, value, date, description, tempSetOfTag, ttr,
					template);
			super.handleItemCancel();
		} catch (NumberFormatException e) {
			ViewJavaFXJBudget.generateAlert("Completa tutti i campi");
		} catch (IllegalArgumentException e1) {
			ViewJavaFXJBudget.generateAlert(e1.getMessage());
		} catch (NullPointerException e2) {
			ViewJavaFXJBudget.generateAlert("Seleziona un Tag");
		}
	}

}
