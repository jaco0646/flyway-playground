package playground.users;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import playground.flyway.User_;
import playground.jpa.metamodel.JpaModelGenUtil;
import playground.jpa.metamodel.SingularAttributesMap;

import java.util.Collection;

import static playground.StringUtil.anyMissing;

@Service
@RequiredArgsConstructor
class UserService {
    private static final SingularAttributesMap COLUMNS = JpaModelGenUtil.mapSingularAttributes(User_.class);
    private final UserRepository userRepo;

    Collection<User> findUsers(String field, String value) {
        if (anyMissing(field, value)) {
            return userRepo.findAll();
        }
        return userRepo.findAll(getSpec(field, value));
    }

    private static Specification<User> getSpec(String field, String value) {
        if (UserSpecification.LOOKUP.containsKey(field)) {
            return UserSpecification.LOOKUP.get(field).buildSpec(value);
        }
        String queryPattern = "%"+ value +"%";
        return (root, query, criteria) -> criteria.like(
                column(root, criteria, field),
                criteria.lower(criteria.literal(queryPattern)));
    }

    private static Expression<String> column(Root<?> root, CriteriaBuilder criteria, String field) {
        return COLUMNS.isNumeric(field)
                ? root.get(field)
                : criteria.lower(root.get(field));
    }
}
