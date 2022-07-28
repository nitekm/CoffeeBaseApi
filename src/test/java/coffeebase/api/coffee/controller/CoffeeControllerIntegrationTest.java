package coffeebase.api.coffee.controller;

import coffeebase.api.coffee.model.Coffee;
import coffeebase.api.coffee.repository.CoffeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CoffeeControllerIntegrationTest {

    private Coffee coffee;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CoffeeRepository coffeeRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        coffeeRepo.deleteAll();
        coffee = new Coffee();
        coffee.setName("foo");
        coffee.setFavourite(false);
    }

//    @WithMockUser
//    @Test
//    void httpGet_returnAllCoffees() throws Exception {
//        //given
//        var coffee = new Coffee();
//        coffee.setName("foo");
//        var coffee2 = new Coffee();
//        coffee2.setName("foo");
//        var coffee3 = new Coffee();
//        coffee3.setName("foo");
//
//        List<Coffee> coffees  = Arrays.asList(coffee, coffee2, coffee3);
//        coffeeRepo.saveAll(coffees);
//
//        //when
//        ResultActions response = mockMvc.perform(get("/coffees/"));
//
//        //then
//        response.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.size()", is(coffees.size())));
//    }

    @WithMockUser
    @Test
    void httpGet_returnGivenCoffee() throws Exception {
        //given
        int id = coffeeRepo.save(coffee).getId();

        //when
        ResultActions response = mockMvc.perform(get("/coffees/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coffee)));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(coffee.getId())))
                .andExpect(jsonPath("$.name", is(coffee.getName())));
    }

    //
//    @Test
//    void httpPost_createCoffee_returnCreated() throws Exception {
//        //given
//        Coffee coffee = new Coffee("foo", "", "", 1, "");
//
//        //expect
//        mockMvc.perform(post("/coffees/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(coffee)))
//                .andDo(print())
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    void httpPut_updateCoffee_returnOK() throws Exception {
//        //given
//        Coffee coffee = new Coffee("foo", "", "", 1, "");
//        int id = coffee.getId();
//
//        //expect
//        mockMvc.perform(put("/coffees/" + id)
//                .content(asJsonString(coffee))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//    }
//
//    @Test
//    void httpDelete_deleteCoffee_returnNoContent() throws Exception{
//        //given
//        Coffee coffee = new Coffee("foo", "", "", 1, "");
//        int id = coffee.getId();
//
//        //expect
//        mockMvc.perform(delete("/coffees/" + id))
//                .andExpect(status().isNoContent());
//    }
//
    @WithMockUser
    @Test
    void httpPatch_returnOk() throws Exception {
        //given
        int id = coffeeRepo.save(coffee).getId();


        //when
        ResultActions response = mockMvc.perform(patch("/coffees/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coffee)));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(coffee.getName())))
                .andExpect(jsonPath("$.favourite", is(!coffee.isFavourite())));
    }

}
