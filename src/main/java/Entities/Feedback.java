package Entities;

import java.time.LocalDate;

public class Feedback {
    private int id;
    private int userId;
    private String subject;
    private String description;
    private int rating;
    private LocalDate date;

    public Feedback(int id, int userId, String subject, String description, int rating, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.description = description;
        this.rating = rating;
        this.date = date;
    }

    public Feedback(int userId, String subject, String description, int rating, LocalDate date) {
        this.userId = userId;
        this.subject = subject;
        this.description = description;
        this.rating = rating;
        this.date = date;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public int getRating() { return rating; }
    public LocalDate getDate() { return date; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setDescription(String description) { this.description = description; }
    public void setRating(int rating) { this.rating = rating; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", userId=" + userId +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", date=" + date +
                '}';
    }
}
