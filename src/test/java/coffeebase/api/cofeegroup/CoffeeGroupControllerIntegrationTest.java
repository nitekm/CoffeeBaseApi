package coffeebase.api.cofeegroup;

import coffeebase.api.coffeegroup.CoffeeGroup;
import coffeebase.api.coffeegroup.CoffeeGroupRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static coffeebase.api.coffeegroup.CoffeeGroup.GroupType.METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class CoffeeGroupControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CoffeeGroupRepository coffeeGroupRepository;

    @Test
    void httpGet_returnAllCoffeeGroups() throws Exception {
        mockMvc.perform(get("/groups/"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void httpGet_returnGivenCoffeeGroup() throws Exception {
        //given
        int id = coffeeGroupRepository.save(new CoffeeGroup("name", METHOD)).getId();

        //expect
        mockMvc.perform(get("/groups/" + id))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void httpPost_createCoffeeGroup_returnCreated() throws Exception {
        //given
        CoffeeGroup coffeeGroup = new CoffeeGroup("name", METHOD);

        //expect
        mockMvc.perform(post("/groups/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(coffeeGroup)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void httpDelete_deleteCoffeeGroup_returnNoContent() throws Exception{
        //given
        CoffeeGroup coffeeGroup = new CoffeeGroup("name", METHOD);
        int id = coffeeGroup.getId();

        //expect
        mockMvc.perform(delete("/groups/" + id))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
