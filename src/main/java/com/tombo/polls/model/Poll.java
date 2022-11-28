package com.tombo.polls.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "polls")
public class Poll implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "question", nullable = false)
    private String question;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Option> options = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    private User creator;
    @Column(name = "creation_date_time")
    private Instant creationDateTime;
    @Column(name = "multiple_choice")
    private boolean multipleChoice;
    @Column(name = "multiple_attempts")
    private boolean multipleAttempts;

    {
        options.add(new Option());
        options.add(new Option());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Poll poll = (Poll) o;
        return id != null && Objects.equals(id, poll.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String creationDateTimeString() {
        LocalDateTime datetime = LocalDateTime.ofInstant(creationDateTime, ZoneId.systemDefault());
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(datetime);
    }
}
