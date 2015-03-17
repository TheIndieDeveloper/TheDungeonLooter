package my.tdl.managers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import my.project.gop.main.Light;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import my.tdl.MoveableObjects.Player;
import my.tdl.generator.World;
import my.tdl.main.Main;

public class HUDmanager {

	private BufferedImage lightmap = new BufferedImage(100*32, 100*32, BufferedImage.TYPE_INT_ARGB);
	private ArrayList<Light> lights = new ArrayList<Light>();
	private Vector2F lightm = new Vector2F();
	private boolean callOnce = false;
	private World world;

	public HUDmanager(World world) {
		this.world = world;
		
		playerlight = loadImageFrom.LoadImageFrom(Main.class, "Light.png");
		addLights();
	}

	private void addLights() {
		lights.add(new Light(200, 200, 920, 255));
		lights.add(new Light(400, 400, 220, 255));
		lights.add(new Light(350, 300, 220, 155));
		UpdateLights();
	}
	
	
	BufferedImage playerlight;
	
	public void UpdateLights(){
		Graphics2D g = null;
		if(g == null){
			g = (Graphics2D) lightmap.getGraphics();
		}

		g.setColor(new Color(0, 0, 0, 255));
		g.fillRect(0, 0, lightmap.getWidth(), lightmap.getHeight());
		g.setComposite(AlphaComposite.DstOut);
		
		for(Light light : lights){
			light.render(g);
		}
		g.dispose();
	}
	

	public void render(Graphics2D g){
		
		g.drawImage(lightmap, (int)lightm.getWorldLocation().xpos, (int)lightm.getWorldLocation().ypos, null);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.width, Main.height / 6);
		g.fillRect(0, 750, Main.width, Main.height / 6);
		g.setColor(Color.WHITE);
		
		if(world.getPlayer().isDebuging()){
			g.drawString("[DEBUG]", 30, 30);
			g.drawString("[MapXpos] "+world.getWorldXpos(), 30, 60);
			g.drawString("[MapYpos] "+world.getWorldYpos(), 30, 90);
			g.drawString("[PlayerXpos] "+world.getPlayer().getPos().xpos, 30, 120);
			g.drawString("[PlayerYpos] "+world.getPlayer().getPos().ypos, 30, 150);
			g.drawString("[Current World Blocks] "+world.getWorldBlocks().getBlocks().size(), 30, 180);
			g.drawString("[Current Loaded World Blocks] "+world.getWorldBlocks().getLoadedBlocks().size(), 30, 210);
		}
		
		//g.drawImage(playerlight, 0, 0, Main.width,Main.height,null);
		
	}
	
}
