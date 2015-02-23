package my.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import my.tdl.MoveableObjects.Player;
import my.tdl.generator.Block.BlockType;
import my.tdl.main.Main;

public class Map {

	TileManager tiles = new TileManager();
	Player player = new Player();
	public static BufferedImage map = null;
	
	public Map() {
		
	}
	
	public void init(){
		player.init();
		
		try{
			map = loadImageFrom.LoadImageFrom(Main.class, "map.png");
		}catch(Exception e){
			
		}
		
		for(int x = 0; x < 100;x++){
			for(int y = 0; y < 100; y++){
				
				int col = map.getRGB(x, y);
				
				switch(col & 0xFFFFFF){
					case 0x808080:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.STONE_1));
					break;
					case 0x404040:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WALL_1).isSolid(true));
					break;
					//////////////////////////////////////////////////////////////////////////////////////////////
					case 0x664A36:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_DOWN_MID_MID));
					break;
					case 0xFFBB87:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_DOWN_MID_TOP));
					break;
					case 0xE5A879:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_DOWN_MID_BOTTOM));
					break;
					
					
					case 0xD89F72:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_DOWN_RIGHT_TOP));
					break;
					case 0x997051:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_DOWN_RIGHT_MID));
					break;
					case 0xBF8C65:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_DOWN_RIGHT_BOTTOM));
					break;
					
					case 0xCC956C:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_DOWN_LEFT_TOP));
					break;
					case 0x7F5D43:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_DOWN_LEFT_MID));
					break;
					case 0xB2825E:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_DOWN_LEFT_BOTTOM));
					break;
					///////////////////////////////////////////////////////////////////////////////////////////////
				}
				
			}
		}
	}
	
	public void tick(double deltaTime){
		tiles.tick(deltaTime);
		player.tick(deltaTime);
	}
	
	public void render(Graphics2D g){
		tiles.render(g);
		player.render(g);
	}

}
