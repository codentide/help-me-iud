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

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name", unique = true, length = 30)
    @Size(min = 5, max = 30)
    private String username;

    @Column(length = 120)
    private String name;

    @Column(name = "last_name", length = 120)
    private String lastName;

    @Column(length = 16)
    @Size(min = 8, max = 16)
    private String password;

    @Column
    private String image;

    @Column
    private Boolean enabled;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "social_web")
    private Boolean socialWeb;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "roles_users",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "roles_id")})
    List<Role> roles;
}
