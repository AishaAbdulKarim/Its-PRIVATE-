package unit_tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import game.Basket;

public class UnitTests {
	
	/*
	 * Alan Mc Gillivray, Unit Tests
	 */
	
	@Test
	void testMoveBasketLeft() {
		Basket basket = new Basket("", 10, 10, 10, 10, "basket_01.png");
		System.out.println(basket.getX());
		basket.moveLeft();
		System.out.println(basket.getX());
		assertTrue(basket.getX() == 6);
	}
	
	@Test
	void testMoveBasketRight() {
		Basket basket = new Basket("", 10, 10, 10, 10, "basket_01.png");
		System.out.println(basket.getX());
		basket.moveRight();
		System.out.println(basket.getX());
		assertTrue(basket.getX() == 14);
	}
	
	@Test
	void testBasketImageFilePath() {
		Basket basket = new Basket("", 10, 10, 10, 10, "basket_01.png");
		assertTrue(basket.getImage() != null);
	}
	
	/*
	 * Aishat Aminu, Unit Tests
	 */
	
	@Test
	void testTwo() {
		
	}
	
	/*
	 * Aisha Abdul Karim, Unit Tests
	 */
	
	@Test
	void testThree() {
		
	}
	
	/*
	 * Joanne Thomas, Unit Tests
	 */
	
	@Test
	void tesfouro() {
		
	}
}
