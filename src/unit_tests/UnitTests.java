package unit_tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import game.Basket;
import game.Egg;
import game.EggSpawner;

public class UnitTests {
	
	/*
	 * Alan Mc Gillivray, Unit Tests
	 */
	
	@Test						// Asserts x position moves left by basket speed (4)
	void testMoveBasketLeft() {
		Basket basket = new Basket("", 10, 10, 10, 10, "basket_01.png");
		System.out.println(basket.getX());
		basket.moveLeft();
		System.out.println(basket.getX());
		assertTrue(basket.getX() == 6);
	}
	
	@Test						// Asserts x position moves right by basket speed (4)
	void testMoveBasketRight() {
		Basket basket = new Basket("", 10, 10, 10, 10, "basket_01.png");
		System.out.println(basket.getX());
		basket.moveRight();
		System.out.println(basket.getX());
		assertTrue(basket.getX() == 14);
	}
	
	@Test						// Asserts basket image is not null after constructed
	void testBasketImageFilePath() {
		Basket basket = new Basket("", 10, 10, 10, 10, "basket_01.png");
		assertTrue(basket.getImage() != null);
	}
	
	@Test						// Assert new egg is added to eggList after spawnEgg executes
	void testEggSpawning() {
		EggSpawner e = new EggSpawner();
		while(e.getEggList().isEmpty()) {
			e.spawnEgg();
		}
		assertTrue(!e.getEggList().isEmpty());
	}
	
	@Test						// Assert eggSpawner.spawnRate increments by 1
	void testSetEggSpawnRate() {
		EggSpawner e = new EggSpawner();
		e.setSpawnRate(e.getSpawnRate() + 1);
		assertTrue(e.getSpawnRate() == 6);
		
	}
	
	/*
	 * Aishat Aminu, Unit Tests
	 */
	@Test
	void testInitialLivesAreThree() {//Verifies that the game starts with 3 lives by default

	}
	@Test
	void testGameOverFlagAfterLivesZero() {//Ensures that the game sets 'game over' to true when lives reach

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
