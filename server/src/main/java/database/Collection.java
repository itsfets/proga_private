package database;

import dto.*;

import java.sql.*;
import java.util.NavigableSet;
import java.util.TreeSet;

public final class Collection {
    private final Connection connection;

    public Collection(Connection connection) {
        this.connection = connection;
    }

    public NavigableSet<StudyGroup> getCollection() {
        NavigableSet<StudyGroup> result = new TreeSet<>();
        try (PreparedStatement stmt = connection.prepareStatement("""
                SELECT
                sg.id, sg.name AS group_name, sg.creation_date, sg.students_count, sg.transferred_students, sg.average_mark, sg.form_of_education, sg.group_admin, sg.owner, 
                c.x AS coord_x, c.y AS coord_y, 
                p.name AS admin_name, p.height AS admin_height, p.color AS admin_color, 
                l.x AS loc_x, l.y AS loc_y, l.z AS loc_z, l.name AS loc_name 
                FROM studygroups AS sg 
                INNER JOIN coordinates AS c ON sg.coord_x = c.x AND sg.coord_y = c.y
                LEFT JOIN persons AS p ON sg.group_admin = p.name 
                LEFT JOIN locations AS l ON p.loc_x = l.x AND p.loc_y = l.y AND p.loc_z = l.z;
                """)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Person admin = null;
                if (rs.getString("group_admin") != null) {
                    admin = new Person(
                            rs.getString("admin_name"),
                            rs.getDouble("admin_height"),
                            Color.valueOf(rs.getString("admin_color")),
                            new Location(
                                    rs.getFloat("loc_x"),
                                    rs.getDouble("loc_y"),
                                    rs.getLong("loc_z"),
                                    rs.getString("loc_name")
                            )
                    );
                }
                result.add(new StudyGroup(
                        rs.getInt("id"),
                        rs.getString("group_name"),
                        new Coordinates(
                                rs.getInt("coord_x"),
                                rs.getInt("coord_y")
                        ),
                        rs.getTimestamp("creation_date").toLocalDateTime(),
                        rs.getLong("students_count"),
                        rs.getLong("transferred_students"),
                        rs.getDouble("average_mark"),
                        FormOfEducation.valueOf(rs.getString("form_of_education")),
                        admin,
                        rs.getString("owner")
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch collection: " + e.getMessage(), e);
        }
    }

    private void insertLocation(Location location) throws SQLException {
        String sql = "INSERT INTO locations(x, y, z, name) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setFloat(1, location.getX());
            stmt.setDouble(2, location.getY());
            stmt.setLong(3, location.getZ());
            stmt.setString(4, location.getName());
            stmt.executeUpdate();
        }
    }

    private void insertPerson(Person person) throws SQLException {
        insertLocation(person.getLocation());
        String sql = "INSERT INTO persons(name, height, color, loc_x, loc_y, loc_z) VALUES (?, ?, ?::person_eye_color, ?, ?, ?) ON CONFLICT DO NOTHING";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setPerson(stmt, person);
            stmt.executeUpdate();
        }
    }

