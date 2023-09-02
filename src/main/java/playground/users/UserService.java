package playground.users;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import playground.jpa.metamodel.JpaModelGenUtil;
import playground.jpa.metamodel.AttributesMap;

import java.util.Collection;

import static playground.StringUtil.anyMissing;

@Service
@RequiredArgsConstructor
class UserService {
    private static final AttributesMap COLUMNS = JpaModelGenUtil.mapAttributes(User_.class);
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
        return COLUMNS.isCollection(field)
                ? queryCollection(field, value)
                : querySingular(field, value);
    }

    private static Specification<User> queryCollection(String field, String value) {
        throw new UnsupportedOperationException("Collections are not queryable.");
    }

    private static Specification<User> querySingular(String field, String value) {
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
