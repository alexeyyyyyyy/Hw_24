package telran.citizens.dao;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import telran.citizens.model.Person;

public class CitizensTreeSetImpl implements Citizens {
	private TreeSet<Person> idList;
	private TreeSet<Person> lastNameList;
	private TreeSet<Person> ageList;

	private static Comparator<Person> idComparator = (p1, p2) -> Integer.compare(p1.getId(), p2.getId());
	private static Comparator<Person> lastNameComparator = (p1, p2) -> {
		int res = p1.getLastName().compareToIgnoreCase(p2.getLastName());
		return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
	};
	private static Comparator<Person> ageComparator = (p1, p2) -> {
		int res = Integer.compare(p1.getAge(), p2.getAge());
		return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
	};

	public CitizensTreeSetImpl() {
		idList = new TreeSet<>(idComparator);
		lastNameList = new TreeSet<>(lastNameComparator);
		ageList = new TreeSet<>(ageComparator);
	}

	public CitizensTreeSetImpl(List<Person> listPerson) {
		this();
		listPerson.forEach(this::add);
	}

	// O(log n)
	@Override
	public boolean add(Person person) {
		 if (person == null || person.getId() < 0 || person.getBirthDate() == null) {
		        return false; 
		    }

		boolean addedToId = idList.add(person);
		boolean addedToLastName = lastNameList.add(person);
		boolean addedToAge = ageList.add(person);

		return addedToId && addedToLastName && addedToAge;
	}

	// O(log n)
	@Override

	public boolean remove(int id) {
	
		

		boolean removedFromId = idList.remove(id);
		boolean removedFromLastName = lastNameList.remove(id);
		boolean removedFromAge = ageList.remove(id);

		return removedFromId && removedFromLastName && removedFromAge;
	}

	// O(log n)
	@Override
	public Person find(int id) {
		Person pattern = new Person(id, null, null, null);
		if (idList.contains(pattern)) {
			return idList.floor(pattern);
		}
		return null;
	}

	// O(log n)
	@Override
	public Iterable<Person> find(int minAge, int maxAge) {
		Person start = new Person(Integer.MIN_VALUE, "Hanna", "Miller", LocalDate.now().minusYears(minAge));
		Person end = new Person(Integer.MAX_VALUE, "Hanna", "John", LocalDate.now().minusYears(maxAge));

		return ageList.subSet(start, end);
	}

	// O(log n)
	@Override
	public Iterable<Person> find(String lastName) {
		Person pattern = new Person(Integer.MIN_VALUE, lastName, null, null);
		return lastNameList.subSet(pattern, new Person(Integer.MAX_VALUE, lastName, null, null));
	}

	// O(n)
	@Override
	public Iterable<Person> getAllPersonSortedById() {
		return idList;
	}

	// O(n)
	@Override
	public Iterable<Person> getAllPersonSortedByLastName() {
		return lastNameList;
	}

	// O(n)
	@Override
	public Iterable<Person> getAllPersonSortedByAge() {
		return ageList;
	}

	// O(1)
	@Override
	public int size() {
		return idList.size();
	}
}
