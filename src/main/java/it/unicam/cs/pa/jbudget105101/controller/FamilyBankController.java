/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.model.Bank;
import it.unicam.cs.pa.jbudget105101.model.BasicTransaction;
import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Category;
import it.unicam.cs.pa.jbudget105101.model.CategoryManager;
import it.unicam.cs.pa.jbudget105101.model.DebitBox;
import it.unicam.cs.pa.jbudget105101.model.FamilyBank;
import it.unicam.cs.pa.jbudget105101.model.Identifier;
import it.unicam.cs.pa.jbudget105101.model.IntegerIdManager;
import it.unicam.cs.pa.jbudget105101.model.SaveBox;
import it.unicam.cs.pa.jbudget105101.model.Tag;
import it.unicam.cs.pa.jbudget105101.model.TagManager;
import it.unicam.cs.pa.jbudget105101.model.Transaction;
import it.unicam.cs.pa.jbudget105101.model.Wallet;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeCashBox;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeScheduler;
import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;

/**
 * <p>
 * La classe {@code FamilyBankController} implementa operazioni che permettono
 * l'interazione e la modifica delle classi del MODEL. </br>
 * Sono implementati metodi per aggiungere e rimuovere {@link Tag tag},
 * {@link BasicTransaction transazioni} e {@link Box box} oppure operazioni come
 * la ricerca di transazioni con determinate caratteristiche.
 * </p>
 * <p>
 * Si appoggia su altre classi per eseguire alcune operazioni come
 * {@link StatisticsOfBank} per le operazioni statistiche o {@link Synchronizer}
 * per salvare e caricare lo stato degli elementi del MODEL.
 * </p>
 * 
 * @see StatisticsOfBank
 * @see Synchronizer
 * @see MasterScheduler
 * @see SchedulerTransaction
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
/**
 * @author Damiano
 *
 */
public class FamilyBankController implements Controller {

	/**
	 * La {@link Bank} su cui si andrà ad operare.
	 */
	private FamilyBank fb;
	/**
	 * Il {@link CategoryManager} in cui verranno gestiti i {@link Category tag}.
	 */
	private TagManager cm;
	/**
	 * Il manager per {@link Identifier id}.
	 */
	private IntegerIdManager idm;
	/**
	 * Utilizzato per ricavara informazioni da una determinata {@link FamilyBank}.
	 */
	private BasicStatisticOfBank<FamilyBank> stats;
	/**
	 * Il manager per {@link SchedulerTransaction}.
	 */
	private MasterSchedulerBasicTransaction ms;
	/**
	 * Utilizzato per caricare e salvare i dati della {@link FamilyBank}, del
	 * {@link TagManager} e del {@link IntegerIdManager}.
	 */
	private LocalSynchronizer ls;
	/**
	 * Tag di default utilizzato per le operazioni si "shift". Nel metodo
	 * {@link #shiftTag}.
	 */
	private final Category shiftTag = new Tag("Shift", 0);

	/**
	 * Costruisce un controller vuoto. Viene istanziato solo
	 * {@link LocalSynchronizer} per permettere di caricare i dati.
	 */
	public FamilyBankController() {
		ls = new LocalSynchronizer();
	}

	/**
	 * Costruisce un {@code FamilyBankController} con una paricolare
	 * {@link FamilyBank}, {@link TagManager} e {@link IntegerIdManager}.
	 * 
	 * @param fb  la {@code FamilyBank}
	 * @param cm  il {@code TagManager}
	 * @param idm l' {@code IntegerIdManager}
	 */
	public FamilyBankController(FamilyBank fb, TagManager cm, IntegerIdManager idm) {
		super();
		this.fb = fb;
		this.cm = cm;
		this.idm = idm;
		inizialize();
	}

	// ________________________________________________ TODO [Load & Save]

