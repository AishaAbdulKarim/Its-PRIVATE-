package unit_tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import game.Basket;

public class UnitTests {
	
	@Test
	void test() {
		Basket basket = new Basket("", 0, 0, 0, 0, "basket_01.png");
		System.out.println(basket.getX());
		basket.moveLeft();
		System.out.println(basket.getX());
		assertTrue(basket.getX() == 0);
	}
}
