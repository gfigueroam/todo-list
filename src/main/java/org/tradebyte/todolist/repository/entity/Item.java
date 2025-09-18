package org.tradebyte.todolist.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.tradebyte.todolist.rest.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "items",
        indexes = {
                @Index(name = "idx_items_status", columnList = "status")
        })
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    @Builder.Default
    private Status status = Status.NOT_DONE;

    @CreationTimestamp
    @Column(updatable = false, name = "creation_datetime")
    @Builder.Default
    private LocalDateTime creationDatetime = LocalDateTime.now();

    @Column(name = "due_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dueDatetime;

    @Column(name = "done_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime doneDatetime;

    public void setStatus(Status status) {
        if (Status.DONE == status) {
            doneDatetime = LocalDateTime.now();
        } else if (Status.NOT_DONE == status) {
            doneDatetime = null;
        }
        this.status = status;
    }

    public boolean isPastDue() {
        return dueDatetime.isBefore(LocalDateTime.now()) && status.equals(Status.NOT_DONE);
    }

    public ItemDto toDto() {
        return ItemDto.builder()
                .id(id)
                .description(description)
                .status(status)
                .creationDatetime(creationDatetime)
                .dueDatetime(dueDatetime)
                .doneDatetime(doneDatetime)
                .build();
    }

}