	/**
	 * Permette di inizializzare le classi: vengono caricati i dati tramite il
	 * {@link LocalSynchronizer}; Se il {@link Synchronizer} restituisce oggetti
	 * {@code null} viene creata una nuova istanza di questi oggetti. Inoltre
	 * vengono inizializzati gli altri componenti.
	 */
	@Override
	public boolean loadData() {
		if ((fb = ls.loadBank()) == null)
			fb = new FamilyBank(0.0);
		if ((cm = ls.loadCategoryManager()) == null)
			cm = new TagManager();
		if ((idm = ls.loadIdManager()) == null)
			idm = new IntegerIdManager();

		inizialize();

		return true;
	}

	/**
	 * Inizializza gli altri componenti del Controller.
	 */
	private void inizialize() {
		stats = new BasicStatisticOfBank<>(fb);
		ms = new MasterSchedulerBasicTransaction();
		cm.addCategory(shiftTag);
		inizializeScheduler();

	}

	/**
	 * Inizializza i componenti del {@link MasterScheduler}.
	 */
	private void inizializeScheduler() {
		try {
			ms.addScheduler(new TemporalScheduler(idm));
		} catch (ExistingElementException e) {
			App.logger.severe("Inizialize Scheduler : " + e.getMessage());
		}
	}

	/**
	 * Permette di salvare lo stato del {@link FamilyBank}, del {@link TagManager} e
	 * del {@link IntegerIdManager}.
	 */
	@Override
	public void saveData() {
		ls.saveData(fb, cm, idm);
	}

	// ________________________________________________ TODO [Valori stato Bank]

	/**
	 * Restituisce il budget della {@link FamilyBank}.
	 * 
	 * @return il budget della {@code FamilyBank}.
	 */
	public double getBudget() {
		return fb.getBudget();
	}

	/**
	 * Permette di impostare il budget della {@link FamilyBank}.
	 * 
	 * @param b il budget da impostare.
	 */
	public void setBudget(double b) {
		fb.changeBudget(b);
	}

	/**
	 * Permette di ottenere le spese in un determinato mese. Prima che il calcolo
	 * venga effettuato vengono aggiurnati gli stati della {@link FamilyBank} e dei
	 * {@link CashBox} che essa gestisce. Viene utilizzato
	 * {@link BasicStatisticOfBank} come classe di supporto.
	 * 
	 * @param date la data in cui verra effetuato il calcolo
	 * @return la spesa in un determinato mese.
	 */
	public double outlayOfMonth(LocalDate date) {
		updateData();
		return stats.outlayOfMonth(date);
	}

	/**
	 * Restituisce la differenza tra il budget e la spesa del mese corrente; ovvero
	 * la quantità di denaro che è rimasata da poter spendere senza sforare il
	 * budget.
	 * 
	 * @return quantità di denaro che è rimasata.
	 */
	public double getRemaining() {
		return getBudget() - outlayOfMonth(LocalDate.now());
	}

	/**
	 * Restituisce il bilancio della {@link FamilyBank}; ovvero la quantità di
	 * denaro che l'utente ha a disposizione.
	 * 
	 * @return il bilancio della {@code FamilyBank}.
	 */
	public double getFinance() {
		updateData();
		return fb.getBalance();
	}

	// ________________________________________________ TODO [Metodi Tag]

	/**
	 * Costruisce un {@link Tag} con un determinato nome e una determinata priorità
	 * e prova ad aggiungerlo al {@link TagManager}. Se nel manager esiste già un
	 * {@code Tag} con lo stesso identico nome verrà lanciata
	 * {@link ExistingElementException}.
	 * 
	 * @throws ExistingElementException se il tag è già persente.
	 */
	@Override
	public void addTag(String name, int priority) throws ExistingElementException {
		if (!cm.addCategory(new Tag(name, priority)))
			throw new ExistingElementException("Il tag e' già presente");
	}

	/**
	 * Permette di rimuovere un tag con un determinato nome. Se il tag è usato da
	 * almeno una {@link Transaction} non potrà essere eliminato.
	 * 
	 * @throws NullPointerException se la stringa del nome del tag è {@code null}.
	 */
	@Override
	public boolean removeTag(String tag) {
		if (tag == null)
			throw new NullPointerException("Seleziona almeno un Tag");
		if (searchTransaction(null, tag, null).size() != 0)
			return false;
		cm.removeCategory(getTag(tag));
		return true;
	}

