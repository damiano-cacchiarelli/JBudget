/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.view;

import java.time.LocalDate;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.controller.FamilyBankController;
import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Category;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeCashBox;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeScheduler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author Damiano
 *
 */

public class JavaFXJBudgetController {

	private ViewJavaFXJBudget ViewJBudget;
	private FamilyBankController controller;

	// ______________________________________________ Home
	@FXML
	private Label Spent;

	@FXML
	private Label Budget;

	@FXML
	private Label Remaining;

	@FXML
	private Label Finance;

	@FXML
	private ListView<String> LastTransaction;

	@FXML
	private ListView<String> TransactionExpire;

	// ______________________________________________ Boxes
	@FXML
	private ListView<String> ListOfAllBox;

	@FXML
	private Label NameBox;

	@FXML
	private Label BudgetBox;

	@FXML
	private Label GoalBox;

	@FXML
	private Label GoalTextBox;

	@FXML
	private Label NumberTraBox;

	@FXML
	private ProgressBar ProcessBarBox;

	@FXML
	private Label Percent;

	@FXML
	private ChoiceBox<String> ShiftFrom;

	@FXML
	private ChoiceBox<String> ShiftTo;

	@FXML
	private TextField ValueToShift;

	// ______________________________________________ Transaction
	@FXML
	private ChoiceBox<String> SelectBox;

	@FXML
	private ChoiceBox<String> SelectTag;

	@FXML
	private DatePicker SelectDate;

	@FXML
	private ListView<String> TransactionListView;

	// ______________________________________________ Tags

	@FXML
	private ListView<String> TagsListView;

	@FXML
	private Label NameTag;

	@FXML
	private Label PriorityTag;

	@FXML
	private Label UsedTag;

	// ______________________________________________ Scheduler

	@FXML
	private Text SchedulerText;

	// ______________________________________________ Statistics

	@FXML
	private LineChart<String, Double> TransactionLog;

	private Series<String, Double> seriesTransaction = new Series<>();

	@FXML
	private PieChart PieChart;

	@FXML
	private BarChart<String, Double> SpendingByMonth;

	private Series<String, Double> seriesBudget = new Series<>();
	private Series<String, Double> seriesSpent = new Series<>();

	public void setMainView(ViewJavaFXJBudget mainView) {
		this.ViewJBudget = mainView;
	}

	public void setController(FamilyBankController c) {
		this.controller = c;

	}

	public void init() {
		App.logger.info("Aggiorno i dati da visualizzare ...");

		controller.updateData();

		initHome();
		initBoxes();
		initTransaction();
		initTag();
		initScheduler();
		initStatistics();

		App.logger.info("Dati aggiornati");

	}

	@FXML
	private void initialize() {
		showBoxDetails(null);
		showTagDetails(null);

		AddListenerListOfAllBox();

		AddListenerShiftFrom();

		AddListenerSelectBox();

		AddListenerSelectTag();

		AddListenerSelectDate();

		AddListenerTagsListView();

		addSeries();

	}

