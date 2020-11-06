package it.unicam.cs.pa.jbudget105101.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unicam.cs.pa.jbudget105101.model.BasicTransaction;
import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Category;
import it.unicam.cs.pa.jbudget105101.model.DebitBox;
import it.unicam.cs.pa.jbudget105101.model.FamilyBank;
import it.unicam.cs.pa.jbudget105101.model.IntegerIdManager;
import it.unicam.cs.pa.jbudget105101.model.SaveBox;
import it.unicam.cs.pa.jbudget105101.model.Tag;
import it.unicam.cs.pa.jbudget105101.model.TagManager;
import it.unicam.cs.pa.jbudget105101.model.Wallet;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeCashBox;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeScheduler;
import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;

class FamilyBankControllerTest {

	TagManager tm = getTagManager();
	IntegerIdManager idm = getIntegerIdManager();
	FamilyBank fb = getFamilyBank();
	FamilyBankController fbc = new FamilyBankController(fb, tm, idm);

	@Test
	void testGetCashBoxTypeCashBox() {
		assertEquals(3, fbc.getCashBox(TypeCashBox.WALLET).size());
		assertEquals(2, fbc.getCashBox(TypeCashBox.SAVEBOX).size());
		assertEquals(1, fbc.getCashBox(TypeCashBox.DEBITBOX).size());
	}

	@Test
	void testSchedulerTransaction() {
		try {
			fbc.addWallet("TestScheduler", 100);

			fbc.schedulerTransaction(TypeScheduler.TEMPORAL, "[TestScheduler] WALLET", -10, LocalDate.of(2010, 01, 01),
					"Transazioni Schedulate negative", getTag1(), 10, "MONTHLY");
			assertEquals(0.0, fbc.getCashBox("[TestScheduler] WALLET").getBalance());

			fbc.schedulerTransaction(TypeScheduler.TEMPORAL, "[TestScheduler] WALLET", 10, LocalDate.of(2010, 01, 01),
					"Transazioni Schedulate positive", getTag2(), 9, "MONTHLY");
			assertEquals(90.0, fbc.getCashBox("[TestScheduler] WALLET").getBalance());

			assertThrows(IllegalArgumentException.class,
					() -> fbc.schedulerTransaction(TypeScheduler.TEMPORAL, "[TestScheduler] WALLET", -10,
							LocalDate.of(2010, 01, 01), "Transazioni Schedulate negative", getTag1(), 10, "MONTHLY"));
			assertEquals(0.0, fbc.getCashBox("[TestScheduler] WALLET").getBalance());

		} catch (ExistingElementException e) {
			fail("ExistingElementException");
		}
	}

	@Test
	void testSearchTransaction() {
		assertEquals(3, fbc.searchTransaction("[Banca] WALLET", null, null).size());
		
		assertEquals(1,	fbc.searchTransaction("[Banca] WALLET", "[Salute, 1]", null).size());
		
		assertEquals(2, fbc.searchTransaction("[Banca] WALLET", "[Spesa, 2]", null).size());

		assertEquals(3, fbc.searchTransaction("[Banca] WALLET", null, LocalDate.now()).size());
		assertEquals(0, fbc.searchTransaction("[Banca] WALLET", null, LocalDate.of(2010, 01, 01)).size());

	}

	@Test
	void testShiftMoney() {
		double balance1 = fbc.getCashBox("[Banca] WALLET").getBalance();
		double balance2 = fbc.getCashBox("[Cassa] WALLET").getBalance();
		assertTrue(fbc.shiftMoney("[Banca] WALLET", 10.0, "[Cassa] WALLET"));
		assertEquals(balance1 - 10.0, fbc.getCashBox("[Banca] WALLET").getBalance());
		assertEquals(balance2 + 10.0, fbc.getCashBox("[Cassa] WALLET").getBalance());

		assertFalse(fbc.shiftMoney("[Banca] WALLET", 1000.0, "[Cassa] WALLET"));

		assertEquals(balance1 - 10.0, fbc.getCashBox("[Banca] WALLET").getBalance());
		assertEquals(balance2 + 10.0, fbc.getCashBox("[Cassa] WALLET").getBalance());
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
		return fb;
	}

	private void addCashBox(FamilyBank fb2) {
		try {
			fb2.addCashBox(new Wallet("Banca", 100));
			fb2.addCashBox(new Wallet("Cassa", 100));
			fb2.addCashBox(new Wallet("Carta di debito", 100));
			addTransaction(fb2.getCashBox("[Banca] WALLET"));
			addTransaction(fb2.getCashBox("[Cassa] WALLET"));
			addTransaction(fb2.getCashBox("[Carta di debito] WALLET"));

			fb2.addCashBox(new SaveBox("Risparmi", 1000, 0));
			fb2.addCashBox(new SaveBox("PC nuovo", 1000, 0));
			addTransaction(fb2.getCashBox("[Risparmi] SAVEBOX"));
			addTransaction(fb2.getCashBox("[PC nuovo] SAVEBOX"));

			fb2.addCashBox(new DebitBox("Mutuo", -10000));
			addTransaction(fb2.getCashBox("[Mutuo] DEBITBOX"));
			
			fb2.updateBalance();
		} catch (ExistingElementException e) {
			e.printStackTrace();
		}
	}

	private void addTransaction(CashBox cb) {
		cb.addTransaction(new BasicTransaction(10, "t1" + cb.getName(), getTag1(), idm.getNewIdentification()));
		cb.addTransaction(new BasicTransaction(10, "t2" + cb.getName(), getTag2(), idm.getNewIdentification()));
		cb.addTransaction(new BasicTransaction(10, "t3" + cb.getName(), getTag3(), idm.getNewIdentification()));
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

	private void addTag(TagManager tm2) {
		tm2.addCategory(new Tag("Casa", 1));
		tm2.addCategory(new Tag("Spesa", 2));
		tm2.addCategory(new Tag("Salute", 1));
		tm2.addCategory(new Tag("Divertimento", 3));

	}

}
