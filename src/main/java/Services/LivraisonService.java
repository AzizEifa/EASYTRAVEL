package Services;



import Entities.Livraison;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivraisonService {
    private Connection cnx;

    public LivraisonService() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    // Method to insert a new livraison
    public void insertLivraison(Livraison livraison) throws SQLException {
        String query = "INSERT INTO livraison (depart, destination, date_livraison, prix) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setString(1, livraison.getDepart());
        pst.setString(2, livraison.getDestination());
        pst.setTimestamp(3, livraison.getDateLivraison());
        pst.setDouble(4, livraison.getPrix());
        pst.executeUpdate();
    }

    // Method to retrieve all livraisons
    public List<Livraison> getAllLivraisons() throws SQLException {
        List<Livraison> livraisons = new ArrayList<>();
        String query = "SELECT * FROM livraison";
        Statement stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Livraison livraison = new Livraison(
                    rs.getString("depart"),
                    rs.getString("destination"),
                    rs.getTimestamp("date_livraison"),
                    rs.getDouble("prix")
            );
            livraison.setId(rs.getInt("id"));
            livraisons.add(livraison);
        }
        return livraisons;
    }

    // Method to update an existing livraison
    public void updateLivraison(Livraison livraison) throws SQLException {
        String query = "UPDATE livraison SET depart = ?, destination = ?, date_livraison = ?, prix = ? WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setString(1, livraison.getDepart());
        pst.setString(2, livraison.getDestination());
        pst.setTimestamp(3, livraison.getDateLivraison());
        pst.setDouble(4, livraison.getPrix());
        pst.setInt(5, livraison.getId());
        pst.executeUpdate();
    }

    // Method to delete a livraison
    public void deleteLivraison(int id) throws SQLException {
        String query = "DELETE FROM livraison WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
    }
}
