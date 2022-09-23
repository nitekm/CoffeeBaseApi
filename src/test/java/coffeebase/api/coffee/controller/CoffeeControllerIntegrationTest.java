package coffeebase.api.coffee.controller;

import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.Coffee;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
//TODO: test negative scenarios as well
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

//    @Test
//    void httpGet_returnAllCoffees() throws Exception {
//        //given
//        var user = new User("123", "username", "email");
//
//        coffee.setUser(user);
//        var coffee2 = new Coffee();
//        coffee2.setName("foo");
//        coffee2.setUser(user);
//        var coffee3 = new Coffee();
//        coffee3.setUser(user);
//        coffee3.setName("foo");
//
//
//        List<Coffee> coffees  = Arrays.asList(coffee, coffee2, coffee3);
//        coffeeRepo.saveAll(coffees);
//
//        //when
//        ResultActions response = mockMvc.perform(get("/coffees/")
//                .with(authentication(user))
//                .;
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

    @WithMockUser
    @Test
    void httpGetInvalidId_returnNotFound() throws Exception {
        //given
        int id = coffeeRepo.save(coffee).getId();

        //when
        ResultActions response = mockMvc.perform(get("/coffees/" + id+1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coffee)));

        //then
        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

//    @WithMockUser
//    @Test
//    void httpPost_createCoffee_returnCreated() throws Exception {
//        //given
//        coffee.setOrigin("bar");
//
//        //when
//        ResultActions response = mockMvc.perform(post("/coffees/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(coffee)));
//
//        //then
//        response.andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name", is(coffee.getName())))
//                .andExpect(jsonPath("$.origin", is(coffee.getOrigin())));
//    }

    @WithMockUser
    @Test
    void httpPut_updateCoffee_returnOK() throws Exception {
        //given
        int id = coffeeRepo.save(coffee).getId();
        var updatedCoffee = new Coffee();
        updatedCoffee.setName("updatedCoffeeName");

        //when
        ResultActions response = mockMvc.perform(put("/coffees/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCoffee)));

        //expect
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedCoffee.getName())));
    }

    @WithMockUser
    @Test
    void httpPutInvalidId_returnNotFound() throws Exception {
        //given
        int id = coffeeRepo.save(coffee).getId();
        var updatedCoffee = new Coffee();
        updatedCoffee.setName("updatedCoffeeName");

        //when
        ResultActions response = mockMvc.perform(put("/coffees/" + id+1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCoffee)));


        //then
        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void httpDelete_deleteCoffee_returnNoContent() throws Exception {
        //given
        int id = coffeeRepo.save(coffee).getId();

        //when
        ResultActions response = mockMvc.perform(delete("/coffees/" + id));

        //then
        response.andExpect(status().isNoContent());
    }

    @WithMockUser
    @Test
    void httpDeleteInvalidId_returnNotFound() throws Exception {
        //given
        int id = coffeeRepo.save(coffee).getId();

        //when
        ResultActions response = mockMvc.perform(delete("/coffees/" + id+1));

        //then
        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

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

    @WithMockUser
    @Test
    void httpPatchInvalidId_returnNotFound() throws Exception {
        //given
        int id = coffeeRepo.save(coffee).getId();

        //when
        ResultActions response = mockMvc.perform(patch("/coffees/" + id+1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coffee)));

        //then
        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

}
