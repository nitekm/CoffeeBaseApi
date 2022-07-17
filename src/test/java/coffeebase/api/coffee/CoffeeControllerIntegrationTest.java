//package coffeebase.api.coffee;
//
//import coffeebase.api.coffee.repository.CoffeeRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("integration")
//public class CoffeeControllerIntegrationTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    CoffeeRepository coffeeRepo;
//
//    @Test
//    void httpGet_returnAllCoffees() throws Exception {
//        mockMvc.perform(get("/coffees/"))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//    }
////
////    @Test
////    void httpGet_returnGivenCoffee() throws Exception {
////        //given
////        int id = coffeeRepo.save(new Coffee("foo", "", "", 1, "")).getId();
////
////        //expect
////        mockMvc.perform(get("/coffees/" + id))
////                .andExpect(status().is2xxSuccessful());
////    }
////
////    @Test
////    void httpPost_createCoffee_returnCreated() throws Exception {
////        //given
////        Coffee coffee = new Coffee("foo", "", "", 1, "");
////
////        //expect
////        mockMvc.perform(post("/coffees/")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(asJsonString(coffee)))
////                .andDo(print())
////                .andExpect(status().isCreated());
////    }
////
////    @Test
////    void httpPut_updateCoffee_returnOK() throws Exception {
////        //given
////        Coffee coffee = new Coffee("foo", "", "", 1, "");
////        int id = coffee.getId();
////
////        //expect
////        mockMvc.perform(put("/coffees/" + id)
////                .content(asJsonString(coffee))
////                .contentType(MediaType.APPLICATION_JSON))
////                .andDo(print())
////                .andExpect(status().is2xxSuccessful());
////    }
////
////    @Test
////    void httpDelete_deleteCoffee_returnNoContent() throws Exception{
////        //given
////        Coffee coffee = new Coffee("foo", "", "", 1, "");
////        int id = coffee.getId();
////
////        //expect
////        mockMvc.perform(delete("/coffees/" + id))
////                .andExpect(status().isNoContent());
////    }
////
////    @Test
////    void httpPatch_returnOk() throws Exception {
////        //given
////        int id = coffeeRepo.save(new Coffee("foo", "", "", 1, "")).getId();
////
////        //expect
////        mockMvc.perform(patch("/coffees/" + id))
////                .andExpect(status().is2xxSuccessful());
////    }
////
////
////
////    private static String asJsonString(final Object obj) {
////        try {
////            return new ObjectMapper().writeValueAsString(obj);
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
////    }
//}
