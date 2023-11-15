package controller;

import config.MySQLConnection;
import config.SQLQueries;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonControllerImpl implements PersonController {


    public PersonControllerImpl() {
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();

        try (Connection connection = MySQLConnection.getInstance().getConnection()) {
            String query = SQLQueries.SELECT_ALL_PERSONS;
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");

                    Person person = new Person(id, nom);
                    persons.add(person);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public Person getPersonById(int personId) {
        try (Connection connection = MySQLConnection.getInstance().getConnection()) {
            String query = SQLQueries.SELECT_PERSON_BY_ID;
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, personId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nom = resultSet.getString("nom");

                        return new Person(id, nom);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Person createPerson(Person person) {
        try (Connection connection = MySQLConnection.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.INSERT_PERSON, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, person.getNom());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating person failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        person.setId(id);
                        return person;
                    } else {
                        throw new SQLException("Creating person failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Person updatePerson(Person person) {
        try (Connection connection = MySQLConnection.getInstance().getConnection()) {
            String query = SQLQueries.UPDATE_PERSON;
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, person.getNom());
                statement.setInt(2, person.getId());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Updating person failed, no rows affected.");
                }

                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deletePerson(int personId) {
        try (Connection connection = MySQLConnection.getInstance().getConnection()) {
            String query = SQLQueries.DELETE_PERSON;
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, personId);

                int affectedRows = statement.executeUpdate();

                return affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
