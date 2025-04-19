package Services;

import Entities.User;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Connection cnx;

    public UserService(){
        cnx = MyDatabase.getInstance().getConnection();
    }
    public void ajouter(User u) throws SQLException {
        String sql = "INSERT INTO user(age, nom, prenom, email, phoneNumber, address, role, password, gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, u.getAge());
        ps.setString(2, u.getNom());
        ps.setString(3, u.getPrenom());
        ps.setString(4, u.getEmail());
        ps.setString(5, u.getPhoneNumber());
        ps.setString(6, u.getAddress());
        ps.setString(7, u.getRole());
        ps.setString(8, u.getPassword());
        ps.setString(9, u.getGender());
        ps.executeUpdate();
    }

    public void modifier(User u) throws SQLException {
        String sql = "UPDATE user SET age = ?, nom = ?, prenom = ?, email = ?, phoneNumber = ?, " +
                "address = ?, role = ?, password = ?, gender = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, u.getAge());
        ps.setString(2, u.getNom());
        ps.setString(3, u.getPrenom());
        ps.setString(4, u.getEmail());
        ps.setString(5, u.getPhoneNumber());
        ps.setString(6, u.getAddress());
        ps.setString(7, u.getRole());
        ps.setString(8, u.getPassword());
        ps.setString(9, u.getGender());
        ps.setInt(10, u.getId());
        ps.executeUpdate();
    }

    public void supprimer(User u) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, u.getId());
        ps.executeUpdate();
    }

    public List<User> recuperer() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phoneNumber");
            String address = rs.getString("address");
            String role = rs.getString("role");
            String password = rs.getString("password");
            String gender = rs.getString("gender");

            User u = new User(id, age, nom, prenom, email, phoneNumber, address, role, password, gender);
            users.add(u);
        }

        return users;
    }
}
