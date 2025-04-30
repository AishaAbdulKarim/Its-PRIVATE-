package unit_tests;

import org.junit.jupiter.api.Test;

import game.Basket;
import game.Egg;
import game.EggSpawner;
import game.SPGameManager;
import game.Sound;
import gameConstants.Constants;
import game.GameManager;
import game.GamePanel;
import game.Init;
import game.MainMenu;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(4, basket.getX());
	}

	@Test						// Asserts x position moves right by basket speed (4)
	void testMoveBasketRight() {
		Basket basket = new Basket("", 10, 10, 10, 10, "basket_01.png");
		System.out.println(basket.getX());
		basket.moveRight();
		System.out.println(basket.getX());
        assertEquals(16, basket.getX());
	}

	@Test						// Asserts basket image is not null after constructed
	void testBasketImageFilePath() {
		Basket basket = new Basket("", 10, 10, 10, 10, "basket_01.png");
        assertNotNull(basket.getImage());
	}

	@Test						// Assert new egg is added to eggList after spawnEgg executes
	void testEggSpawning() {
		EggSpawner e = new EggSpawner();
		while(e.getEggList().isEmpty()) {
			e.spawnEgg();
		}
        assertFalse(e.getEggList().isEmpty());
	}

	@Test						// Assert eggSpawner.spawnRate increments by 1
	void testSetEggSpawnRate() {
		EggSpawner e = new EggSpawner();
		e.setSpawnRate(e.getSpawnRate() + 1);
        assertEquals(6, e.getSpawnRate());
	}
	
	@Test
	void testGetAudioMusicNotNull() {
		Sound mainMusic;
		mainMusic = new Sound("musicOne.wav");
		assertTrue(mainMusic.musicFileExists());
	}
	
	@Test
	void testGetAudioEggCatchNotNull() {
		Sound mainMusic;
		mainMusic = new Sound("one.wav");
		assertTrue(mainMusic.musicFileExists());
	}




		/*
		 * Aishat Aminu, Unit Tests
		 */
		@Test
		void testInitialLivesAreThree() {//Verifies that the game starts with 3 lives by default
			SPGameManager manager = new SPGameManager(null);                // Create new game instance
			assertEquals(3, manager.getLives(),                         // Check that initial lives = 3
					"Initial lives should be 3 at game start.");
		}

		@Test
		void testScoreDoesNotIncreaseWhenNoCollision() {// Confirms that no score is awarded when the egg doesn't collide with the basket
			SPGameManager manager = new SPGameManager(null);                // New game instance
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
			SPGameManager manager = new SPGameManager(null);                // Game instance

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
	@Test
	void testGameOverFlagAfterLivesZero() throws Exception { //  Added 'throws Exception' to allow reflection
		SPGameManager manager = new SPGameManager(null); // Creates a new single-player game instance

		manager.setLives(0); // Force lives to 0 so update() should trigger game over

		//  the internal countdown which normally delays game logic
		Field countdownField = SPGameManager.class.getDeclaredField("countdownFinished");
		countdownField.setAccessible(true);
		countdownField.setBoolean(manager, true);

		manager.update(); // Triggers the logic that checks if the game should end

		// Check if gameOver flag was set to true
		assertTrue(manager.getIsGameOver(), "Game should be over when lives reach 0.");
	}
	@Test
	void testEggCaughtIncreasesScoreAndRemovesEgg() throws Exception {
		// single-player game manager instance
		SPGameManager manager = new SPGameManager(null);

		Basket basket = manager.getBasket();

		// Access the egg spawner so we can manually add an egg
		EggSpawner spawner = manager.getEggSpawner();

		//  Bypassed the game's countdown timer so update() actually processes game logic
		Field countdownField = SPGameManager.class.getDeclaredField("countdownFinished");
		countdownField.setAccessible(true);
		countdownField.setBoolean(manager, true);

		//  Created an egg positioned directly over the basket to force a collision
		Egg egg = new Egg(basket.getX(), basket.getY(), 40, 50, "egg_01.png");
		spawner.getEggList().add(egg); // Add the egg to the game manually

		// Run one update cycle should detect the egg-basket collision
		manager.update();

		//  Asserts that score increased by 10 after catching the egg
		assertEquals(10, manager.getScore(), "Score should increase by 10 on egg catch.");

		//  Asserts that the egg is removed from the egg list after being caught
		assertEquals(0, spawner.getEggList().size(), "Egg should be removed after catch.");
	}
	@Test
	void testEggMissedDecreasesLivesAndRemovesEgg() throws Exception {
		//  single-player game manager instance
		SPGameManager manager = new SPGameManager(null);
		EggSpawner spawner = manager.getEggSpawner(); // Access egg spawner to manually add eggs

		// Bypass the internal countdown so update() logic executes immediately
		Field countdownField = SPGameManager.class.getDeclaredField("countdownFinished");
		countdownField.setAccessible(true);
		countdownField.setBoolean(manager, true);

		//  Added an egg positioned below the screen to simulate a missed catch
		Egg egg = new Egg(0, Constants.FRAME_HEIGHT + 20, 40, 50, "egg_01.png");
		spawner.getEggList().add(egg);

		int livesBefore = manager.getLives(); // Record lives before update

		manager.update(); // Triggered game logic

		//  Checked that a life was lost due to the missed egg
		assertEquals(livesBefore - 1, manager.getLives(), "Lives should decrease when egg is missed.");

		// Confirm the egg is removed from the game
		assertTrue(spawner.getEggList().isEmpty(), "Missed egg should be removed.");
	}
	@Test
	public void testInitialSpawnRateSPMode() {
		SPGameManager spGameManager = new SPGameManager("Test Player");
		assertEquals(5, spGameManager.getEggSpawner().getSpawnRate(),
				"Initial spawn rate should be 5");
	}
	@Test
	public void testSpawnRateIncreasesAfterScoreCheckpoint_SPGameManager()  {

	}


	//	/*
	//	 * Aisha Abdul Karim, Unit Tests
	//	 */
	//
	//
	//	@Test
	//	void testStartPlayer2() {
	//		GameManager manager = new GameManager();
	//		manager.start(); // Start the game for Player 1
	//
	//		// Assert Player 1 is active initially
	//		assertEquals(1, manager.getCurrentPlayer());
	//
	//		// Simulate Player 1 losing all lives
	//		manager.update(); // Call update to handle losing lives
	//		manager.update();
	//		manager.update(); // After 3 updates, Player 1 should be out of lives
	//
	//		// Call startPlayer2 to switch to Player 2
	//		manager.startPlayer2();
	//
	//		// Assert Player 2 is now active
	//		assertEquals(2, manager.getCurrentPlayer());
	//
	//		// Assert the score and lives have been reset for Player 2
	//		assertEquals(0, manager.getScore());
	//		assertFalse(manager.isGameOver()); // Game over should be false for Player 2
	//	}
	//
	//	@Test
	//	void testWaitingForPlayer2() {
	//		GameManager manager = new GameManager();
	//		manager.start();
	//
	//		// Simulate Player 1 losing all lives
	//		manager.update();
	//		manager.update();
	//		manager.update();
	//
	//	}
	//
	//	@Test
	//	void testStartPlayer2SwitchesTurn() {
	//		GameManager manager = new GameManager();
	//		manager.start();
	//
	//		manager.startPlayer2(); // Simulate switch to Player 2
	//
	//		// Assert that Player 2 is now active
	//		assertEquals(2, manager.getCurrentPlayer()); // Should now be Player 2
	//		assertEquals(0, manager.getScore()); // Score should reset when Player 2 starts
	//		assertFalse(manager.isGameOver()); // Game should not be over after switching to Player 2
	//		assertFalse(manager.isWaitingForPlayer2()); // No longer waiting for Player 2
	//	}
	//
	//	@Test
	//	void testGameManagerDrawsCorrectPlayer() {
	//		GameManager manager = new GameManager();
	//		manager.start();
	//
	//		// Assert that Player 1 is initially active
	//		assertEquals(1, manager.getCurrentPlayer());
	//
	//		manager.startPlayer2(); // Simulate switching to Player 2
	//
	//		// Assert that Player 2 is now active
	//		assertEquals(2, manager.getCurrentPlayer()); // Player 2 should now be active
	//	}
	//
	//	@Test
	//	void testGameOverForPlayer2() {
	//		GameManager manager = new GameManager();
	//		manager.start();
	//
	//		// Simulate Player 1 losing all lives
	//		manager.update();
	//		manager.update();
	//		manager.update();
	//
	//		// Start Player 2
	//		manager.startPlayer2();
	//
	//		// Simulate Player 2 losing all lives
	//		manager.update();
	//		manager.update();
	//		manager.update();
	//
	//		// Assert game over state for Player 2
	//
	//		assertEquals(2, manager.getCurrentPlayer()); // Player 2 should be the active player
	//	}
	//
	//	@Test
	//	public void testButtonActions() {
	//		GamePanel panel = new GamePanel(null);
	//		panel.restartGameButton.doClick(); // Simulate a click on the restart button
	//
	//		// Assert that the restart button is hidden after click
	//		assertFalse(panel.restartGameButton.isVisible());
	//	}
	//
	//	@Test
	//	public void testScoreResetForPlayer2() {
	//		GameManager gameManager = new GameManager();
	//		gameManager.start();
	//
	//		// Simulate Player 1 scoring points
	//		gameManager.update();
	//		gameManager.update();
	//		int player1Score = gameManager.getScore();  // Save Player 1's score
	//		gameManager.update();  // Simulate some game updates
	//
	//		// Simulate Player 1 finishing and Player 2 starting
	//		gameManager.startPlayer2(); // Start Player 2's turn
	//	}
	//
	//
	//
	//
	//	/*
	//	 * Joanne Thomas, Unit Tests
	//	 */
	//
	//	@Test
	//	void testEggCaughtIncreasesScore() {
	//		SPGameManager game = new SPGameManager(null);
	//		Basket basket = game.getBasket();
	//
	//		Egg egg = new Egg(basket.getX(), basket.getY(), basket.getWidth(), basket.getHeight(), "egg_01.png");
	//		game.getEggSpawner().getEggList().clear();
	//		game.getEggSpawner().getEggList().add(egg);
	//
	//		int oldScore = game.getScore();
	//		game.update(); // triggers checkCollisions()
	//		assertTrue(game.getScore() > oldScore);
	//	}
	//	@Test
	//	void testEggMissedDoesNotIncreaseScore() {
	//		SPGameManager game = new SPGameManager(null);
	//		game.getEggSpawner().getEggList().clear();
	//
	//		Egg egg = new Egg(0, Constants.FRAME_HEIGHT + 100, 40, 50, "egg_01.png");
	//		game.getEggSpawner().getEggList().add(egg);
	//
	//		int scoreBefore = game.getScore();
	//		game.update(); // egg should be removed
	//		assertEquals(scoreBefore, game.getScore());
	//	}
	//
	//
	//	@Test
	//	void testSetAndGetHighScore() {
	//		Init window = new Init();
	//		window.setHighScore(50);
	//		assertEquals(50, window.getHighScore());
	//	}
	//
	//	@Test
	//	void testReturnToMainMenuReplacesPanel() {
	//		Init window = new Init();
	//		window.showSPGame(); // switch to game panel
	//		window.returnToMainMenu(); // switch back
	//	}
	//
	//
	//	@Test
	//	void testHighscoresButtonExists() {
	//		MainMenu menu = new MainMenu(new Init());
	//
	//		JButton highscoreButton = findButton(menu, "Highscores");
	//		assertNotNull(highscoreButton, "'Highscores' button should exist");
	//	}
	//
	//	private JButton findButton(MainMenu menu, String text) {
	//		for (Component comp : menu.getComponents()) {
	//			if (comp instanceof JButton btn && btn.getText().equals(text)) {
	//				return btn;
	//			}
	//		}
	//		return null;
	//	}

	}

