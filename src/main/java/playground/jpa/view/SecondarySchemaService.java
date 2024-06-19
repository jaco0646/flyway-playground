package playground.jpa.view;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecondarySchemaService {
    private final SecondarySchemaRepo repo;

    public List<SSProjection> findKeyLike(String key) {
        Specification<SecondarySchemaView> spec =
                (root, query, builder) -> builder.like(root.get("k"), "%" + key + "%");
        return repo.findBy(spec, q -> q.as(SSProjection.class).all());
    }
}
