package playground.jpa.view

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class SecondarySchemaRepoSpec extends Specification {
    @Autowired
    SecondarySchemaService service

    def 'Test View Query'() {
        given:
            def views = service.findKeyLike('se')
        expect:
            views.size() == 1
            views.first().getK() == 'consectetur'
    }
}
