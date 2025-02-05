package co.edu.iudigital.helpmeiud.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role implements Serializable {

    static final long serialVersionUID = 1L;

    public Role(){
    }

    public Role(Long id) {
        this.id = id;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, length = 100)
    String name;
    @Column
    String description;

    @ManyToMany( mappedBy = "roles")
    @JsonBackReference
    List<User> users;

}
