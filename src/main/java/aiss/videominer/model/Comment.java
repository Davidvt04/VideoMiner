package aiss.videominer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

/**
 * @author Juan C. Alonso
 */
@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("text")
    @Column(columnDefinition="TEXT")
    @NotEmpty(message = "Comment message cannot be empty or null")
    private String text;

    @JsonProperty("createdOn")
    @Column(name = "createdOn")
    @NotEmpty(message = "Comment creation time can not be empty")
    private String createdOn;

    @JsonProperty("author")
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull(message = "Comment author cannot be null")
    private User author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", author=" + author +
                '}';
    }
}
