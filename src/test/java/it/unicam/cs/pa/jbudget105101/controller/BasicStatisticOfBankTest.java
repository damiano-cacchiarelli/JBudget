package it.unicam.cs.pa.jbudget105101.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unicam.cs.pa.jbudget105101.model.BasicTransaction;
import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Category;
import it.unicam.cs.pa.jbudget105101.model.FamilyBank;
import it.unicam.cs.pa.jbudget105101.model.IntegerIdManager;
import it.unicam.cs.pa.jbudget105101.model.SaveBox;
import it.unicam.cs.pa.jbudget105101.model.Tag;
import it.unicam.cs.pa.jbudget105101.model.TagManager;
import it.unicam.cs.pa.jbudget105101.model.Wallet;
import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;

class BasicStatisticOfBankTest {

	TagManager tm = getTagManager();
	IntegerIdManager idm = getIntegerIdManager();
	FamilyBank fb = getFamilyBank();
	BasicStatisticOfBank<FamilyBank> s = new BasicStatisticOfBank<>(fb);

	@Test
	void testOutlayOfMonth() {
		assertEquals(22, s.outlayOfMonth(LocalDate.now()));

	}

	@Test
	void testOutlayOfCategory() {
		assertEquals(120, s.outlayOfCategory(tm.getCategory("[Spesa, 2]")));
	}

	@Test
	void testOutlayPercentOfCategory() {
		assertEquals(0.0, s.outlayPercentOfCategory(tm.getCategory("[Salute, 1]")));
		assertEquals(98.36, s.outlayPercentOfCategory(tm.getCategory("[Spesa, 2]")));
	}

	@Test
	void testOutlayOfDay() {
		assertEquals(22, s.outlayOfDay(LocalDate.now()));
	}

	@Test
	void testNumberOfTransactionInDay() {
		assertEquals(4, s.numberOfTransactionInDay(LocalDate.now()));
	}

	@Test
	void testGetBalancePercent() {
			SaveBox sb = new SaveBox("Test", 100, 0);
			sb.addTransaction(new BasicTransaction(50, "t", getTag1(), idm.getNewIdentification()));
			sb.updateBalance();
			
			assertEquals(0.5, s.getBalancePercent(sb));
	}

	private IntegerIdManager getIntegerIdManager() {
		return new IntegerIdManager();
	}

	private TagManager getTagManager() {
		TagManager tm = new TagManager();
		addTag(tm);
		return tm;
	}

	private FamilyBank getFamilyBank() {
		FamilyBank fb = new FamilyBank(1000);
		addCashBox(fb);
		fb.updateBalance();
		return fb;
	}

	private void addCashBox(FamilyBank fb2) {
		try {
			fb2.addCashBox(new Wallet("Banca", 100));
			fb2.addCashBox(new Wallet("Cassa", 100));
			addTransaction(fb2.getCashBox("[Cassa] WALLET"));
			addTransaction(fb2.getCashBox("[Banca] WALLET"));
			fb2.updateBalance();

		} catch (ExistingElementException e) {
			e.printStackTrace();
		}
	}

	private void addTransaction(CashBox cb) {
		cb.addTransaction(new BasicTransaction(-10, LocalDate.now(), "t1", getTag1(), idm.getNewIdentification()));

		cb.addTransaction(
				new BasicTransaction(-50, LocalDate.now().plusMonths(1), "t2", getTag2(), idm.getNewIdentification()));

		cb.addTransaction(
				new BasicTransaction(-50, LocalDate.now().plusMonths(-1), "t3", getTag3(), idm.getNewIdentification()));

		cb.addTransaction(new BasicTransaction(-1, LocalDate.now(), "t4", getTag4(), idm.getNewIdentification()));

		cb.updateBalance();
	}

	private Set<Category> getTag1() {
		Set<Category> tag = new HashSet<>();
		tag.add(tm.getCategory("[Casa, 1]"));
		tag.add(tm.getCategory("[Spesa, 2]"));
		return tag;
	}

	private Set<Category> getTag2() {
		Set<Category> tag = new HashSet<>();
		tag.add(tm.getCategory("[Salute, 1]"));
		return tag;
	}

	private Set<Category> getTag3() {
		Set<Category> tag = new HashSet<>();
		tag.add(tm.getCategory("[Divertimento, 3]"));
		tag.add(tm.getCategory("[Spesa, 2]"));
		return tag;
	}

	private Set<Category> getTag4() {
		Set<Category> tag = new HashSet<>();
		tag.add(tm.getCategory("[Shift, 0]"));
		return tag;
	}

	private void addTag(TagManager tm2) {
		tm2.addCategory(new Tag("Casa", 1));
		tm2.addCategory(new Tag("Spesa", 2));
		tm2.addCategory(new Tag("Salute", 1));
		tm2.addCategory(new Tag("Divertimento", 3));
		tm2.addCategory(new Tag("Shift", 0));

	}

}
