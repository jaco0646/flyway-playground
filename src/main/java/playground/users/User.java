package playground.users;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "user_table")  // "user" is a keyword in H2
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String email;

    private int age;

    // Without this annotation, jpamodelgen will not populate the Attribute.
    @Type(ListArrayType.class)
    @Column(columnDefinition = "text array")
    private List<String> hobbies;
}