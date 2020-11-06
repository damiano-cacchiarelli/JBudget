package it.unicam.cs.pa.jbudget105101.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class SaveBoxTest {

	@Test
	void testSaveBox() {
		assertThrows(NullPointerException.class, () -> new SaveBox(null, 100, 0.0));
		assertThrows(IllegalArgumentException.class, () -> new SaveBox("SaveBoxTest", -1, 0.0));
		assertThrows(IllegalArgumentException.class, () -> new SaveBox("SaveBoxTest", 10.0, 100.0));
		assertThrows(IllegalArgumentException.class, () -> new SaveBox("SaveBoxTest", 100.0, -1));
	}

	@Test
	void testGetGoal() {
		SaveBox sb = new SaveBox("SaveBoxTest", 100.524852, 0.0);
		assertEquals(100.52, sb.getGoal());
	}

	@Test
	void testAddTransaction() {
		SaveBox sb = new SaveBox("SaveBoxTest", 100, 90.0);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		assertFalse(sb.addTransaction(
				new BasicTransaction(100, LocalDate.of(2010, 1, 1), "t1", st, idm.getNewIdentification())));
		assertTrue(sb.addTransaction(
				new BasicTransaction(5, LocalDate.of(2010, 1, 1), "t2", st, idm.getNewIdentification())));
		sb.updateBalance();
		assertEquals(95.0, sb.getBalance());

		assertFalse(sb.addTransaction(
				new BasicTransaction(10, LocalDate.of(2010, 1, 1), "t3", st, idm.getNewIdentification())));
		sb.updateBalance();
		assertTrue(sb.addTransaction(
				new BasicTransaction(5, LocalDate.of(2010, 1, 1), "t4", st, idm.getNewIdentification())));
		sb.updateBalance();
		assertFalse(sb.addTransaction(
				new BasicTransaction(5, LocalDate.of(2010, 1, 1), "t5", st, idm.getNewIdentification())));
		sb.updateBalance();
		assertEquals(100.0, sb.getBalance());

	}

	@Test
	void testSubTransaction() {
		SaveBox sb = new SaveBox("SaveBoxTest", 100, 50);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		BasicTransaction t1 = new BasicTransaction(10, LocalDate.of(2010, 1, 1), "t1", st, idm.getNewIdentification());
		BasicTransaction t2 = new BasicTransaction(10, LocalDate.of(2010, 1, 1), "t2", st, idm.getNewIdentification());
		sb.addTransaction(t1);
		sb.addTransaction(t2);
		sb.updateBalance();
		
		assertTrue(sb.subTransaction(t1));
		sb.updateBalance();
		assertEquals(60.0, sb.getBalance());

		BasicTransaction t3 = new BasicTransaction(20, LocalDate.of(2010, 1, 1), "t3", st, idm.getNewIdentification());
		BasicTransaction t4 = new BasicTransaction(20, LocalDate.of(2010, 1, 1), "t4", st, idm.getNewIdentification());
		sb.addTransaction(t3);
		sb.addTransaction(t4);
		sb.updateBalance();
		
		assertFalse(sb.subTransaction(t1));
		sb.updateBalance();
		assertEquals(100.0, sb.getBalance());
	}

	@Test
	void testCheckTransaction() {
		SaveBox sb = new SaveBox("SaveBoxTest", 100, 50);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));
		
		assertFalse(sb.checkTransaction(51));
		assertFalse(sb.checkTransaction(-51));
		
		BasicTransaction t1 = new BasicTransaction(50.006, LocalDate.of(2010, 1, 1), "t1", st, idm.getNewIdentification());
		assertFalse(sb.checkTransaction(t1.getValue()));
		
		BasicTransaction t2 = new BasicTransaction(50.005, LocalDate.of(2010, 1, 1), "t2", st, idm.getNewIdentification());
		assertFalse(sb.checkTransaction(t2.getValue()));
		
		BasicTransaction t3 = new BasicTransaction(50.004, LocalDate.of(2010, 1, 1), "t3", st, idm.getNewIdentification());
		assertTrue(sb.checkTransaction(t3.getValue()));
	}

}
