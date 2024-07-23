package com.pack.card.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pack.card.exception.CardNotFoundException;
import com.pack.card.model.Card;
import com.pack.card.service.CardService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CardController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CardControllerUnitTest {
    @Autowired
    private CardController cardController;

    @MockBean
    private CardService cardService;

    /**
     * Method under test: {@link CardController#issueCard(Card)}
     */
    @Test
    void testIssueCard() throws Exception {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        when(cardService.issueCard(Mockito.<Card>any())).thenReturn(card);

        Card card2 = new Card();
        card2.setCardHolderName("Card Holder Name");
        card2.setCardNumber("42");
        card2.setExpiryDate("2020-03-01");
        card2.setSpendingLimit(10.0d);
        card2.setStatus("Status");
        String content = (new ObjectMapper()).writeValueAsString(card2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(cardController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"cardNumber\":\"42\",\"cardHolderName\":\"Card Holder Name\",\"status\":\"Status\",\"spendingLimit"
                                        + "\":10.0,\"expiryDate\":\"2020-03-01\"},\"message\":\"Card issued successfully\",\"status\":201}"));
    }

    /**
     * Method under test: {@link CardController#getAllCards()}
     */
    @Test
    void testGetAllCards() throws Exception {
        // Arrange
        when(cardService.getAllCards()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"responseObj\":[],\"message\":\"Retrieved all cards\",\"status\":200}"));
    }

    /**
     * Method under test: {@link CardController#getCardById(String)}
     */
    @Test
    void testGetCardById() throws Exception {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        when(cardService.getCardById(Mockito.<String>any())).thenReturn(card);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/{cardNumber}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"cardNumber\":\"42\",\"cardHolderName\":\"Card Holder Name\",\"status\":\"Status\",\"spendingLimit"
                                        + "\":10.0,\"expiryDate\":\"2020-03-01\"},\"message\":\"Retrieved all cards\",\"status\":200}"));
    }

    /**
     * Method under test: {@link CardController#getCardById(String)}
     */
    @Test
    void testGetCardByIdForNonExistingCard() throws Exception {
        // Arrange
        when(cardService.getAllCards()).thenReturn(new ArrayList<>());
        when(cardService.getCardById(Mockito.<String>any())).thenThrow(new CardNotFoundException("Retrieved all cards"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/{cardNumber}", "",
                "Uri Variables");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"responseObj\":[],\"message\":\"Retrieved all cards\",\"status\":200}"));
    }

    /**
     * Method under test: {@link CardController#updateCard(String, Card)}
     */
    @Test
    void testUpdateCard() throws Exception {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        when(cardService.updateCard(Mockito.<String>any(), Mockito.<Card>any())).thenReturn(card);

        Card card2 = new Card();
        card2.setCardHolderName("Card Holder Name");
        card2.setCardNumber("42");
        card2.setExpiryDate("2020-03-01");
        card2.setSpendingLimit(10.0d);
        card2.setStatus("Status");
        String content = (new ObjectMapper()).writeValueAsString(card2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/cards/{cardNumber}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"cardNumber\":\"42\",\"cardHolderName\":\"Card Holder Name\",\"status\":\"Status\",\"spendingLimit"
                                        + "\":10.0,\"expiryDate\":\"2020-03-01\"},\"message\":\"Retrieved all cards\",\"status\":200}"));
    }

    /**
     * Method under test: {@link CardController#deleteCard(String)}
     */
    @Test
    void testDeleteCard() throws Exception {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        when(cardService.deleteCard(Mockito.<String>any())).thenReturn(card);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/cards/{cardNumber}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"cardNumber\":\"42\",\"cardHolderName\":\"Card Holder Name\",\"status\":\"Status\",\"spendingLimit"
                                        + "\":10.0,\"expiryDate\":\"2020-03-01\"},\"message\":\"Retrieved all cards\",\"status\":200}"));
    }

    /**
     * Method under test: {@link CardController#activateCard(String)}
     */
    @Test
    void testActivateCard() throws Exception {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        when(cardService.activateCard(Mockito.<String>any())).thenReturn(card);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/cards/{cardNumber}/activate", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"cardNumber\":\"42\",\"cardHolderName\":\"Card Holder Name\",\"status\":\"Status\",\"spendingLimit"
                                        + "\":10.0,\"expiryDate\":\"2020-03-01\"},\"message\":\"Retrieved all cards\",\"status\":200}"));
    }

    /**
     * Method under test: {@link CardController#deactivateCard(String)}
     */
    @Test
    void testDeactivateCard() throws Exception {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        when(cardService.deactivateCard(Mockito.<String>any())).thenReturn(card);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/cards/{cardNumber}/deactivate", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"cardNumber\":\"42\",\"cardHolderName\":\"Card Holder Name\",\"status\":\"Status\",\"spendingLimit"
                                        + "\":10.0,\"expiryDate\":\"2020-03-01\"},\"message\":\"Retrieved all cards\",\"status\":200}"));
    }

    /**
     * Method under test: {@link CardController#holdCard(String)}
     */
    @Test
    void testHoldCard() throws Exception {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        when(cardService.holdCard(Mockito.<String>any())).thenReturn(card);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/cards/{cardNumber}/hold", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"cardNumber\":\"42\",\"cardHolderName\":\"Card Holder Name\",\"status\":\"Status\",\"spendingLimit"
                                        + "\":10.0,\"expiryDate\":\"2020-03-01\"},\"message\":\"Retrieved all cards\",\"status\":200}"));
    }
}
