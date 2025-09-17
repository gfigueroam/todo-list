package org.tradebyte.todolist.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private Status status = Status.NOT_DONE;

    @CreationTimestamp
    @Column(updatable = false, name = "creation_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDatetime = LocalDateTime.now();

    @Column(name = "due_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dueDatetime;

    @Column(name = "done_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime doneDatetime;

}
