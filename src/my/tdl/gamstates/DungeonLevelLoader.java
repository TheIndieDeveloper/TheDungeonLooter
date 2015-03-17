package my.tdl.gamstates;

import java.awt.Graphics2D;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import my.tdl.MoveableObjects.Player;
import my.tdl.gamstate.GameState;
import my.tdl.gamstate.GameStateManager;
import my.tdl.generator.World;
import my.tdl.main.Main;

public class DungeonLevelLoader extends GameState{
	
	World world;
	
	public DungeonLevelLoader(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		world = new World("world");
		world.setSize(100,100);
		world.setWorldSpawn(10, 10);
		world.addPlayer(new Player());
		
		world.init();
		world.generate("map");
	}

	@Override
	public void tick(double deltaTime) {
		if(world.hasGenerated()){
			world.tick(deltaTime);
		}
	}

	@Override
	public void render(Graphics2D g) {
		if(world.hasGenerated()){
			world.render(g);
		}
		g.clipRect(0, 0, Main.width, Main.height);
	}

}
