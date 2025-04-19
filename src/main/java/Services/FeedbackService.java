package Services;

import Entities.Feedback;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService {
    private Connection cnx;

    public FeedbackService(){
        cnx = MyDatabase.getInstance().getConnection();
    }

    public void ajouter(Feedback f) throws SQLException {
        String sql = "INSERT INTO feedback(user_id, subject, description, rating, date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, f.getUserId());
        ps.setString(2, f.getSubject());
        ps.setString(3, f.getDescription());
        ps.setInt(4, f.getRating());
        ps.setDate(5, Date.valueOf(f.getDate()));
        ps.executeUpdate();
    }

    public void modifier(Feedback f) throws SQLException {
        String sql = "UPDATE feedback SET subject = ?, description = ?, rating = ?, date = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, f.getSubject());
        ps.setString(2, f.getDescription());
        ps.setInt(3, f.getRating());
        ps.setDate(4, Date.valueOf(f.getDate()));
        ps.setInt(5, f.getId());
        ps.executeUpdate();
    }

    public void supprimer(Feedback f) throws SQLException {
        String sql = "DELETE FROM feedback WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, f.getId());
        ps.executeUpdate();
    }

    public List<Feedback> recuperer() throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT * FROM feedback";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            int userId = rs.getInt("user_id");
            String subject = rs.getString("subject");
            String description = rs.getString("description");
            int rating = rs.getInt("rating");
            LocalDate date = rs.getDate("date").toLocalDate();

            Feedback f = new Feedback(id, userId, subject, description, rating, date);
            feedbacks.add(f);
        }

        return feedbacks;
    }
}
