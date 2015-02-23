package my.tdl.gamstate;

import java.awt.Graphics2D;

public abstract class GameState {

	public GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public abstract void init();
	public abstract void tick(double deltaTime);
	public abstract void render(Graphics2D g);


	
}