	/**
	 * Restituisce l'insieme dei {@link Tag} che il {@link TagManager} gestisce.
	 */
	@Override
	public Set<Category> getTagList() {
		return cm.getCategoryList();
	}

	/**
	 * Restituisce un tag con un determinato nome.
	 */
	@Override
	public Category getTag(String s) {
		return cm.getCategory(s);
	}

	/**
	 * Restituisce la quantità di denaro spesa per una determinata {@link Category}.
	 * 
	 * @param c la {@code Category categoria}.
	 * @return il denaro speso.
	 */
	public double getOutlayOfCategory(Category c) {
		return stats.outlayOfCategory(c);
	}

	// ________________________________________________ TODO [Metodi Transaction]

	/**
	 * Permette di costruire e aggiungere ad un {@link CashBox} una
	 * {@link BasicTransaction}. Se la data che si vuole utilizzare per la
	 * transazione è {@code null} verrà untilizzato il costruttore di
	 * {@code BasicTransaction} che non ha tra i campi la data.
	 */
	@Override
	public boolean addTransaction(String box, double value, LocalDate ld, String i, Set<Category> tl) {
		BasicTransaction t;
		if (ld == null) {
			t = new BasicTransaction(value, i, tl, idm.getNewIdentification());
		} else {
			t = new BasicTransaction(value, ld, i, tl, idm.getNewIdentification());
		}

		return executeTransaction(getCashBox(box), t);
	}

	/**
	 * Permette di eliminare una determinata {@link Transaction transazione} in un
	 * determinato {@link CashBox}. Viene effettuata una ricerca della transazione
	 * che corrisponde alla stringa data ({@link #searchTransaction(CashBox, String)
	 * searchTransaction()}). Lo stesso vale per il Box
	 * ({@link #getCashBox(String)}).
	 * 
	 * @throws NullPointerException se uno dei due parametri è {@code null}.
	 */
	@Override
	public boolean deleteTransactionInBox(String tr, String box) {
		if (box == null)
			throw new NullPointerException("Seleziona un CashBox");
		if (tr == null)
			throw new NullPointerException("Seleziona una transazione");
		CashBox cb = getCashBox(box);
		Transaction t = searchTransaction(cb, tr);
		if (cb.subTransaction(t)) {
			return cb.updateBalance();
		}
		return false;
	}

	/**
	 * Restituisce una collezione di tutte le {@link Transaction} che i
	 * {@link CashBox} della {@link FamilyBank} possiedono.
	 */
	@Override
	public Collection<Transaction> getAllTransaction() {
		Stack<Transaction> tList = new Stack<>();
		for (CashBox c : fb.getAllBox()) {
			tList.addAll(c.getAllTransaction());
		}
		return tList;
	}

	/**
	 * Permette do ottenere il numero di transazioni effettuate in un giorno.
	 * 
	 * @param date la data in cui calcolare il numero di transazioni.
	 * @return il numero di transazioni.
	 */
	public double getNumberOfTransactionInDay(LocalDate date) {
		return stats.numberOfTransactionInDay(date);
	}

	/**
	 * Metodo di supporto che permette di ottenere la {@link Transaction}
	 * corrispondente alla stringa che è contenuta in un determinato
	 * {@link CashBox}.
	 * 
	 * @param c la stringa che corrisponde al {@code CashBox} dove è contenuta la
	 *          transazione;
	 * @param t la stringa che corrisponde alla transazione
	 * @return la transazionione o {@code null} se non viene trovata.
	 */
	private Transaction searchTransaction(CashBox c, String t) {
		for (Transaction tr : c.getAllTransaction()) {
			if (tr.toString().equals(t.toString()))
				return tr;
		}
		return null;
	}

	// ________________________________________________ TODO [Metodi CashBox]

