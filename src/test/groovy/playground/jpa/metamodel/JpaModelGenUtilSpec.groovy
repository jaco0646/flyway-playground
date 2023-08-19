package playground.jpa.metamodel

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import playground.flyway.User_
import spock.lang.Specification

@DataJpaTest
class JpaModelGenUtilSpec extends Specification {

    def 'Generate SingularAttributesMap with numeric and non-numeric assignments'() {
        given:
            def map = JpaModelGenUtil.mapSingularAttributes(User_.class)
        expect:
            map.isNumeric(column) == expected
        where:
            column      || expected
            'firstName' || false
            'lastName'  || false
            'id'        || true
            'email'     || false
            'age'       || true
    }

    def 'Throw exception when column name is not found'() {
        given:
            def map = JpaModelGenUtil.mapSingularAttributes(User_.class)
        when:
            map.isNumeric('foo')
        then:
            def e = thrown(NoSuchElementException)
            e.message.contains('foo')
    }
}
