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
import my.tdl.main.Main;

public class HUDmanager {

	private Player player;
	
	private BufferedImage lightmap = new BufferedImage(100*32, 100*32, BufferedImage.TYPE_INT_ARGB);
	private ArrayList<Light> lights = new ArrayList<Light>();
	private Vector2F lightm = new Vector2F();
	

	public HUDmanager(Player player) {
		this.player = player;
		playerlight = loadImageFrom.LoadImageFrom(Main.class, "light.png");
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
		
		//g.drawImage(playerlight, 0, 0, Main.width,Main.height,null);
		
	}
	
}
