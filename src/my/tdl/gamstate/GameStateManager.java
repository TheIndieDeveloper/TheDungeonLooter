package my.tdl.gamstate;

import java.awt.Graphics2D;
import java.util.Stack;

import my.tdl.gamstates.DungeonLevelLoader;
import my.tdl.gamstates.MenuState;

public class GameStateManager {

	public static Stack<GameState> states;
	
	public GameStateManager() {
		states = new Stack<GameState>();
		states.push(new MenuState(this));
	}
	
	public void tick(double deltaTime){
		states.peek().tick(deltaTime);
	}
	
	public void render(Graphics2D g){
		states.peek().render(g);
	}

	public void init() {
		states.peek().init();
	}
	

}
