package playground


import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher
import spock.lang.Shared
import spock.lang.Specification


class MatcherUtilSpec extends Specification {
    @Shared
    ResultMatcher pass = result -> { return }

    @Shared
    ResultMatcher fail = result -> { throw new RuntimeException('Failing ResultMatcher') }

    @Shared
    MvcResult mvcResult = Mock()

    def 'Test Either Matcher passes when one or both matchers pass'() {
        when:
            MatcherUtil.either(a, b).match(mvcResult)
        then:
            noExceptionThrown()
        where:
            a    | b
            pass | pass
            pass | fail
            fail | pass
    }

    def 'Test Either Matcher fails when both matchers fail'() {
        when:
            MatcherUtil.either(fail, fail).match(mvcResult)
        then:
            def e = thrown(AssertionError)
            e.suppressed.size() == 2
            e.suppressed.every{it.message == 'Failing ResultMatcher'}
    }
}