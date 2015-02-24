package my.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import my.tdl.MoveableObjects.Player;
import my.tdl.generator.Block.BlockType;

public class World {

	private String worldName;
	private BufferedImage world_image;
	private int world_width;
	private int world_height;
	
	private TileManager tiles = new TileManager();
	private Player player = new Player();
	
	public World(String worldName, BufferedImage world_image, int world_width, int world_height) {
		this.worldName = worldName;
		this.world_image = world_image;
		this.world_width = world_width;
		this.world_height = world_height;
	}

	public void generateWorld() {
		
		for(int x = 0; x < world_width;x++){
			for(int y = 0; y < world_height; y++){
				
				int col = world_image.getRGB(x, y);
				
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
		player.init(this);
	}
	
	
	public void tick(double deltaTime){
		tiles.tick(deltaTime);
		player.tick(deltaTime);
	}
	
	public void render(Graphics2D g){
		tiles.render(g);
		player.render(g);
	}

	
	
	public String getWorldName() {
		return worldName;
	}
	
	public BufferedImage getWorld_image() {
		return world_image;
	}

}
