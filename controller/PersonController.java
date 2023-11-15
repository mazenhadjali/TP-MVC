package controller;

import model.Person;

import java.util.List;

public interface PersonController {
    List<Person> getAllPersons();

    Person getPersonById(int personId);

    Person createPerson(Person person);

    Person updatePerson(Person person);

    boolean deletePerson(int personId);
}
