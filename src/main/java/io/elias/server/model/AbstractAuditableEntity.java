package io.elias.server.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@NoArgsConstructor
@MappedSuperclass
@Data
public class AbstractAuditableEntity {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
