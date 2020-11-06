package it.unicam.cs.pa.jbudget105101.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class WalletTest {

	@Test
	void testWallet() {
		assertThrows(NullPointerException.class, () -> new Wallet(null, 0));
		assertThrows(IllegalArgumentException.class, () -> new Wallet("WalletTest", -1));
	}

	@Test
	void testGetGoal() {
		Wallet w = new Wallet("WalletTest", 0.0);
		assertThrows(UnsupportedOperationException.class, () -> w.getGoal());
	}

	@Test
	void testAddTransactionNull() {
		Wallet w = new Wallet("WalletTest", 0.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();

		assertThrows(NullPointerException.class,
				() -> w.addTransaction(new BasicTransaction(100, "t1", st, idm.getNewIdentification())));
		assertFalse(w.addTransaction(null));
	}

	@Test
	void testAddTransactionPositiveValue() {
		Wallet w = new Wallet("WalletTest", 0.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		w.addTransaction(new BasicTransaction(100, "t2", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(100.0, w.getBalance());

		w.addTransaction(new BasicTransaction(100, LocalDate.of(2010, 1, 1), "t1", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(100, LocalDate.of(2010, 1, 1), "t2", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(100, LocalDate.of(2010, 1, 1), "t3", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(100, LocalDate.of(2030, 1, 1), "t4", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(100, LocalDate.of(2030, 1, 1), "t5", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(100, LocalDate.of(2030, 1, 1), "t6", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(400.0, w.getBalance());
	}

	@Test
	void testAddTransactionNegativeValue() {
		Wallet w = new Wallet("WalletTest", 0.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		w.addTransaction(new BasicTransaction(1000, "t1", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(1000, w.getBalance());

		w.addTransaction(new BasicTransaction(-300, LocalDate.of(2010, 1, 1), "t2", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(-300, LocalDate.of(2010, 1, 1), "t3", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(-300, LocalDate.of(2010, 1, 1), "t4", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(-100, LocalDate.of(2030, 1, 1), "t5", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(-100, LocalDate.of(2030, 1, 1), "t6", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(-100, LocalDate.of(2030, 1, 1), "t7", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(100, w.getBalance());

		w.addTransaction(new BasicTransaction(-300, LocalDate.of(2010, 1, 1), "t8", st, idm.getNewIdentification()));
		assertEquals(100, w.getBalance());
	}

	@Test
	void testAddTransaction() {
		Wallet w = new Wallet("WalletTest", 10.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		w.addTransaction(new BasicTransaction(-100, "t1", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(100, "t2", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(110, w.getBalance());

		w.addTransaction(new BasicTransaction(100, "t3", st, idm.getNewIdentification()));
		w.addTransaction(new BasicTransaction(-200, "t4", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(10, w.getBalance());

	}
	
	@Test
	void testAddTransactionExist() {
		Wallet w = new Wallet("WalletTest", 0.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		IntegerId id = idm.getNewIdentification();

		BasicTransaction t2 = new BasicTransaction(100, "t2", st, id);
		BasicTransaction t3 = new BasicTransaction(100, "t2", st, id);
		
		assertTrue(w.addTransaction(t2));
		assertFalse(w.addTransaction(t3));
		

	}


	@Test
	void testCheckTransaction() {
		Wallet w = new Wallet("WalletTest", 10.0);
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		assertTrue(w.checkTransaction(10));
		assertTrue(w.checkTransaction(-10));
		assertFalse(w.checkTransaction(-50));

	}

	@Test
	void testSubTransactionNull() {
		Wallet w = new Wallet("WalletTest", 0.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();

		assertThrows(NullPointerException.class,
				() -> w.subTransaction(new BasicTransaction(100, "t1", st, idm.getNewIdentification())));

		st.add(new Tag("Tag1", 1));
		assertFalse(w.subTransaction(null));
		assertFalse(w.subTransaction(new BasicTransaction(100, "t1", st, idm.getNewIdentification())));

		BasicTransaction t = new BasicTransaction(100, "t1", st, idm.getNewIdentification());
		w.addTransaction(t);
		w.addTransaction(new BasicTransaction(-50, "t1", st, idm.getNewIdentification()));
		w.updateBalance();
		assertFalse(w.subTransaction(t));

	}

	@Test
	void testSubTransactionPositiveValue() {
		Wallet w = new Wallet("WalletTest", 0.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		BasicTransaction t1 = new BasicTransaction(100, LocalDate.of(2010, 1, 1), "t1", st, idm.getNewIdentification());
		BasicTransaction t2 = new BasicTransaction(100, LocalDate.of(2010, 1, 1), "t2", st, idm.getNewIdentification());
		BasicTransaction t3 = new BasicTransaction(-200, LocalDate.of(2010, 1, 1), "t3", st,
				idm.getNewIdentification());
		w.addTransaction(t1);
		w.addTransaction(t2);
		w.addTransaction(t3);
		w.updateBalance();
		assertEquals(0.0, w.getBalance());

		assertFalse(w.subTransaction(t1));
		w.updateBalance();
		assertEquals(0.0, w.getBalance());
	}

	@Test
	void testSubTransactionNegativeValue() {
		Wallet w = new Wallet("WalletTest", 0.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		BasicTransaction t1 = new BasicTransaction(100, LocalDate.of(2010, 1, 1), "t1", st, idm.getNewIdentification());
		BasicTransaction t2 = new BasicTransaction(100, LocalDate.of(2010, 1, 1), "t2", st, idm.getNewIdentification());
		BasicTransaction t3 = new BasicTransaction(-200, LocalDate.of(2010, 1, 1), "t3", st,
				idm.getNewIdentification());
		w.addTransaction(t1);
		w.addTransaction(t2);
		w.addTransaction(t3);
		w.updateBalance();
		assertEquals(0.0, w.getBalance());

		assertTrue(w.subTransaction(t3));
		w.updateBalance();
		assertEquals(200.0, w.getBalance());
	}

	@Test
	void testSubTransactionNotFound() {
		Wallet w = new Wallet("WalletTest", 0.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		BasicTransaction t1 = new BasicTransaction(100, "t1", st, idm.getNewIdentification());
		w.addTransaction(new BasicTransaction(100, "t1", st, idm.getNewIdentification()));
		assertFalse(w.subTransaction(t1));

		IntegerId id = idm.getNewIdentification();

		BasicTransaction t2 = new BasicTransaction(100, "t2", st, id);
		BasicTransaction t3 = new BasicTransaction(100, "t2", st, id);
		w.addTransaction(t2);
		w.updateBalance();
		assertTrue(w.subTransaction(t3));
		

	}

	@Test
	void testUpdateBalance() {
		Wallet w = new Wallet("WalletTest", 0.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		w.addTransaction(new BasicTransaction(100, "t1", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(100.0, w.getBalance());
		w.addTransaction(new BasicTransaction(100, "t2", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(200.0, w.getBalance());

		w.addTransaction(new BasicTransaction(-100, "t3", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(100.0, w.getBalance());
		w.addTransaction(new BasicTransaction(-100, "t4", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(0.0, w.getBalance());

		BasicTransaction t5 = new BasicTransaction(100, "t5", st, idm.getNewIdentification());
		w.addTransaction(t5);
		w.updateBalance();
		w.addTransaction(new BasicTransaction(-50, "t2", st, idm.getNewIdentification()));
		w.updateBalance();
		assertEquals(50.0, w.getBalance());
		w.subTransaction(t5);
		w.updateBalance();
		assertEquals(50.0, w.getBalance());
	}

}
