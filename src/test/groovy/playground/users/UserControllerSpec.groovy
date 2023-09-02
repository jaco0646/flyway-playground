package playground.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Ignore
import spock.lang.Specification

import static org.hamcrest.Matchers.*
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc

    @Autowired
    UserRepository userRepo

    def 'Return all Users when either query param is omitted'() {
        given:
            int expectedSize = (int) userRepo.count()
        expect:
            mockMvc.perform(get('/user')
                    .param('field', field)
                    .param('value', query))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(expectedSize)))
        where:
            field   | query
            'email' | ''
            'email' | ' '
            ''      | 'gmail'
            ' '     | 'gmail'
    }

    def 'Return Users found by single column query'() {
        given:
            def matches = userRepo.findAll().findAll{it.email.containsIgnoreCase(query)}
        expect:
            matches
            mockMvc.perform(get('/user')
                    .param('field', 'email')
                    .param('value', query))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(matches.size())))
                .andExpect(jsonPath('$.*.email', everyItem(containsStringIgnoringCase(query))))
        where:
            query << ['JOHN', 'doe', 'gMail', '.com']
    }

    def 'Return Users found by multi-column query'() {
        given:
            def matches = userRepo.findAll().findAll{
                it.firstName.containsIgnoreCase(query) || it.lastName.containsIgnoreCase(query)
            }
            def emails = matches.collect{it.email}.toArray()
        expect:
            matches
            mockMvc.perform(get('/user')
                    .param('field', 'name')
                    .param('value', query))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(matches.size())))
                .andExpect(jsonPath('$.*.email', containsInAnyOrder(emails)))
//                .andExpect(either(
//                        jsonPath('$.*.firstName', everyItem(containsStringIgnoringCase(query))),
//                        jsonPath('$.*.lastName', everyItem(containsStringIgnoringCase(query)))
//                ))
        where:
            query << ['JOHN', 'doe', 'FOO']
    }

    def 'Return Users found by numeric query'() {
        given:
            def matches = userRepo.findAll().findAll{it.age.toString().contains(query)}
        expect:
            matches.isEmpty() == isExpectedEmpty
            mockMvc.perform(get('/user')
                    .param('field', 'age')
                    .param('value', query))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(matches.size())))
                .andExpect(jsonPath('$.*.age', everyItem(hasToString(containsString(query)))))
        where:
            query | isExpectedEmpty
            '4'   | false
            'bar' | true
    }

    @Ignore
    def 'Return Users found by Collection query'() {
        expect:
            mockMvc.perform(get('/user')
                    .param('field', 'hobbies')
                    .param('value', 'foo'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.*.hobbies', everyItem(hasSize(2))))
    }

    def 'Return nothing when query param is not found'() {
        expect:
            mockMvc.perform(get('/user')
                    .param('field', 'email')
                    .param('value', 'abcdefg'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(0)))
    }

    def 'Throw exception when query field is invalid column name'() {
        expect:
            mockMvc.perform(get('/user')
                    .param('field', 'foo')
                    .param('value', 'bar'))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString('foo')))
    }
}
