package config;

public class SQLQueries {
    public static final String CREATE_PERSON_TABLE =
            "CREATE TABLE IF NOT EXISTS person (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nom VARCHAR(255) NOT NULL)";

    public static final String SELECT_ALL_PERSONS =
            "SELECT * FROM person";

    public static final String SELECT_PERSON_BY_ID =
            "SELECT * FROM person WHERE id = ?";

    public static final String UPDATE_PERSON =
            "UPDATE person SET nom = ? WHERE id = ?";

    public static final String DELETE_PERSON =
            "DELETE FROM person WHERE id = ?";
    public static final String INSERT_PERSON =
            "INSERT INTO person (nom) VALUES (?)";

}
