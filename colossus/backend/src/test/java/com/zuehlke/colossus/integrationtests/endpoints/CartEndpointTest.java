package com.zuehlke.colossus.integrationtests.endpoints;

import com.zuehlke.colossus.capabilities.json.JacksonJson;
import com.zuehlke.colossus.cart.model.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("inttest")
class CartEndpointTest {

    private JacksonJson json = JacksonJson.create();

    private String sessionId = UUID.randomUUID().toString();

    private MockHttpServletRequestBuilder getFromEndpoint() {
        return get("/cart")
          .accept(MediaType.APPLICATION_JSON)
          .header("COLOSSUS_SESSION", sessionId);
    }

    @Test
    void get_shoppingcart(@Autowired MockMvc mockMvc) throws Exception {
        // given

        // when
        String actualBody =
          mockMvc
            .perform(getFromEndpoint())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        // then
        assertThat(actualBody).isNotNull();
        Cart cart = json.parse(actualBody, Cart.class);
        assertThat(cart.sessionid()).isEqualTo(sessionId);
    }

}
