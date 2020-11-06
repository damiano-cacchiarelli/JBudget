/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.view;

import java.io.IOException;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.controller.FamilyBankController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * <p>
 * La classe {@code ViewJavaFXJBudget} imlementa una GUI tramite la libreria
 * JavaFx.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class ViewJavaFXJBudget extends Application implements View {

	/**
	 * 
	 */
	private Stage primaryStage;
	/**
	 * 
	 */
	private FamilyBankController controller;

	/**
	 *
	 */
	@Override
	public void open() {
		App.logger.config("Lancio la vista");
		Application.launch(this.getClass());
	}

	/**
	 *
	 */
	@Override
	public void close() {
		primaryStage.close();

	}

	/**
	 * Chiamato non appena l'Applicazione viene caricata e costruita, configura il
	 * CONTROLLER e carica i salvataggi.
	 */
	@Override
	public void init() throws Exception {
		App.logger.config("Inizializzo il controller");
		controller = new FamilyBankController();
		controller.loadData();
		super.init();
	}

	/**
	 * Salva i dati prima che l'Applicazione venga chiusa.
	 */
	@Override
	public void stop() throws Exception {
		App.logger.config("Chiudo la vista dopo aver salvato");
		controller.saveData();
		super.stop();
	}

	/**
	 * Inizializza la vista grafica e imposta l'icona dell'Applicazione.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("JBudget");

		App.logger.config("Carico l'icona");
		setIcon();

		App.logger.config("Visualizzo la view JBudget");
		showJBudget();
	}

	/**
	 * Permette di impostare l'icona dell'Applicazione.
	 */
	private void setIcon() {
		try {
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/MoneyWallet.png")));
		} catch (Exception e) {
			App.logger.info("Non è stato possibile caricare l'icona");
		}

	}

	/**
	 * Viene caricato il file fxml e viene creato la scena per lo stage principale.
	 * 
	 */
	private void showJBudget() {
		try {
			FXMLLoader loader = getFXMLLoader("/JBudget.fxml");
			BorderPane budgetOverview = (BorderPane) loader.load();

			Scene scene = new Scene(budgetOverview);

			JavaFXJBudgetController controller = loader.getController();
			controller.setMainView(this);
			controller.setController(this.controller);
			controller.init();

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Permette di visualizzare lo stage stage per la finestra di dialogo popup.
	 */
	public void showAddWallet() {
		App.logger.config("Visualizzo lo stage di AddWallet");
		showInNewStage("/AddWallet.fxml", "Add Wallet");
	}

	/**
	 * Permette di visualizzare lo stage stage per la finestra di dialogo popup.
	 */
	public void showAddTag() {
		App.logger.config("Visualizzo lo stage di AddWallet");
		showInNewStage("/AddTag.fxml", "Add Tag");
	}

	/**
	 * Permette di visualizzare lo stage stage per la finestra di dialogo popup.
	 */
	public void showAddSaveBox() {
		App.logger.config("Visualizzo lo stage di AddWallet");
		showInNewStage("/AddSaveBox.fxml", "Add SaveBox");
	}

	/**
	 * Permette di visualizzare lo stage stage per la finestra di dialogo popup.
	 */
	public void showAddDebitBox() {
		App.logger.config("Visualizzo lo stage di AddDebitBox");
		showInNewStage("/AddDebitBox.fxml", "Add DebitBox");
	}

	/**
	 * Permette di visualizzare lo stage stage per la finestra di dialogo popup.
	 */
	public void showAddTransaction() {
		App.logger.config("Visualizzo lo stage di AddTransaction");
		showInNewStage("/AddTransaction.fxml", "Add Transaction");
	}

	/**
	 * Permette di visualizzare lo stage stage per la finestra di dialogo popup.
	 */
	public void showSetBudget() {
		App.logger.config("Visualizzo lo stage di SetBudget");
		showInNewStage("/SetBudget.fxml", "Set Budget");
	}

	/**
	 * Permette di visualizzare lo stage stage per la finestra di dialogo popup.
	 */
	public void showTemporalScheduler() {
		App.logger.config("Visualizzo lo stage di TemporalScheduler");
		showInNewStage("/TemporalScheduler.fxml", "Use TemporalScheduler");
	}

	/**
	 * Permette di visualizzare lo stage stage per la finestra di dialogo popup.
	 */
	public void showAboutMenu() {
		App.logger.config("Visualizzo lo stage di TemporalScheduler");
		showInNewStage("/AboutMenu.fxml", "About");
	}

	/**
	 * Viene caricato il file fxml e viene creato un nuovo stage per la finestra di
	 * dialogo popup.
	 * 
	 * @param directory
	 * @param titleStage
	 */
	private void showInNewStage(String directory, String titleStage) {
		try {
			FXMLLoader loader = getFXMLLoader(directory);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = getStage(titleStage, new Scene(page));

			JavaFXStageController controller = loader.getController();
			controller.setdialogStage(dialogStage);
			controller.setController(this.controller);
			controller.init();

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Genera un nuovo {@link FXMLLoader} e imposta il file fxml
	 * 
	 * @param directory il nome del file fxml.
	 * @return un nuovo {@code FXMLLoader}.
	 */
	private FXMLLoader getFXMLLoader(String directory) {
		App.logger.config("FXMLLoader");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(directory));
		return loader;

	}

	/**
	 * Crea un nuovo stage, figlio dello stage principale, con una determinata
	 * scena.
	 * 
	 * @param titleStage lo stage principale;
	 * @param scene      la scena da impostare.
	 * @return il nuovo stage.
	 */
	private Stage getStage(String titleStage, Scene scene) {
		App.logger.config("Imposta lo Stage");
		Stage dialogStage = new Stage();
		dialogStage.setTitle(titleStage);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.initOwner(primaryStage);
		dialogStage.setScene(scene);

		return dialogStage;

	}

	/**
	 * Genera un {@link Alert} con un determinato messaggio.
	 * 
	 * @param errorMessage il messaggio da visualizzare
	 */
	public static void generateAlert(String errorMessage) {
		App.logger.info("Evento : generateAlert: " + errorMessage);
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Attenzione");
		alert.setHeaderText("Sono stati riscontrati alcuni problemi");
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}

}
