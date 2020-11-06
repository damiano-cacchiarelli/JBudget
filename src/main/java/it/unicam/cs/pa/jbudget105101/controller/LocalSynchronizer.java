/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.model.Bank;
import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Category;
import it.unicam.cs.pa.jbudget105101.model.CategoryManager;
import it.unicam.cs.pa.jbudget105101.model.FamilyBank;
import it.unicam.cs.pa.jbudget105101.model.IdentificationManager;
import it.unicam.cs.pa.jbudget105101.model.Identifier;
import it.unicam.cs.pa.jbudget105101.model.IntegerIdManager;
import it.unicam.cs.pa.jbudget105101.model.TagManager;
import it.unicam.cs.pa.jbudget105101.model.Transaction;

/**
 * <p>
 * La classe {@code LocalSynchronizer} implementa un {@link Synchronizer} che
 * permette di salvare e caricare localmente lo stato degli elementi.
 * </p>
 * <p>
 * Per salvare e caricare i vari elementi viene utilizzata la libreria
 * {@code Gson} che permette di convertire oggetti Java nella loro
 * rappresentazione {@code JSON}, ma anche per convertire una stringa JSON in un
 * oggetto Java equivalente.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class LocalSynchronizer implements Synchronizer<FamilyBank, TagManager, IntegerIdManager> {

	/**
	 * Il builder personalizzato per serializzare e deserializzare le istanze delle
	 * classi.
	 */
	private GsonBuilder builder = new GsonBuilder();
	/**
	 * Istanza della classe Gson.
	 */
	private Gson gson;

	/**
	 * Il path dove verranno caricati e salvati i dati.
	 */
	private Path path = Paths.get(System.getProperty("user.dir"), "resources", "json");

	/**
	 * Costruisce un {@code LocalSynchronizer} e inizializza l'oggetto gson per
	 * salvare e caricare i dati.
	 */
	public LocalSynchronizer() {
		registerAdapterGson();
		gson = builder.serializeNulls().create();
	}

	/**
	 * Metodo di supporto per personalizzare il builder. Vengono aggiunti tutti gli
	 * adapter necessari per permettere la corretta serializzazione e
	 * deserializzazione dei dati.
	 */
	private void registerAdapterGson() {
		builder.registerTypeAdapter(Category.class, new InterfaceAdapterGson<>());
		builder.registerTypeAdapter(CategoryManager.class, new InterfaceAdapterGson<>());
		builder.registerTypeAdapter(Transaction.class, new InterfaceAdapterGson<>());
		builder.registerTypeAdapter(CashBox.class, new InterfaceAdapterGson<>());
		builder.registerTypeAdapter(Bank.class, new InterfaceAdapterGson<>());
		builder.registerTypeAdapter(MasterScheduler.class, new InterfaceAdapterGson<>());
		builder.registerTypeAdapter(IdentificationManager.class, new InterfaceAdapterGson<>());
		builder.registerTypeAdapter(Identifier.class, new InterfaceAdapterGson<>());

	}

	/**
	 * Permette di ottenere un'istanza della classe {@link FamilyBank} costruita
	 * deserializzando i dati contenuti nel file "BankGSON.json". Se non viene
	 * trovato alcun file o dato il metodo ritorna {@code null}.
	 * 
	 * @return l'oggetto caricato o {@code null}.
	 */
	@Override
	public FamilyBank loadBank() {
		String gsonBank = readGsonStringFromFile("BankGSON.json");
		if (gsonBank == "") {
			return null;
		}
		return gson.fromJson(gsonBank, FamilyBank.class);
	}

	/**
	 * Permette di ottenere un'istanza della classe {@link TagManager} costruita
	 * deserializzando i dati contenuti nel file "CatGSON.json". Se non viene
	 * trovato alcun file o dato il metodo ritorna {@code null}.
	 * 
	 * @return l'oggetto caricato o {@code null}.
	 */
	@Override
	public TagManager loadCategoryManager() {
		String gsonCm = readGsonStringFromFile("CatGSON.json");
		if (gsonCm == "") {
			return null;
		}
		return gson.fromJson(gsonCm, TagManager.class);
	}

	/**
	 * Permette di ottenere un'istanza della classe {@link IntegerIdManager}
	 * costruita deserializzando i dati contenuti nel file "IdManagerGSON.json". Se
	 * non viene trovato alcun file o dato il metodo ritorna {@code null}.
	 * 
	 * @return l'oggetto caricato o {@code null}.
	 */
	@Override
	public IntegerIdManager loadIdManager() {
		String gsonIdm = readGsonStringFromFile("IdManagerGSON.json");
		if (gsonIdm == "") {
			return null;
		}
		return gson.fromJson(gsonIdm, IntegerIdManager.class);
	}

	/**
	 * Serializza le istanze delle classi {@link FamilyBank}, {@link TagManager} e
	 * {@link IntegerIdManager} e salva i dati ottenuti in tre file separati
	 * rispettivamente in : "BankGSON.json", "CatGSON.json" e "IdManagerGSON.json".
	 * 
	 * @param bank                  la {@code FamilyBank} che si vuole salvare;
	 * @param categoryManager       il {@code TagManager} di categorie che si vuole
	 *                              salvare;
	 * @param identificationManager l' {@code IntegerIdManager} che si vuole
	 *                              salvare.
	 */
	@Override
	public void saveData(FamilyBank bank, TagManager categoryManager, IntegerIdManager identificationManager) {

		String jsonBank = gson.toJson(bank, FamilyBank.class);
		String jsonCm = gson.toJson(categoryManager, TagManager.class);
		String jsonIdm = gson.toJson(identificationManager, IntegerIdManager.class);

		save(jsonBank, getWriter("BankGSON.json"));
		save(jsonCm, getWriter("CatGSON.json"));
		save(jsonIdm, getWriter("IdManagerGSON.json"));
		App.logger.info("Salvataggio completato!");

	}

	/**
	 * Permette di leggere il contenuto di un determinato file e restituirlo sotto
	 * forma di stringa. Se non viene trovato alcun file o il file è vuoto viene
	 * restituita una stringa vuota.
	 * 
	 * @param nameFile il nome del file al quale si vuole leggere il contenuto
	 * @return la stringa corrispondente al contenuto del file
	 */
	private String readGsonStringFromFile(String nameFile) {
		String gsonString = "";
		BufferedReader br = getReader(nameFile);
		if (br != null) {
			try {
				App.logger.info("Read file : " + nameFile);
				String tString = br.readLine();
				while (tString != null) {
					gsonString = gsonString + tString;
					tString = br.readLine();
				}
			} catch (IOException e) {
				App.logger.severe(e.toString());
			}
		}
		return gsonString;
	}

	/**
	 * Permette di cercare e, nel caso in cui non esista, di crare il file con un
	 * determinato nome all'interno della directory impostata.
	 * 
	 * @param nameFile il file che si vuole cercare o creare.
	 * @return il file
	 */
	private File generateFile(String nameFile) {
		App.logger.info("Cerco il File : " + nameFile);
		File file = path.resolve(nameFile).toFile();
		try {
			if (file.createNewFile())
				App.logger.info("Il File non esiste, ne ho creato uno nuovo");
		} catch (IOException e) {
			App.logger.severe(e.getMessage());
		}
		return file;

	}

	/**
	 * Permette di scrivere una determinata stringa nel file.
	 * 
	 * @param jsonStringElement la stringa che si vuole salvare.
	 * @param bw                {@link BufferedReader} per scrivere la stringa nel
	 *                          file.
	 */
	private void save(String jsonStringElement, BufferedWriter bw) {
		if (bw != null) {
			try {
				App.logger.info("Scrivo su File");
				bw.write(jsonStringElement);
				bw.close();
			} catch (IOException e) {
				App.logger.severe("IOException");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Restituisce un {@link BufferedWriter} per il file. Se il file non esiste ne
	 * viene crato uno nuovo.
	 * 
	 * @param nameFile il nome del file
	 * @return il {@code BufferedWriter} per quel file.
	 */
	private BufferedWriter getWriter(String nameFile) {
		try {
			App.logger.info("Generazione nuovo Writer per " + nameFile);
			return new BufferedWriter(new FileWriter(generateFile(nameFile)));
		} catch (IOException e) {
			App.logger.severe(e.toString());
			return null;
		}

	}

	/**
	 * Restituisce un {@link BufferedReader} per il file. Se il file non esiste ne
	 * viene crato uno nuovo.
	 * @param nameFile il nome del file.
	 * @return il {@code BufferedReader} per quel file.
	 */
	private BufferedReader getReader(String nameFile) {
		try {
			App.logger.info("Generazione nuovo Reader per " + nameFile);
			return new BufferedReader(new FileReader(generateFile(nameFile)));
		} catch (IOException e) {
			App.logger.severe(e.toString());
			return null;
		}
	}

}
