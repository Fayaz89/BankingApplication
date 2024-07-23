package com.pack.card.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pack.card.exception.CardAlreadyExistsException;
import com.pack.card.exception.CardNotFoundException;
import com.pack.card.model.Card;
import com.pack.card.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CardService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CardServiceDiffblueTest {
    @MockBean
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    /**
     * Method under test: {@link CardService#issueCard(Card)}
     */
    @Test
    void testIssueCard() throws CardAlreadyExistsException {
        // Arrange
        when(cardRepository.existsById(Mockito.<String>any())).thenReturn(true);

        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");

        // Act and Assert
        assertThrows(CardAlreadyExistsException.class, () -> cardService.issueCard(card));
        verify(cardRepository).existsById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#issueCard(Card)}
     */
    @Test
    void testIssueCard2() throws CardAlreadyExistsException {
        // Arrange
        when(cardRepository.existsById(Mockito.<String>any())).thenReturn(false);

        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cardService.issueCard(card));
        verify(cardRepository).existsById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#issueCard(Card)}
     */
    @Test
    void testIssueCard3() throws CardAlreadyExistsException {
        // Arrange
        when(cardRepository.existsById(Mockito.<String>any()))
                .thenThrow(new IllegalArgumentException("Invalid date format. Please use MM/yyyy."));

        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cardService.issueCard(card));
        verify(cardRepository).existsById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#getAllCards()}
     */
    @Test
    void testGetAllCards() {
        // Arrange
        ArrayList<Card> cardList = new ArrayList<>();
        when(cardRepository.findAll()).thenReturn(cardList);

        // Act
        List<Card> actualAllCards = cardService.getAllCards();

        // Assert
        verify(cardRepository).findAll();
        assertTrue(actualAllCards.isEmpty());
        assertSame(cardList, actualAllCards);
    }

    /**
     * Method under test: {@link CardService#getAllCards()}
     */
    @Test
    void testGetAllCards2() {
        // Arrange
        when(cardRepository.findAll()).thenThrow(new IllegalArgumentException("foo"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cardService.getAllCards());
        verify(cardRepository).findAll();
    }

    /**
     * Method under test: {@link CardService#getCardById(String)}
     */
    @Test
    void testGetCardById() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Card actualCardById = cardService.getCardById("42");

        // Assert
        verify(cardRepository).findById(eq("42"));
        assertSame(card, actualCardById);
    }

    /**
     * Method under test: {@link CardService#getCardById(String)}
     */
    @Test
    void testGetCardById2() throws CardNotFoundException {
        // Arrange
        Optional<Card> emptyResult = Optional.empty();
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(CardNotFoundException.class, () -> cardService.getCardById("42"));
        verify(cardRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#getCardById(String)}
     */
    @Test
    void testGetCardById3() throws CardNotFoundException {
        // Arrange
        when(cardRepository.findById(Mockito.<String>any())).thenThrow(new IllegalArgumentException("Card not found"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cardService.getCardById("42"));
        verify(cardRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#updateCard(String, Card)}
     */
    @Test
    void testUpdateCard() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Card cardDetails = new Card();
        cardDetails.setCardHolderName("Card Holder Name");
        cardDetails.setCardNumber("42");
        cardDetails.setExpiryDate("2020-03-01");
        cardDetails.setSpendingLimit(10.0d);
        cardDetails.setStatus("Status");

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCard("42", cardDetails));
        verify(cardRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#updateCard(String, Card)}
     */
    @Test
    void testUpdateCard2() throws CardNotFoundException {
        // Arrange
        Optional<Card> emptyResult = Optional.empty();
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        Card cardDetails = new Card();
        cardDetails.setCardHolderName("Card Holder Name");
        cardDetails.setCardNumber("42");
        cardDetails.setExpiryDate("2020-03-01");
        cardDetails.setSpendingLimit(10.0d);
        cardDetails.setStatus("Status");

        // Act and Assert
        assertThrows(CardNotFoundException.class, () -> cardService.updateCard("42", cardDetails));
        verify(cardRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#deleteCard(String)}
     */
    @Test
    void testDeleteCard() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);
        doNothing().when(cardRepository).delete(Mockito.<Card>any());
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Card actualDeleteCardResult = cardService.deleteCard("42");

        // Assert
        verify(cardRepository).delete(isA(Card.class));
        verify(cardRepository).findById(eq("42"));
        assertSame(card, actualDeleteCardResult);
    }

    /**
     * Method under test: {@link CardService#deleteCard(String)}
     */
    @Test
    void testDeleteCard2() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);
        doThrow(new IllegalArgumentException("foo")).when(cardRepository).delete(Mockito.<Card>any());
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cardService.deleteCard("42"));
        verify(cardRepository).delete(isA(Card.class));
        verify(cardRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#deleteCard(String)}
     */
    @Test
    void testDeleteCard3() throws CardNotFoundException {
        // Arrange
        Optional<Card> emptyResult = Optional.empty();
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(CardNotFoundException.class, () -> cardService.deleteCard("42"));
        verify(cardRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#activateCard(String)}
     */
    @Test
    void testActivateCard() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);

        Card card2 = new Card();
        card2.setCardHolderName("Card Holder Name");
        card2.setCardNumber("42");
        card2.setExpiryDate("2020-03-01");
        card2.setSpendingLimit(10.0d);
        card2.setStatus("Status");
        when(cardRepository.save(Mockito.<Card>any())).thenReturn(card2);
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Card actualActivateCardResult = cardService.activateCard("42");

        // Assert
        verify(cardRepository).findById(eq("42"));
        verify(cardRepository).save(isA(Card.class));
        assertSame(card2, actualActivateCardResult);
    }

    /**
     * Method under test: {@link CardService#activateCard(String)}
     */
    @Test
    void testActivateCard2() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);
        when(cardRepository.save(Mockito.<Card>any())).thenThrow(new IllegalArgumentException("ACTIVE"));
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cardService.activateCard("42"));
        verify(cardRepository).findById(eq("42"));
        verify(cardRepository).save(isA(Card.class));
    }

    /**
     * Method under test: {@link CardService#activateCard(String)}
     */
    @Test
    void testActivateCard3() throws CardNotFoundException {
        // Arrange
        Optional<Card> emptyResult = Optional.empty();
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(CardNotFoundException.class, () -> cardService.activateCard("42"));
        verify(cardRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#deactivateCard(String)}
     */
    @Test
    void testDeactivateCard() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);

        Card card2 = new Card();
        card2.setCardHolderName("Card Holder Name");
        card2.setCardNumber("42");
        card2.setExpiryDate("2020-03-01");
        card2.setSpendingLimit(10.0d);
        card2.setStatus("Status");
        when(cardRepository.save(Mockito.<Card>any())).thenReturn(card2);
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Card actualDeactivateCardResult = cardService.deactivateCard("42");

        // Assert
        verify(cardRepository).findById(eq("42"));
        verify(cardRepository).save(isA(Card.class));
        assertSame(card2, actualDeactivateCardResult);
    }

    /**
     * Method under test: {@link CardService#deactivateCard(String)}
     */
    @Test
    void testDeactivateCard2() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);
        when(cardRepository.save(Mockito.<Card>any())).thenThrow(new IllegalArgumentException("INACTIVE"));
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cardService.deactivateCard("42"));
        verify(cardRepository).findById(eq("42"));
        verify(cardRepository).save(isA(Card.class));
    }

    /**
     * Method under test: {@link CardService#deactivateCard(String)}
     */
    @Test
    void testDeactivateCard3() throws CardNotFoundException {
        // Arrange
        Optional<Card> emptyResult = Optional.empty();
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(CardNotFoundException.class, () -> cardService.deactivateCard("42"));
        verify(cardRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link CardService#holdCard(String)}
     */
    @Test
    void testHoldCard() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);

        Card card2 = new Card();
        card2.setCardHolderName("Card Holder Name");
        card2.setCardNumber("42");
        card2.setExpiryDate("2020-03-01");
        card2.setSpendingLimit(10.0d);
        card2.setStatus("Status");
        when(cardRepository.save(Mockito.<Card>any())).thenReturn(card2);
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Card actualHoldCardResult = cardService.holdCard("42");

        // Assert
        verify(cardRepository).findById(eq("42"));
        verify(cardRepository).save(isA(Card.class));
        assertSame(card2, actualHoldCardResult);
    }

    /**
     * Method under test: {@link CardService#holdCard(String)}
     */
    @Test
    void testHoldCard2() throws CardNotFoundException {
        // Arrange
        Card card = new Card();
        card.setCardHolderName("Card Holder Name");
        card.setCardNumber("42");
        card.setExpiryDate("2020-03-01");
        card.setSpendingLimit(10.0d);
        card.setStatus("Status");
        Optional<Card> ofResult = Optional.of(card);
        when(cardRepository.save(Mockito.<Card>any())).thenThrow(new IllegalArgumentException("ON_HOLD"));
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cardService.holdCard("42"));
        verify(cardRepository).findById(eq("42"));
        verify(cardRepository).save(isA(Card.class));
    }

    /**
     * Method under test: {@link CardService#holdCard(String)}
     */
    @Test
    void testHoldCard3() throws CardNotFoundException {
        // Arrange
        Optional<Card> emptyResult = Optional.empty();
        when(cardRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(CardNotFoundException.class, () -> cardService.holdCard("42"));
        verify(cardRepository).findById(eq("42"));
    }
}