	/**
	 * Permette di crare a aggiungere un {@link Wallet} con un determinato nome e
	 * bilancio. Dopo che tale {@link CashBox} è stato aggiunto viene eseguito un
	 * aggiornamento dello stato della {@link FamilyBank}.
	 * 
	 */
	@Override
	public void addWallet(String n, double b) throws ExistingElementException {
		fb.addCashBox(new Wallet(n, App.round(b)));
		updateData();

	}

	/**
	 * Permette di crare a aggiungere un {@link SaveBox} con un determinato nome,
	 * bilancio e obiettivo. Dopo che tale {@link CashBox} è stato aggiunto viene
	 * eseguito un aggiornamento dello stato della {@link FamilyBank}.
	 */
	@Override
	public void addSaveBox(String n, double b, double goal) throws ExistingElementException {
		fb.addCashBox(new SaveBox(n, App.round(goal), App.round(b)));
		updateData();
	}

	/**
	 * Permette di crare a aggiungere un {@link Wallet} con un determinato nome e
	 * debito. Dopo che tale {@link CashBox} è stato aggiunto viene eseguito un
	 * aggiornamento dello stato della {@link FamilyBank}.
	 */
	@Override
	public void addDebitBox(String name, double debit) throws ExistingElementException {
		fb.addCashBox(new DebitBox(name, debit));
		updateData();
	}

	/**
	 * Permette di eliminare un determinato {@link CashBox} corrispondente alla
	 * stringa data. Dopo che il {@code CashBox} è stato eliminato viene eseguito un
	 * aggiornamento dello stato della {@link FamilyBank}.
	 * 
	 * @return {@code true} se il box viene trovato ed eliminato; {@code false}
	 *         altrimenti.
	 */
	@Override
	public boolean deleteBox(String box) {
		CashBox cb = getCashBox(box);
		if (cb == null)
			return false;
		fb.subCashBox(cb);
		updateData();
		return true;
	}

	/**
	 * Restituisce tutti i {@link CashBox} che la {@link FamilyBank} gestisce.
	 */
	@Override
	public Collection<CashBox> getCashBoxList() {
		return fb.getAllBox();
	}

	/**
	 * Restituisce il {@link CashBox} corrispondente alla stringa data.
	 * 
	 * @param name la stringa che corrisponde al {@code CashBox}.
	 * @return il {@code CashBox}; {@code null} se non viene trovato alcun elemento.
	 */
	public CashBox getCashBox(String name) {
		return fb.getCashBox(name);
	}

	/**
	 * Restituisce una lista di {@link CashBox} con un determinato
	 * {@link TypeCashBox tipo}.
	 */
	@Override
	public Collection<CashBox> getCashBox(TypeCashBox tc) {
		return getCashBoxList().parallelStream().filter(c -> c.getType().equals(tc)).collect(Collectors.toList());
	}

	// ________________________________________________ TODO [Metodi Scheduler]

	/**
	 * Permette di creare e schedulare una lista di {@link BasicTransaction
	 * transazioni} in un determinato {@link CashBox}. Viene selezionato per
	 * eseguire tale funzione lo {@link SchedulerTransaction} che ha come
	 * {@link TypeScheduler tipo} il tipo indicato. &Egrave; richiesto inoltre un
	 * {@code template} che descriva la funzione che verrà utilizzata per generare
	 * le diverse transazioni; sono disponibili tre {@code template} : "DAILY",
	 * "WEEKLY", "MONTHLY".
	 * 
	 * @param type     il tipo di {@code Scheduler};
	 * @param box      lastringa che corrisponde al {@code CashBox};
	 * @param v        l'importo della transazione;
	 * @param ld       la data della transazione;
	 * @param i        la descrizione della transazione;
	 * @param tl       la lista di {@link Category};
	 * @param ttr      il numero di transazioni che devono essere generate;
	 * @param template il template che descrive la funzione di modifica.
	 */
	public void schedulerTransaction(TypeScheduler type, String box, double v, LocalDate ld, String i, Set<Category> tl,
			int ttr, String template) {
		CashBox cb = getCashBox(box);
		Collection<BasicTransaction> listT = ms.getScheduler(type).generateTransactionList(ttr,
				new BasicTransaction(v, ld, i, tl, idm.getNewIdentification()), getFunctionToTemplate(template));
		if (!addScheduledTransaction(listT, cb))
			throw new IllegalArgumentException("Non tutte le transazioni sono state inserite");
	}

