package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.SpringBootAppApplicationTests;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class CityControllerTest extends SpringBootAppApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("All cities in the database should be provided in the response")
    public void testGetAllCities() throws Exception {
        mvc.perform(get("/v1/city/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response.length()", is(10)));
    }

    @Test
    @DisplayName("All cities in the database should be provided in the response sorted by time added")
    public void testGetCitiesSorted() throws Exception {
        mvc.perform(get("/v1/city/sorted")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response.length()", is(10)))
                .andExpect(jsonPath("$.response[0].name", is("Trogir")))
                .andExpect(jsonPath("$.response[1].name", is("Slavonski Brod")));
    }

    @Test
    @DisplayName("Cities which have been marked as favourite should be provided in the response sorted by the number of votes")
    public void testGetMostPopularCities() throws Exception {
        mvc.perform(get("/v1/city/popular")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response.length()", is(3)))
                .andExpect(jsonPath("$.response[0].name", is("Osijek")))
                .andExpect(jsonPath("$.response[0].favouriteCount", is(3)))
                .andExpect(jsonPath("$.response[1].name", is("Ilok")))
                .andExpect(jsonPath("$.response[2].name", is("Trogir")));
    }

}