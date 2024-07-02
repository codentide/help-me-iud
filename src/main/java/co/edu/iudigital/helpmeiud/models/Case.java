package co.edu.iudigital.helpmeiud.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "cases")
@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Case {

    static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "date_time")
    LocalDateTime dateTime;
    @Column
    Long latitude;
    @Column
    Long longitude;
    @Column
    Long altitude;
    @Column
    Boolean visible;
    @Column
    String description;
    @Column(name = "map_url")
    String mapUrl;
    @Column(name = "rmi_url")
    String rmiUrl;

    //userId;
    @ManyToOne @JoinColumn(name = "user_id")
    User userId;

    //crimeId;
    @ManyToOne @JoinColumn(name = "crime_id")
    Crime crimeId;
}
