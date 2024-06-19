package playground.jpa.metamodel

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import playground.users.User_
import spock.lang.Specification

@DataJpaTest
class JpaModelGenUtilSpec extends Specification {

    def 'Generate AttributesMap with numeric and non-numeric assignments'() {
        given:
            def map = JpaModelGenUtil.mapAttributes(User_.class)
        expect:
            map.isNumeric(column) == expectedNumeric
            map.isCollection(column) == expectedCollection
        where:
            column      | expectedNumeric | expectedCollection
            'firstName' | false           | false
            'lastName'  | false           | false
            'id'        | true            | false
            'email'     | false           | false
            'age'       | true            | false
            'hobbies'   | false           | false  // Unfortunately not recognized as collection.
    }

    def 'Throw exception when column name is not found'() {
        given:
            def map = JpaModelGenUtil.mapAttributes(User_.class)
        when:
            map.isNumeric('foo')
        then:
            def e = thrown(NoSuchElementException)
            e.message.contains('foo')
    }
}
