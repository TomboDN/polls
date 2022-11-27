package com.tombo.polls.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Poll implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "question", nullable = false)
    private String question;
    @OneToMany
    @JoinColumn(name = "option_id")
    @OrderBy
    @ToString.Exclude
    private Set<Option> options;

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
}
