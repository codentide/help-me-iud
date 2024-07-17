package co.edu.iudigital.helpmeiud.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {

    static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_name", unique = true)
    String username;

    @Column(length = 120, nullable = false)
    String name;

    @Column(name = "last_name", length = 120)
    String lastName;

    @Column
    String password;

    @Column
    String image;

    @Column
    Boolean enabled;

    @Column(name = "birth_date")
    LocalDate birthDate;

    @Column(name = "social_web")
    Boolean socialWeb;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    List<Role> roles;
}
