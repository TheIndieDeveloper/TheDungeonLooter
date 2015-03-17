package my.tdl.gamstates;

import java.awt.Graphics2D;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;
import my.tdl.gamstate.GameState;
import my.tdl.gamstate.GameStateButton;
import my.tdl.gamstate.GameStateManager;
import my.tdl.main.Main;
import my.tdl.managers.Mousemanager;

public class MenuState extends GameState{

	GameStateButton startGame;
	GameStateButton multiplayer;
	GameStateButton options;
	GameStateButton quit;
	Mousemanager mm;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		mm = new Mousemanager();
		startGame = new GameStateButton(Main.width / 3, 200, new DungeonLevelLoader(gsm), gsm, "StartGame");
		multiplayer = new GameStateButton(Main.width / 3, 300, new DungeonLevelLoader(gsm), gsm, "Multiplayer");
		options = new GameStateButton(Main.width / 3, 400, new DungeonLevelLoader(gsm), gsm, "Options");
		quit = new GameStateButton(Main.width / 3, 500, new QuitState(gsm), gsm, "Quit");
	}

	@Override
	public void tick(double deltaTime) {
		mm.tick();
		startGame.tick();
		multiplayer.tick();
		options.tick();
		quit.tick();
	}

	@Override
	public void render(Graphics2D g) {
		startGame.render(g);
		multiplayer.render(g);
		options.render(g);
		quit.render(g);
		
		mm.render(g);
		g.clipRect(0, 0, Main.width, Main.height);
	}

}
