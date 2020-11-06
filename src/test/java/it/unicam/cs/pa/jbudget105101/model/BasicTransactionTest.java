/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unicam.cs.pa.jbudget105101.model.enums.TypeTransaction;

/**
 * @author Damiano
 *
 */
class BasicTransactionTest {
	
	Tag t1 = new Tag("Spesa", 2);
	Tag t2 = new Tag("Casa", 2);
	Tag t3 = new Tag("Salute", 1);
	Tag t4 = new Tag("Auto", 3);
	
	Set<Category> tl1 = new HashSet<>();
	
	IntegerIdManager iim = new IntegerIdManager();
	
	@Test void testBasicTransaction(){
		
		tl1.clear();
		
		tl1.add(t1);
		tl1.add(t2);
		tl1.add(t3);
		tl1.add(t4);
		
		assertThrows(NullPointerException.class,
                () -> new BasicTransaction(-20.57, null, tl1, iim.getNewIdentification()));
		assertThrows(NullPointerException.class,
                () -> new BasicTransaction(30, "Test", null, iim.getNewIdentification()));
		
		assertThrows(NullPointerException.class,
                () -> new BasicTransaction(-00, null, "@Test", tl1, iim.getNewIdentification()));
		
		assertThrows(NullPointerException.class,
                () -> new BasicTransaction(10.0, null, "@Test", new HashSet<Category>(), iim.getNewIdentification()));
	}
	
	@Test void testGetType() {
		
		tl1.add(t1);
		tl1.add(t2);
		
		LocalDate ld = LocalDate.of(2020, 8, 20);
		
		BasicTransaction t = new BasicTransaction(20.0, "credits", tl1, iim.getNewIdentification());
		BasicTransaction t1 = new BasicTransaction(-20.0, ld, "debit", tl1, iim.getNewIdentification());
		
		assertEquals(TypeTransaction.CREDITS, t.getType());
		assertEquals(TypeTransaction.DEBIT, t1.getType());
	}
	
	@Test void testGetValue() {
		tl1.clear();
		
		tl1.add(t1);
		tl1.add(t2);
		
		BasicTransaction t = new BasicTransaction(20.0, "pippo", tl1, iim.getNewIdentification());
		
		assertEquals(20.0, t.getValue());
	}
	
	@Test void testGetDate() {
		
		tl1.add(t1);
		tl1.add(t2);
		
		LocalDate ld = LocalDate.of(2020, 8, 20);
		
		BasicTransaction t = new BasicTransaction(20.0, "pippo", tl1, iim.getNewIdentification());
		BasicTransaction t1 = new BasicTransaction(-20.0, ld, "pippo", tl1, iim.getNewIdentification());
		
		assertEquals(LocalDate.now(), t.getDate());
		assertEquals(ld, t1.getDate());
	}
	
	@Test void testGetInfo() {
		
		tl1.add(t1);
		tl1.add(t2);
		
		BasicTransaction t = new BasicTransaction(20.0, "pippo", tl1, iim.getNewIdentification());
		assertEquals("pippo", t.getInfo());
	}
	
	
	
}
