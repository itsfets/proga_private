package database;

import dto.StudyGroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NavigableSet;

public final class Manager {
    private final Connection connection;
    private final Authentificator authentificator;
    private final Collection dbCollection;

    public Manager(String login, String password) {
        this.connection = connect(login, password);
        this.authentificator = new Authentificator(connection);
        if (!init()) throw new RuntimeException("failed to init database, server cannot keep running without it");
        this.dbCollection = new Collection(connection);
    }

    public Connection connect(String login, String password) {
        try {
            return DriverManager.getConnection("jdbc:postgresql://pg/studs", login, password);
        } catch (SQLException e) {
            System.out.println("couldn't connect to database: " + e.getMessage());
            return null;
        }
    }

    public void disconnect() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("couldn't close connection to database: " + e.getMessage());
        }
    }

    public boolean tryAuth(String login, String password) {
        return authentificator.checkAuth(login, password);
    }

    private boolean init() {
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS users(login VARCHAR(32) PRIMARY KEY, password_hash VARCHAR(32) NOT NULL);").executeUpdate();
            if (!connection.prepareStatement("SELECT 1 FROM pg_type WHERE typname = 'person_eye_color';").executeQuery().next()) {
                connection.prepareStatement("CREATE TYPE person_eye_color AS ENUM('GREEN', 'RED', 'YELLOW', 'WHITE', 'BROWN');").executeUpdate();
            }
            connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS locations(
                    x REAL NOT NULL, 
                    y DOUBLE PRECISION NOT NULL, 
                    z BIGINT NOT NULL, 
                    name VARCHAR(255), 
                    PRIMARY KEY (x, y, z)
                    );"""
            ).executeUpdate();
            connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS persons(
                    name VARCHAR(255) PRIMARY KEY CHECK (TRIM(name) <> ''), 
                    height DOUBLE PRECISION CHECK (height > 0), 
                    color person_eye_color, 
                    loc_x INTEGER NOT NULL, 
                    loc_y INTEGER NOT NULL, 
                    loc_z INTEGER NOT NULL, 
                    FOREIGN KEY (loc_x, loc_y, loc_z) REFERENCES locations(x, y, z) ON DELETE CASCADE
                    );"""
            ).executeUpdate();
            if (!connection.prepareStatement("SELECT 1 FROM pg_type WHERE typname = 'studygroup_form_of_education';").executeQuery().next()) {
                connection.prepareStatement("CREATE TYPE studygroup_form_of_education AS ENUM('DISTANCE_EDUCATION', 'FULL_TIME_EDUCATION', 'EVENING_CLASSES');").executeUpdate();
            }
            connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS coordinates(
                    x INTEGER NOT NULL CHECK(x > -740), 
                    y INTEGER NOT NULL, 
                    PRIMARY KEY (x, y)
                    );"""
            ).executeUpdate();
            connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS studygroups(
                    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY, 
                    name VARCHAR(32) NOT NULL CHECK (TRIM(name) <> ''), 
                    coord_x INTEGER NOT NULL, 
                    coord_y INTEGER NOT NULL, 
                    creation_date TIMESTAMP NOT NULL DEFAULT NOW(), 
                    students_count BIGINT CHECK (students_count > 0), 
                    transferred_students BIGINT CHECK (transferred_students > 0), 
                    average_mark DOUBLE PRECISION CHECK (average_mark > 0), 
                    form_of_education studygroup_form_of_education NOT NULL, 
                    group_admin VARCHAR(255) REFERENCES persons(name), 
                    owner VARCHAR(32) REFERENCES users(login), 
                    FOREIGN KEY (coord_x, coord_y) REFERENCES coordinates(x, y) ON DELETE CASCADE
                    );"""
            ).executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void clearCollection() {
        dbCollection.clearCollection();
    }

    public NavigableSet<StudyGroup> getCollection() {
        return dbCollection.getCollection();
    }

    public NavigableSet<StudyGroup> insert(StudyGroup studyGroup) {
        return dbCollection.insert(studyGroup);
    }

    public NavigableSet<StudyGroup> update(StudyGroup studyGroup) {
        return dbCollection.update(studyGroup);
    }

    public NavigableSet<StudyGroup> delete(StudyGroup studyGroup) {
        return dbCollection.delete(studyGroup);
    }
}
