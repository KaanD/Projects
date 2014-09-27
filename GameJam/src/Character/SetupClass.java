package Character;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SetupClass extends StateBasedGame{

	public static int gameScore = 0;
	public static int lives = 5;
	public static Music song;
	public static float songPosition = 0;
	
	public SetupClass() {
		super("");
	}
	
	public SetupClass(String title) {
		super(title);
	}
	
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SetupClass("Binary"));
		app.setDisplayMode(800, 800, false);
		//app.setAlwaysRender(true);
		app.setTargetFrameRate(60);
		app.setVSync(true);
		app.start();
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
/*		this.addState(new Menu());
		this.addState(new Stage1());
		this.addState(new Stage2());
		this.addState(new GameOverState());
		this.addState(new WonGameState());*/
	}


	public static int getGameScore() {
		return gameScore;
	}


	public static void setGameScore(int gameScore) {
		SetupClass.gameScore = gameScore;
	}


	public static int getLives() {
		return lives;
	}


	public static void setLives(int lives) {
		SetupClass.lives = lives;
	}


	
}
