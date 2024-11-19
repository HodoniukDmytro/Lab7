package sample;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ShoppingCart class.
 * Author: Dmytro
 */
public class ShoppingCartTest {
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    void shouldThrowExceptionWhenAddingItemWithEmptyTitle() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                cart.addItem("", 0.99, 5, ShoppingCart.ItemType.NEW));
        assertEquals("Illegal title", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAddingItemWithZeroPrice() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                cart.addItem("Apple", 0.00, 5, ShoppingCart.ItemType.NEW));
        assertEquals("Illegal price", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAddingItemWithZeroQuantity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                cart.addItem("Apple", 0.99, 0, ShoppingCart.ItemType.NEW));
        assertEquals("Illegal quantity", exception.getMessage());
    }

    @Test
    void shouldReturnNoItemsMessageWhenCartIsEmpty() {
        assertEquals("No items.", cart.formatTicket());
    }

    @Test
    void shouldApplySecondFreeDiscountCorrectly() {
        cart.addItem("Banana", 20.00, 4, ShoppingCart.ItemType.SECOND_FREE);
        String ticket = cart.formatTicket();
        assertAll(
                () -> assertTrue(ticket.contains("50%")),
                () -> assertTrue(ticket.contains("$40.00"))
        );
    }

    @Test
    void shouldApplySaleDiscountCorrectly() {
        cart.addItem("Toilet Paper", 10.00, 2, ShoppingCart.ItemType.SALE);
        String ticket = cart.formatTicket();
        assertAll(
                () -> assertTrue(ticket.contains("70%")),
                () -> assertTrue(ticket.contains("$6.00"))
        );
    }

    @Test
    void shouldApplySmallQuantityDiscountToRegularItem() {
        cart.addItem("Nails", 2.00, 20, ShoppingCart.ItemType.REGULAR);
        String ticket = cart.formatTicket();
        assertAll(
                () -> assertTrue(ticket.contains("2%")),
                () -> assertTrue(ticket.contains("$39.20"))
        );
    }

    @Test
    void shouldApplyMaxDiscountForLargeQuantityOfRegularItem() {
        cart.addItem("Nails", 2.00, 1000, ShoppingCart.ItemType.REGULAR);
        String ticket = cart.formatTicket();
        assertAll(
                () -> assertTrue(ticket.contains("80%")),
                () -> assertTrue(ticket.contains("$400.00"))
        );
    }
}