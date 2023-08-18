package playground

import org.springframework.test.util.ExceptionCollector
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher

class MatcherUtil {
    static ResultMatcher either(ResultMatcher a, ResultMatcher b) {
        any(a, b)
    }

    static ResultMatcher any(ResultMatcher... matchers) {
        (MvcResult result) -> {
            def exceptionCollector = new ExceptionCollector()
            for (int i = 0; i < matchers.size(); i++) {
                exceptionCollector.execute{ matchers[i].match(result) }
                if (exceptionCollector.exceptions.size() <= i) {
                    return
                }
            }
            exceptionCollector.assertEmpty()
        }
    }
}