	@FXML
	private void save() {
		try {
			ViewJBudget.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void refresh(ActionEvent actionEvent) {
		init();
	}

	@FXML
	private void setBudget(ActionEvent event) {
		App.logger.info("Evento : Click su SetBudget");
		ViewJBudget.showSetBudget();
		initHome();
		initStatistics();
	}

	@FXML
	public void closeApp() {
		ViewJBudget.close();

	}
	
    @FXML
    void aboutMenu(ActionEvent event) {
    	App.logger.info("Evento : Click su About");
    	ViewJBudget.showAboutMenu();
    }

	@FXML
	void addTag(ActionEvent event) {
		App.logger.info("Evento : Click su addTag");
		ViewJBudget.showAddTag();
		initTag();
		initStatistics();
	}

	@FXML
	void addWallet(ActionEvent event) {
		App.logger.info("Evento : Click su addWallet");
		ViewJBudget.showAddWallet();
		init();
	}

	@FXML
	void addSaveBox(ActionEvent event) {
		App.logger.info("Evento : Click su addSaveBox");
		ViewJBudget.showAddSaveBox();
		init();
	}

	@FXML
	void addDebitBox(ActionEvent event) {
		App.logger.info("Evento : Click su addDebitBox");
		ViewJBudget.showAddDebitBox();
		initBoxes();
	}

	@FXML
	void addTransaction(ActionEvent event) {
		App.logger.info("Evento : Click su addTransaction");
		ViewJBudget.showAddTransaction();
		init();
	}

	@FXML
	void deleteBox(ActionEvent event) {
		App.logger.info("Evento : Click su deleteBox");
		String box = ListOfAllBox.getSelectionModel().getSelectedItem();
		if (controller.deleteBox(box)) {
			ListOfAllBox.getItems().remove(box);
		}
		init();
	}

	@FXML
	void shift(ActionEvent event) {
		try {
			App.logger.info("Evento : Click su shift");
			String shiftFrom = ShiftFrom.getSelectionModel().getSelectedItem();
			String shiftTo = ShiftTo.getSelectionModel().getSelectedItem();
			double valueToShift = Double.valueOf(ValueToShift.getText());
			controller.shiftMoney(shiftFrom, valueToShift, shiftTo);
			init();
		} catch (NumberFormatException e) {
			ViewJavaFXJBudget.generateAlert(e.getMessage());
		} catch (NullPointerException e1) {
			ViewJavaFXJBudget.generateAlert(e1.getMessage());
		}
	}

	@FXML
	void cleanUp(ActionEvent event) {
		App.logger.info("Evento : Click su cleanUp");
		SelectDate.setValue(null);
		SelectBox.setValue(null);
		SelectTag.setValue(null);
	}

	@FXML
	void deleteTransaction(ActionEvent event) {
		try {
			App.logger.info("Evento : Click su deleteTransaction");
			String tr = TransactionListView.getSelectionModel().getSelectedItem();
			String cb = SelectBox.getSelectionModel().getSelectedItem();
			controller.deleteTransactionInBox(tr, cb);
			TransactionListView.getItems().remove(tr);
			initHome();
			initStatistics();
		} catch (NullPointerException e) {
			ViewJavaFXJBudget.generateAlert(e.getMessage());
		}

	}

	@FXML
	void deleteTag(ActionEvent event) {
		App.logger.info("Evento : Click su deleteTag");
		String tag = TagsListView.getSelectionModel().getSelectedItem();
		try {
			if (!controller.removeTag(tag))
				ViewJavaFXJBudget.generateAlert("Non è possibile eliminare un tag utilizzato");
			initTag();
		} catch (NullPointerException e) {
			ViewJavaFXJBudget.generateAlert(e.getMessage());
		}

	}

	@FXML
	void useTemporalScheduler(ActionEvent event) {
		App.logger.info("Evento : Click su Use TemporalScheduler");
		ViewJBudget.showTemporalScheduler();
		init();
	}

	private void initHome() {
		App.logger.config("InitHome...");
		Budget.setText(String.valueOf(controller.getBudget()));
		Spent.setText(String.valueOf(controller.outlayOfMonth(LocalDate.now())));
		Remaining.setText(String.valueOf(controller.getRemaining()));
		Finance.setText(String.valueOf(controller.getFinance()));

		LastTransaction.setItems(FXCollections.observableArrayList(
				controller.generateStringList(controller.searchTransaction(null, null, LocalDate.now()))));

		TransactionExpire.setItems(FXCollections.observableArrayList(
				controller.generateStringList(controller.searchTransaction(null, null, LocalDate.now().plusDays(1)))));
	}

	private void initBoxes() {
		App.logger.config("InitBoxes...");
		ListOfAllBox.setItems(
				FXCollections.observableArrayList(controller.generateStringList(controller.getCashBoxList())));

		ShiftFrom.setItems(FXCollections
				.observableArrayList(controller.generateStringList(controller.getCashBox(TypeCashBox.WALLET))));

		ShiftTo.setItems(FXCollections
				.observableArrayList(controller.generateStringList(controller.getCashBox(TypeCashBox.WALLET))));//TODO

		ValueToShift.clear();
	}

	private void initTransaction() {
		App.logger.config("InitTransaction...");
		SelectBox.setItems(
				FXCollections.observableArrayList(controller.generateStringList(controller.getCashBoxList())));

		SelectTag.setItems(FXCollections.observableArrayList(controller.generateStringList(controller.getTagList())));

		TransactionListView.setItems(
				FXCollections.observableArrayList(controller.generateStringList(controller.getAllTransaction())));

	}

	private void initTag() {
		App.logger.config("InitTag...");
		TagsListView
				.setItems(FXCollections.observableArrayList(controller.generateStringList(controller.getTagList())));
	}

	private void initScheduler() {
		App.logger.config("InitScheduler...");
		SchedulerText.setText(getTextScheduler());
	}

	private String getTextScheduler() {
		String s = controller.getSchedulerDescription(TypeScheduler.TEMPORAL);
		if (s == null)
			s = "-";
		return s;
	}

	private void initStatistics() {
		App.logger.config("InitStatistics...");
		PieChart.setData(details());
		insertSeriesData();
	}

	private void insertSeriesData() {
		
		insertSeriesTransaction();
		insertSeriesBarChart();
	}

	private void insertSeriesTransaction() {
		App.logger.config("Inserisco i dati per il grafico delle transazioni");
		seriesTransaction.getData().clear();
		LocalDate date = LocalDate.now().plusDays(-6);
		for (int day = 0; day < 7; day++) {
			seriesTransaction.getData()
					.add(new Data<>(date.toString(), Double.valueOf(controller.getNumberOfTransactionInDay(date))));
			date = date.plusDays(1);
		}
	}

	private void insertSeriesBarChart() {
		App.logger.config("Inserisco i dati per il grafico per la spesa");
		seriesBudget.getData().clear();
		seriesSpent.getData().clear();
		LocalDate date = LocalDate.now().plusMonths(-6);
		for (int month = 0; month < 7; month++) {
			seriesBudget.getData().add(new Data<>(date.getMonth().name(), controller.getBudget()));
			seriesSpent.getData().add(new Data<>(date.getMonth().name(), controller.outlayOfMonth(date)));
			date = date.plusMonths(1);
		}
	}

	private ObservableList<PieChart.Data> details() {
		App.logger.config("inserisco i dati per il grafico dei Tag");
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (Category c : controller.getTagList()) {
			double spent = controller.getOutlayOfCategory(c);
			if (spent != 0) {
				pieChartData.add(new PieChart.Data(c.getName(), -spent));
			}
		}
		return pieChartData;
	}

	private void AddListenerListOfAllBox() {
		App.logger.config("Creo un Listener per ListOfAllBox");
		ListOfAllBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			showBoxDetails(controller.getCashBox(newValue));
			double percent = controller.getBalancePercent(newValue);
			Percent.setText(percent * 100 + " %");
			ProcessBarBox.setProgress(percent);
		});
	}

