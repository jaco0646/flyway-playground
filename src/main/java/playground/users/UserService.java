package playground.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import playground.flyway.User;
import playground.query.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository userRepo;

    Collection<User> findUsers(String field, String value) {
        if (field == null || value == null) {
            return userRepo.findAll();
        }
        return userRepo.findAll(getSpec(field, value));
    }

    // TODO handle numeric values
    private Specification<User> getSpec(String field, String value) {
        if (UserSpecification.LOOKUP.containsKey(field)) {
            return UserSpecification.LOOKUP.get(field).buildSpec(value);
        }
        String queryPattern = "%"+ value +"%";
        return (root, query, criteria) -> criteria.like(
                criteria.lower(root.get(field)),
                criteria.lower(criteria.literal(queryPattern)));
    }
}
