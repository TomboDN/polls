package com.tombo.polls.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "votes")
public class Vote implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "poll_id", nullable = false)
    @ToString.Exclude
    private Poll poll;

    @OneToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Option> options;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    public Vote(Poll poll, User user) {
        this.poll = poll;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vote vote = (Vote) o;
        return id != null && Objects.equals(id, vote.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