    private void insertCoordinates(Coordinates coordinates) throws SQLException {
        String sql = "INSERT INTO coordinates(x, y) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, coordinates.getX());
            stmt.setInt(2, coordinates.getY());
            stmt.executeUpdate();
        }
    }

    public NavigableSet<StudyGroup> insert(StudyGroup studyGroup) {
        String sql = "INSERT INTO studygroups(name, coord_x, coord_y, students_count, transferred_students, average_mark, form_of_education, group_admin, owner) VALUES (?, ?, ?, ?, ?, ?, ?::studygroup_form_of_education, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            insertCoordinates(studyGroup.getCoordinates());
            setStudyGroup(stmt, studyGroup);
            stmt.setString(9, studyGroup.getCreatedBy());
            if (stmt.executeUpdate() != 1) {
                System.out.println("Failed to insert studygroup");
            }
            return getCollection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert studygroup: " + e.getMessage(), e);
        }
    }

    private void updateLocation(Location location) throws SQLException {
        String sql = "UPDATE locations SET x = ?, y = ?, z = ?, name = ? WHERE x = ? AND y = ? AND z = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setFloat(1, location.getX());
            stmt.setDouble(2, location.getY());
            stmt.setLong(3, location.getZ());
            stmt.setString(4, location.getName());
            stmt.setFloat(5, location.getX());
            stmt.setDouble(6, location.getY());
            stmt.setLong(7, location.getZ());
            stmt.executeUpdate();
        }
    }

    private void updatePerson(Person person) throws SQLException {
        updateLocation(person.getLocation());
        String sql = "UPDATE persons SET name = ?, height = ?, color = ?, loc_x = ?, loc_y = ?, loc_z = ? WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setPerson(stmt, person);
            stmt.setString(7, person.getName());
            stmt.executeUpdate();
        }
    }

    private void updateCoordinates(Coordinates coordinates) throws SQLException {
        String sql = "UPDATE coordinates SET x = ?, y = ? WHERE x = ? AND y = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, coordinates.getX());
            stmt.setInt(2, coordinates.getY());
            stmt.setInt(3, coordinates.getX());
            stmt.setInt(4, coordinates.getY());
            stmt.executeUpdate();
        }
    }

    public NavigableSet<StudyGroup> update(StudyGroup studyGroup) {
        String sql = "UPDATE studygroups SET name = ?, coord_x = ?, coord_y = ?, students_count = ?, transferred_students = ?, average_mark = ?, form_of_education = ?, group_admin = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            updateCoordinates(studyGroup.getCoordinates());
            if (studyGroup.getGroupAdmin() != null) {
                updatePerson(studyGroup.getGroupAdmin());
            }
            setStudyGroup(stmt, studyGroup);
            stmt.setInt(9, studyGroup.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update studygroup: " + e.getMessage(), e);
        }
        return getCollection();
    }

    private void deleteStudyGroup(int id) throws SQLException {
        String sql = "DELETE FROM studygroups WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private void deleteOrphanedCoordinates(int x, int y) throws SQLException {
        String sql = """
                DELETE FROM coordinates 
                WHERE x = ? AND y = ? 
                AND NOT EXISTS (SELECT 1 FROM studygroups WHERE coord_x = ? AND coord_y = ?)
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, x);
            stmt.setInt(2, y);
            stmt.setInt(3, x);
            stmt.setInt(4, y);
            stmt.executeUpdate();
        }
    }

    private void deleteOrphanedPerson(String name) throws SQLException {
        String sql = """
                DELETE FROM persons 
                WHERE name = ? 
                AND NOT EXISTS (SELECT 1 FROM studygroups WHERE group_admin = ?)
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, name);
            stmt.executeUpdate();
        }
    }

    private void deleteOrphanedLocation(float x, double y, long z) throws SQLException {
        String sql = """
                DELETE FROM locations 
                WHERE x = ? AND y = ? AND z = ? 
                AND NOT EXISTS (SELECT 1 FROM persons WHERE loc_x = ? AND loc_y = ? AND loc_z = ?)
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setFloat(1, x);
            stmt.setDouble(2, y);
            stmt.setLong(3, z);
            stmt.setFloat(4, x);
            stmt.setDouble(5, y);
            stmt.setLong(6, z);
            stmt.executeUpdate();
        }
    }

    public NavigableSet<StudyGroup> delete(StudyGroup studyGroup) {
        try {
            deleteStudyGroup(studyGroup.getId());
            Coordinates coords = studyGroup.getCoordinates();
            deleteOrphanedCoordinates(coords.getX(), coords.getY());
            Person person = studyGroup.getGroupAdmin();
            if (person != null) {
                deleteOrphanedPerson(person.getName());
                Location loc = person.getLocation();
                deleteOrphanedLocation(loc.getX(), loc.getY(), loc.getZ());
            }
            return getCollection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete studygroup: " + e.getMessage(), e);
        }
    }

    private void setStudyGroup(PreparedStatement stmt, StudyGroup studyGroup) throws SQLException {
        Coordinates coords = studyGroup.getCoordinates();
        Person person = studyGroup.getGroupAdmin();
        stmt.setString(1, studyGroup.getName());
        stmt.setInt(2, coords.getX());
        stmt.setInt(3, coords.getY());
        stmt.setLong(4, studyGroup.getStudentsCount());
        stmt.setLong(5, studyGroup.getTransferredStudents());
        stmt.setDouble(6, studyGroup.getAverageMark());
        stmt.setObject(7, studyGroup.getFormOfEducation(), Types.OTHER);
        if (person != null) {
            insertPerson(person);
            stmt.setString(8, person.getName());
        } else {
            stmt.setNull(8, Types.VARCHAR);
        }
    }

    private void setPerson(PreparedStatement stmt, Person person) throws SQLException {
        Location loc = person.getLocation();
        stmt.setString(1, person.getName());
        stmt.setDouble(2, person.getHeight());
        stmt.setObject(3, person.getEyeColor(), Types.OTHER);
        stmt.setFloat(4, loc.getX());
        stmt.setDouble(5, loc.getY());
        stmt.setLong(6, loc.getZ());
    }

    public void clearCollection() {
        String sql = "TRUNCATE TABLE studygroups, coordinates, persons, locations RESTART IDENTITY CASCADE";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear collection: " + e.getMessage(), e);
        }
    }
}