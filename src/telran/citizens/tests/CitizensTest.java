package telran.citizens.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.citizens.dao.Citizens;
import telran.citizens.dao.CitizensTreeSetImpl;
import telran.citizens.model.Person;

class CitizensTest {
	Citizens citizens;
	static final LocalDate now = LocalDate.now();

	@BeforeEach
	void setUp() throws Exception {
		citizens = new CitizensTreeSetImpl(List.of(new Person(1, "John", "Smith", now.minusYears(23)),
				new Person(42, "Mary", "Smith", now.minusYears(20)),
				new Person(3, "Peter", "Jackson", now.minusYears(27)),
				new Person(22, "Foma", "Rabinovich", now.minusYears(35))));
	}

	@Test
	void testAdd() {
		assertFalse(citizens.add(null));
		assertFalse(citizens.add(new Person(-1, "John2", "Smith2", now.minusYears(21))));
		assertEquals(4, citizens.size());
		assertFalse(citizens.add(new Person(1, "John2", "Smith2", now.minusYears(21))));
		assertEquals(4, citizens.size());
		assertTrue(citizens.add(new Person(5, "Piter", "Parker", now.minusYears(18))));
		assertEquals(5, citizens.size());

	}

	@Test
	void testRemove() {
		assertFalse(citizens.remove(5));
		assertEquals(4, citizens.size());
		assertTrue(citizens.remove(22));
		assertEquals(3, citizens.size());
	}

	@Test
	void testFindInt() {
		assertNull(citizens.find(5));
		Person person = citizens.find(42);
		assertEquals(42, person.getId());
		assertEquals("Mary", person.getFirstName());
		assertEquals("Smith", person.getLastName());
		assertEquals(now.minusYears(20), person.getBirthDate());
	}

	@Test
	void testFindIntInt() {
		Iterable<Person> res = citizens.find(20, 27);
		Iterable<Person> expected = List.of(new Person(42, "Mary", "Smith", now.minusYears(20)),
				new Person(1, "John", "Smith", now.minusYears(23)));
		assertIterableEquals(expected, res);
	}

	@Test
	void testFindString() {
		Iterable<Person> res = citizens.find("Smith");
		Iterable<Person> expected = List.of(new Person(1, "John", "Smith", now.minusYears(23)),
				new Person(42, "Mary", "Smith", now.minusYears(20)));
		assertIterableEquals(expected, res);
	}

	@Test
	void testGetAllPersonSortedById() {
		Iterable<Person> res = citizens.getAllPersonSortedById();
		Iterable<Person> expected = List.of(new Person(1, "John", "Smith", now.minusYears(23)),
				new Person(3, "Peter", "Jackson", now.minusYears(27)),
				new Person(22, "Foma", "Rabinovich", now.minusYears(35)),
				new Person(42, "Mary", "Smith", now.minusYears(20)));
		assertIterableEquals(expected, res);
	}

	@Test
	void testGetAllPersonSortedByLastName() {
		Iterable<Person> res = citizens.getAllPersonSortedByLastName();
		Iterable<Person> expected = List.of(new Person(3, "Peter", "Jackson", now.minusYears(27)),
				new Person(22, "Foma", "Rabinovich", now.minusYears(35)),
				new Person(1, "John", "Smith", now.minusYears(23)),
				new Person(42, "Mary", "Smith", now.minusYears(20)));
		assertIterableEquals(expected, res);
	}

	@Test
	void testGetAllPersonSortedByAge() {
		Iterable<Person> res = citizens.getAllPersonSortedByAge();
		Iterable<Person> expected = List.of(new Person(42, "Mary", "Smith", now.minusYears(20)),
				new Person(1, "John", "Smith", now.minusYears(23)),
				new Person(3, "Peter", "Jackson", now.minusYears(27)),
				new Person(22, "Foma", "Rabinovich", now.minusYears(35)));
		assertIterableEquals(expected, res);
	}

	@Test
	void testSize() {
		assertEquals(4, citizens.size());
	}

}
