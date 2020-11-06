package it.unicam.cs.pa.jbudget105101.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;

class FamilyBankTest {

	@Test
	void testFamilyBank() {
		assertThrows(IllegalArgumentException.class, () -> new FamilyBank(-1));
	}

	@Test
	void testGetBalance() throws ExistingElementException {
		FamilyBank fb = new FamilyBank(0);
		Wallet w = new Wallet("WalletTestBank", 100);
		
		fb.addCashBox(w);
		assertEquals(100, fb.getBalance());
		
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		w.addTransaction(new BasicTransaction(100, "t1", st, idm.getNewIdentification()));
		w.updateBalance();
		fb.updateBalance();
		assertEquals(200, fb.getBalance());
	}

	@Test
	void testGetCashBox() throws ExistingElementException {
		FamilyBank fb = new FamilyBank(0);
		Wallet w = new Wallet("WalletTestBank", 100);
		SaveBox sb = new SaveBox("SaveBoxTestBank", 100, 0.0);
		
		fb.addCashBox(w);
		fb.addCashBox(sb);
		
		assertEquals(w, fb.getCashBox(w.toString()));
		assertEquals(sb, fb.getCashBox(sb.toString()));
		assertEquals(null, fb.getCashBox("StringNull"));
		assertEquals(null, fb.getCashBox(null));
		
	}

	@Test
	void testAddCashBox() throws ExistingElementException {
		FamilyBank fb = new FamilyBank(0);
		Wallet w = new Wallet("WalletTestBank", 100);
		SaveBox sb = new SaveBox("SaveBoxTestBank", 100, 0.0);
		
		fb.addCashBox(w);
		fb.addCashBox(sb);
		assertThrows(ExistingElementException.class, () -> fb.addCashBox(w));
		
		Wallet w1 = new Wallet("WalletTestBank", 100);
		assertThrows(ExistingElementException.class, () -> fb.addCashBox(w1));
		
		Wallet w2 = new Wallet("WalletTestBank", 10);
		assertThrows(ExistingElementException.class, () -> fb.addCashBox(w2));
		
		assertThrows(NullPointerException.class, () -> fb.addCashBox(null));
	}

	@Test
	void testSubCashBox() throws ExistingElementException {
		FamilyBank fb = new FamilyBank(0);
		Wallet w = new Wallet("WalletTestBank", 100);
		SaveBox sb = new SaveBox("SaveBoxTestBank", 100, 0.0);
		DebitBox db = new DebitBox("DebitBoxTestBank", -100);
		
		fb.addCashBox(w);
		fb.addCashBox(sb);
		assertThrows(NullPointerException.class, () -> fb.subCashBox(null));
		
		assertThrows(NullPointerException.class, () -> fb.subCashBox(db));
		
		fb.subCashBox(sb);
		assertEquals(1, fb.getAllBox().size());
		assertEquals(w.toString(), fb.getCashBox(w.toString()).toString());
		
		fb.subCashBox(w);
		assertEquals(0, fb.getAllBox().size());
	}

	@Test
	void testUpdateBalance() throws ExistingElementException {
		FamilyBank fb = new FamilyBank(0);
		Wallet w = new Wallet("WalletTestBank", 100);
		SaveBox sb = new SaveBox("SaveBoxTestBank", 150, 0.0);
		DebitBox db = new DebitBox("DebitBoxTestBank", -100);
		
		fb.addCashBox(w);
		fb.addCashBox(sb);
		fb.addCashBox(db);
		fb.updateBalance();
		assertEquals(100, fb.getBalance());
		
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));
		BasicTransaction t = new BasicTransaction(100, LocalDate.of(2010, 1, 1), "t1", st, idm.getNewIdentification());
		sb.addTransaction(t);
		sb.updateBalance();
		fb.updateBalance();
		assertEquals(200, fb.getBalance());
		
		assertTrue(sb.subTransaction(t));
		sb.updateBalance();
		fb.updateBalance();
		assertEquals(100, fb.getBalance());
	}

}
