package unit_tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import game.Basket;

public class UnitTests {
	
	@Test
	void testMoveBasketLeft() {
		Basket basket = new Basket("", 10, 10, 10, 10, "basket_01.png");
		System.out.println(basket.getX());
		basket.moveLeft();
		System.out.println(basket.getX());
		assertTrue(basket.getX() == 6);
	}
}