	private void AddListenerShiftFrom() {
		App.logger.config("Creo un Listener per ShiftFrom");
		ShiftFrom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			ShiftTo.getItems().remove(newValue);
			ShiftTo.getItems().add(oldValue);
			ShiftTo.setValue(null);
		});
	}

	private void AddListenerSelectBox() {
		App.logger.config("Creo un Listener per SelecBox");
		SelectBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			String selectedBox = SelectBox.getSelectionModel().getSelectedItem();
			String selectedTag = SelectTag.getSelectionModel().getSelectedItem();
			LocalDate selectedDate = SelectDate.getValue();
			generateListOftransaction(selectedBox, selectedTag, selectedDate);
		});
	}

	private void AddListenerSelectTag() {
		App.logger.config("Creo un Listener per SelectTag");
		SelectTag.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			String selectedBox = SelectBox.getSelectionModel().getSelectedItem();
			String selectedTag = SelectTag.getSelectionModel().getSelectedItem();
			LocalDate selectedDate = SelectDate.getValue();
			generateListOftransaction(selectedBox, selectedTag, selectedDate);
		});
	}

	private void AddListenerSelectDate() {
		App.logger.config("Creo un Listener per SelectDate");
		SelectDate.valueProperty().addListener((observable, oldValue, newValue) -> {
			String selectedBox = SelectBox.getSelectionModel().getSelectedItem();
			String selectedTag = SelectTag.getSelectionModel().getSelectedItem();
			LocalDate selectedDate = SelectDate.getValue();
			generateListOftransaction(selectedBox, selectedTag, selectedDate);
		});
	}

	private void AddListenerTagsListView() {
		App.logger.config("Creo un Listener per ListView");
		TagsListView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showTagDetails(controller.getTag(newValue)));
	}

	private void generateListOftransaction(String selectedBox, String selectedTag, LocalDate selectedDate) {
		App.logger.config("Creo una lista delle transazioni con i criteri selezionati");
		TransactionListView.setItems(FXCollections.observableArrayList(
				controller.generateStringList(controller.searchTransaction(selectedBox, selectedTag, selectedDate))));

	}

	private void addSeries() {
		TransactionLog.getData().add(seriesTransaction);

		seriesBudget.setName("Budget");
		seriesSpent.setName("Spending");
		SpendingByMonth.getData().add(seriesBudget);
		SpendingByMonth.getData().add(seriesSpent);
	}

	private void showBoxDetails(CashBox cb) {
		if (cb != null) {
			String nameBox = getStringOfNameBox(cb);
			String budgetBox = getStringOfBudgetBox(cb);
			String goalBox = getStringOfGoalBox(cb);
			String numberTraBox = getStringOfNumberTraBox(cb);
			String goalTextBox = getStringOfGoalTextBox(cb);
			setValueOfBoxTransactionDetails(nameBox, budgetBox, goalBox, numberTraBox, goalTextBox);
		} else {
			setValueOfBoxTransactionDetails("-", "-", "-", "-", "-");
		}
	}

	private void setValueOfBoxTransactionDetails(String nB, String bB, String gB, String nTb, String gTb) {
		NameBox.setText(nB);
		BudgetBox.setText(bB);
		GoalBox.setText(gB);
		NumberTraBox.setText(nTb);
		GoalTextBox.setText(gTb);

	}

	private String getStringOfBudgetBox(CashBox cb) {
		return String.valueOf(cb.getBalance());
	}

	private String getStringOfNumberTraBox(CashBox cb) {
		return String.valueOf(cb.getAllTransaction().size());
	}

	private String getStringOfGoalTextBox(CashBox cb) {
		return getStringOfTypeBox(cb.getType());
	}

	private String getStringOfNameBox(CashBox cb) {
		return cb.getName();
	}

	private String getStringOfGoalBox(CashBox cb) {
		try {
			return String.valueOf(cb.getGoal());
		} catch (UnsupportedOperationException e) {
			return "-";
		}
	}

	private String getStringOfTypeBox(TypeCashBox type) {
		if (type == TypeCashBox.WALLET) {
			return "-";
		} else {
			return "Goal";
		}
	}

	private void showTagDetails(Category tag) {
		if (tag != null) {
			String name = tag.toString();
			String priority = String.valueOf(tag.getPriority());
			String used = String.valueOf(controller.searchTransaction(null, name, null).size());
			setValueOfTagDetails(name, priority, used);
		} else {
			setValueOfTagDetails("-", "-", "-");
		}
	}

	private void setValueOfTagDetails(String name, String priority, String used) {
		NameTag.setText(name);
		PriorityTag.setText(String.valueOf(priority));
		UsedTag.setText(String.valueOf(used));
	}

}
