package Services;

import Entities.Trajet;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrajetService {
    private Connection cnx;

    public TrajetService() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    // CREATE
    public void add(Trajet trajet) throws SQLException {
        String query = "INSERT INTO trajet (depart, destination, distance, date_depart, prix) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, trajet.getDepart());
            ps.setString(2, trajet.getDestination());
            ps.setDouble(3, trajet.getDistance());
            ps.setTimestamp(4, Timestamp.valueOf(trajet.getDateDepart()));
            ps.setDouble(5, trajet.getPrix());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) trajet.setId(rs.getInt(1));
        }
    }

    // READ ALL
    public List<Trajet> getAll() throws SQLException {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM trajet";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Trajet t = new Trajet(
                        rs.getString("depart"),
                        rs.getString("destination"),
                        rs.getDouble("distance"),
                        rs.getTimestamp("date_depart").toLocalDateTime(),
                        rs.getDouble("prix")
                );
                t.setId(rs.getInt("id"));
                trajets.add(t);
            }
        }
        return trajets;
    }

    // UPDATE
    public void update(Trajet trajet) throws SQLException {
        String query = "UPDATE trajet SET depart=?, destination=?, distance=?, date_depart=?, prix=? WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, trajet.getDepart());
            ps.setString(2, trajet.getDestination());
            ps.setDouble(3, trajet.getDistance());
            ps.setTimestamp(4, Timestamp.valueOf(trajet.getDateDepart()));
            ps.setDouble(5, trajet.getPrix());
            ps.setInt(6, trajet.getId());
            ps.executeUpdate();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM trajet WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}