	/**
	 * Restituisce tutti gli {@link SchedulerTransaction} gestiti dal
	 * {@link MasterScheduler manager}.
	 * 
	 * @return una collezione di {@code Scheduler}.
	 */
	public Collection<SchedulerTransaction<BasicTransaction>> getAllScheduler() {
		return ms.getAllScheduler();

	}

	/**
	 * Restituisce tutti i {@link TypeScheduler} degli {@link SchedulerTransaction}
	 * gestiti dal {@link MasterScheduler manager}.
	 * 
	 * @return una collezione di {@code TypeScheduler}
	 */
	public Collection<TypeScheduler> getAllTypeScheduler() {
		return ms.getAllSchedulerType();

	}

	/**
	 * Metodo di supporto che permette di aggiungere una lista di
	 * {@link BasicTransaction} ad un determinato {@link CashBox}. Alla fine del
	 * processo viene aggiornato lo stato del {@code CashBox}.
	 * 
	 * @param generateTransactionList lista di transazioni;
	 * @param cb                      il cashbox dove inserire le transazioni.
	 * @return {@code true} se le transazioni sono state aggiunge correttamente;
	 *         {@code false} altrimenti.
	 */
	private boolean addScheduledTransaction(Collection<BasicTransaction> generateTransactionList, CashBox cb) {
		for (Transaction t : generateTransactionList) {
			cb.addTransaction(t);
		}
		return cb.updateBalance();

	}

	/**
	 * Metodo di supporto che restituisce una funzione corrispondente al template
	 * dato.
	 * 
	 * @param s il template.
	 * @return la funzione corrispondente al template.
	 * @throws IllegalArgumentException se non esiste un template corrispondente;
	 */
	private BiFunction<Integer, LocalDate, LocalDate> getFunctionToTemplate(String s) {
		switch (s) {
		case "DAILY":
			return (i, d) -> (d.plusDays(i));
		case "WEEKLY":
			return (i, d) -> (d.plusWeeks(i));
		case "MONTHLY":
			return (i, d) -> (d.plusMonths(i));
		}
		throw new IllegalArgumentException("Nessuna funzione con questo template");
	}

	/**
	 * Restituisce la descrizione dello {@link SchedulerTransaction} corrispondente
	 * al {@link TypeScheduler tipo} dato.
	 * 
	 * @param ts il {@code tipo} dello {@code Scheduler}.
	 * @return la descrizione dello {@code Scheduler}.
	 */
	public String getSchedulerDescription(TypeScheduler ts) {
		return ms.getScheduler(ts).getDescription();
	}

	// ________________________________________________ TODO [Operazioni]

	/**
	 * Permette di generare una collezione di {@link Transaction} con determinati
	 * requisiti. &Egrave; possibile definire un {@link CashBox}, un {@link Tag} e
	 * una data che caratterizzeranno le transazioni presenti nella lista. &Egrave;
	 * possibile inserire valori {@code null} come parametri, non verrà quindi
	 * effettuata alcuna operazione di filtraggio con tali valori.
	 * 
	 * @param box  la stringa che corrisponde al {@code CashBox};
	 * @param tag  la stringa che corrisponde al {@code Tag};
	 * @param date la data.
	 * @return una collezione di {@code transazioni}.
	 */
	public Collection<Transaction> searchTransaction(String box, String tag, LocalDate date) {
		Collection<Transaction> t = getAllTransaction();
		if (box != null) {
			t = getCashBox(box).getAllTransaction();
		}
		if (tag != null) {
			Category tempC = getTag(tag);
			t = t.stream().filter(z -> z.getCategory().contains(tempC)).collect(Collectors.toList());
		}
		if (date != null) {
			t = t.stream().filter(y -> y.getDate().isEqual(date)).collect(Collectors.toList());
		}

		return t;
	}

