package playground.users


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import playground.query.UserRepository
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc

    @Autowired
    UserRepository userRepo

    def "Find All Users"() {
        given:
            int expectedSize = (int) userRepo.count()
        expect:
            mockMvc.perform(get('/user'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(expectedSize)))
    }
}
