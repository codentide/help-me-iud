package co.edu.iudigital.helpmeiud.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "crimes")
@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Crime {

    static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "Nombre requerido")
    @Column(unique = true, length = 100, nullable = false)
    String name;

    @Column
    String description;

    @NotNull(message = "Usuario requerido")
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
            updatedAt = LocalDateTime.now();
    }
}
