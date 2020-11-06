package it.unicam.cs.pa.jbudget105101.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class DebitBoxTest {

	@Test
	void testDebitBox() {
		assertThrows(NullPointerException.class, () -> new DebitBox(null, -100));
		assertThrows(IllegalArgumentException.class, () -> new DebitBox("DebitBoxTest", 10.0));

		assertEquals(-100.56, new DebitBox("DebitBoxTest", -100.564).getBalance());
		assertEquals(-100.57, new DebitBox("DebitBoxTest", -100.566).getBalance());
	}

	@Test
	void testAddTransaction() {
		DebitBox db = new DebitBox("DebitBoxTest", -100);
		IntegerIdManager idm = new IntegerIdManager();
		Set<Category> st = new HashSet<>();
		st.add(new Tag("Tag1", 1));

		assertFalse(db.addTransaction(
				new BasicTransaction(101, LocalDate.of(2010, 1, 1), "t1", st, idm.getNewIdentification())));

		assertTrue(db.addTransaction(
				new BasicTransaction(90, LocalDate.of(2010, 1, 1), "t2", st, idm.getNewIdentification())));
		db.updateBalance();
		assertEquals(-10, db.getBalance());

		assertThrows(IllegalArgumentException.class, () -> db.addTransaction(
				new BasicTransaction(-10, LocalDate.of(2010, 1, 1), "t1", st, idm.getNewIdentification())));
		db.updateBalance();
		assertEquals(-10, db.getBalance());

	}

}
