package playground.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import playground.flyway.User_;

import java.util.Map;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
enum UserSpecification {
    FirstOrLastName("name", userQuery -> {
        String searchTerm = "%" + userQuery + "%";
        return (root, query, criteria) -> criteria.or(
            criteria.like(
                criteria.lower(root.get(User_.FIRST_NAME)),
                criteria.lower(criteria.literal(searchTerm))),
            criteria.like(
                criteria.lower(root.get(User_.LAST_NAME)),
                criteria.lower(criteria.literal(searchTerm)))
        );
    });

    static final Map<String, UserSpecification> LOOKUP = stream(values())
            .collect(toUnmodifiableMap(it -> it.key, identity()));

    private final String key;
    private final Function<String, Specification<User>> specBuilder;

    Specification<User> buildSpec(String value) {
        return specBuilder.apply(value);
    }
}
