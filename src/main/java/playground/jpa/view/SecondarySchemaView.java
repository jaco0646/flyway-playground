package playground.jpa.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "public_view_of_schema2")
public class SecondarySchemaView {
    @Id
    private String k;

    private String v;
}
