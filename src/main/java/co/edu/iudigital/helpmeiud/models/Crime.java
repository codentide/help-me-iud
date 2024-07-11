package co.edu.iudigital.helpmeiud.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "crimes")
@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Crime {

    static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull(message = "Nombre requerido")
    @Column(unique = true, length = 100, nullable = false)
    String name;

    @Column
    String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