	/**
	 * Aggiorna lo stato di tutti i {@link CashBox} e della {@link FamilyBank}.
	 */
	public void updateData() {
		fb.getAllBox().forEach(c -> c.updateBalance());
		fb.updateBalance();

	}

	/**
	 * Restituisce la percentuale di completamento di un box.
	 * 
	 * @param box la stringa che corrisponde al {@code CashBox};
	 * @return la percentuale di completamento.
	 */
	public double getBalancePercent(String box) {
		return stats.getBalancePercent(getCashBox(box));
	}

	/**
	 * Effettua in modo automatico un prelievo di denaro da un {@link CashBox} ad un
	 * altro. Tale operazione viene effettuata inserendo l'importo che si vuole
	 * prelevare, dove si vuole prelevare e dove sarà depositato. In autoamtico
	 * verranno cheate due {@link BasicTransaction} con importo opposto che verranno
	 * inserite nei due {@code Box}. Non è possibile spostare somme di denaro
	 * negarive. Nel caso in cui una delle due transazioni non potesse essere
	 * aggiunta al box, verranno eliminate entrambe.
	 * 
	 * @param shiftFrom    la stringa che corrisponde al {@code CashBox} in cui
	 *                     prelevare il denaro;
	 * @param valueToShift l'importo da spostare;
	 * @param shiftTo      la stringa che corrisponde al {@code CashBox} in cui
	 *                     depositare il denaro.
	 * @return {@code true} se le transazioni vengono inserite correttamente;
	 *         {@code false} altrimenti.
	 */
	public boolean shiftMoney(String shiftFrom, double valueToShift, String shiftTo) {
		if (valueToShift <= 0)
			throw new NumberFormatException("Inserisci un valore positivo");
		if (shiftFrom == null || shiftTo == null)
			throw new NullPointerException("Inserisci un CashBox");
		String info = shiftFrom + ">" + shiftTo;
		BasicTransaction b1 = new BasicTransaction(-valueToShift, info, getShiftTag(), idm.getNewIdentification());
		BasicTransaction b2 = new BasicTransaction(valueToShift, info, getShiftTag(), idm.getNewIdentification());

		if (executeTransaction(getCashBox(shiftFrom), b1)) {
			if (executeTransaction(getCashBox(shiftTo), b2)) {
				return true;
			}
			deleteTransactionInBox(b1.toString(), shiftFrom);
		}
		return false;
	}

	/**
	 * Restituisce un insieme di Tag per le transazioni "Shiftate".
	 * 
	 * @return un insieme di Tag
	 */
	private Set<Category> getShiftTag() {
		Set<Category> tags = new HashSet<>();
		tags.add(shiftTag);
		return tags;
	}

	/**
	 * Dato un Box e una Transazione esegue aggiunge la transazione al box. Se la
	 * transazione non può essere aggiunta il box eliminerà in modo autonomo la
	 * transazione. In questo caso verraà restituito {@code false}.
	 * 
	 * @param box il CashBox dove inserire la transazione.
	 * @param b1  la transazione da inserire.
	 * @return {@code true} se la transazione è stata aggiunta; {@code false}
	 *         altrimenti.
	 */
	private boolean executeTransaction(CashBox box, BasicTransaction b1) {
		box.updateBalance();
		if (box.addTransaction(b1)) {
			return box.updateBalance();
		}
		return false;
	}

	/**
	 * Metodo che permette di generare una collezzione di stringhe partendo da una
	 * collezione di elementi generici.
	 * 
	 * @param <T>  il tipo di elemento da trasformare in stringa;
	 * @param list la lista di elementi.
	 * @return una collezione di stringhe.
	 */
	public <T extends Object> Collection<String> generateStringList(Collection<T> list) {
		Stack<String> tempList = new Stack<>();
		for (T e : list) {
			tempList.add(e.toString());
		}
		return tempList;
	}

}
