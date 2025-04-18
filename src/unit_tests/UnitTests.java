package unit_tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import game.Basket;
import game.Egg;
import game.EggSpawner;
import game.SPGameManager;
import gameConstants.Constants;
import game.GameManager;

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
		SPGameManager manager = new SPGameManager();                // Create new game instance
		assertEquals(3, manager.getLives(),                         // Check that initial lives = 3
				"Initial lives should be 3 at game start.");
	}
	@Test
	void testGameOverFlagAfterLivesZero() {//Ensures that the game sets 'game over' to true when lives reach 0
		SPGameManager manager = new SPGameManager();                // Create game instance
		manager.setLives(0);                                        // Force lives to 0
		manager.update();                                           // Call update to trigger game over logic
		assertTrue(manager.getIsGameOver(),                         // Check if game over flag is true
                "Game should be over when lives reach 0.");
	}

	@Test
	void testEggCaughtIncreasesScoreAndRemovesEgg() {//Confirms that score increases by 10 and egg is removed after catching it
		SPGameManager manager = new SPGameManager();                // Create game manager
		Basket basket = manager.getBasket();                        // Get basket reference
		EggSpawner spawner = manager.getEggSpawner();               // Get egg spawner reference

		// Create egg directly above basket to force collision
		Egg egg = new Egg(basket.getX(), basket.getY(), 40, 50, "egg.png");
		spawner.getEggList().add(egg);                              // Add egg to list

		manager.update();                                           // Update game to check collision
		assertEquals(10, manager.getScore(),                        // Score should be 10
				"Score should increase by 10 on egg catch.");
		assertEquals(0, spawner.getEggList().size(),                // Egg list should be empty
				"Egg should be removed after catch.");
	}
	@Test
	void testEggMissedDecreasesLivesAndRemovesEgg() {//Validates that a missed egg reduces lives and removes the egg from the list
		SPGameManager manager = new SPGameManager();                // Game instance
		EggSpawner spawner = manager.getEggSpawner();               // Access egg list

		// Create egg that's below the screen (missed)
		Egg egg = new Egg(0, Constants.FRAME_HEIGHT + 20, 40, 50, "egg.png");
		spawner.getEggList().add(egg);                              // Add missed egg

		int livesBefore = manager.getLives();                       // Store initial lives
		manager.update();                                           // Update game to process missed egg

		assertEquals(livesBefore - 1, manager.getLives(),           // Lives should decrease by 1
				"Lives should decrease when egg is missed.");
		assertTrue(spawner.getEggList().isEmpty(),                  // Egg should be removed
                "Missed egg should be removed.");
	}
	@Test
	void testScoreDoesNotIncreaseWhenNoCollision() {// Confirms that no score is awarded when the egg doesn't collide with the basket
		SPGameManager manager = new SPGameManager();                // New game instance
		Basket basket = manager.getBasket();                        // Get basket
		EggSpawner spawner = manager.getEggSpawner();               // Egg spawner

		// Place egg far from basket so no collision occurs
		Egg egg = new Egg(basket.getX() + 200, basket.getY() + 200, 40, 50, "egg.png");
		spawner.getEggList().add(egg);                              // Add egg

		manager.update();                                           // Update game, no collision expected

		assertEquals(0, manager.getScore(),                         // Score should remain unchanged
				"Score should not increase without collision.");
	}
	@Test
	void testGameResetAfterStart() {//Checks that calling start() resets lives, score, and game state properly
		SPGameManager manager = new SPGameManager();                // Game instance

		// Simulate mid-game state
		manager.setLives(1);
		manager.setScore(50);
		manager.setGameOver(true);

		manager.start();                                            // Reset game state

		assertEquals(3, manager.getLives(),                         // Lives should reset to 3
				"Lives should reset to 3.");
		assertEquals(0, manager.getScore(),                         // Score should reset to 0
				"Score should reset to 0.");
		assertFalse(manager.getIsGameOver(),                        // Game should not be over anymore
				"Game over should be reset to false.");
	}
	/*
	 * Aisha Abdul Karim, Unit Tests
	 */


	@Test
	void testStartPlayer2() {
		GameManager manager = new GameManager();
		manager.start(); // Start the game for Player 1

		// Assert Player 1 is active initially
		assertEquals(1, manager.getCurrentPlayer());

		// Simulate Player 1 losing all lives
		manager.update(); // Call update to handle losing lives
		manager.update();
		manager.update(); // After 3 updates, Player 1 should be out of lives

		// Call startPlayer2 to switch to Player 2
		manager.startPlayer2();

		// Assert Player 2 is now active
		assertEquals(2, manager.getCurrentPlayer());

		// Assert the score and lives have been reset for Player 2
		assertEquals(0, manager.getScore());
		assertFalse(manager.isGameOver()); // Game over should be false for Player 2
	}
	void testStartPlayer2SwitchesTurn() {
		GameManager manager = new GameManager();
		manager.start();

		manager.startPlayer2();                                     // Simulate switch

		assertEquals(2, manager.getCurrentPlayer());                // Should now be Player 2
		assertEquals(0, manager.getScore());                        // Score should reset
		assertFalse(manager.isGameOver());                          // Game should not be over
		assertFalse(manager.isWaitingForPlayer2());                 // No longer waiting
	}

	@Test
	void testGameManagerDrawsCorrectPlayer() {
		GameManager manager = new GameManager();
		manager.start();

		assertEquals(1, manager.getCurrentPlayer());

		manager.startPlayer2();

		assertEquals(2, manager.getCurrentPlayer());                // Player 2 should now be active
	}




	/*
	 * Joanne Thomas, Unit Tests
	 */

	@Test
	void tesfouro() {

	}
}
