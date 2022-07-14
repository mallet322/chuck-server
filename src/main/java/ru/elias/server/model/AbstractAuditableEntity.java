package ru.elias.server.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class AbstractAuditableEntity {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
