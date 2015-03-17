package my.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import my.tdl.MoveableObjects.Player;
import my.tdl.generator.Block.BlockType;
import my.tdl.main.Main;

public class World {

	public Vector2F map_pos = new Vector2F();
	private String worldName;
	private BufferedImage map;
	private int world_width;
	private int world_height;
	private int blockSize = 48;
	private Player player;
	private boolean hasGenerated;
	//LISTS
	private CopyOnWriteArrayList<BlockEntity> blockents;
	public TileManager tiles;
	
	//world Spawn
	private Block spawn;
	
	//BOOLEANS
	private boolean hasSize = false;
	
	public World(String worldName) {
		this.worldName = worldName;
		Vector2F.setWorldVaribles(map_pos.xpos, map_pos.ypos);
	}

	public void init() {
		blockents = new CopyOnWriteArrayList<BlockEntity>();
		tiles = new TileManager(this);	
		
		map_pos.xpos = spawn.getBlockLocation().xpos - player.getPos().xpos;
		map_pos.ypos = spawn.getBlockLocation().ypos - player.getPos().ypos;
		
		if(player != null){
			player.init(this);
		}	
		
	}

	public void tick(double deltaTime) {
		Vector2F.setWorldVaribles(map_pos.xpos, map_pos.ypos);

		if(!player.hasSpawned()){
			spawn.tick(deltaTime);
		}
		tiles.tick(deltaTime);
		
		if(!blockents.isEmpty()){
			for(BlockEntity ent : blockents){
				if(player.render.intersects(ent)){
					ent.tick(deltaTime);
					ent.setAlive(true);
				}else{
					ent.setAlive(false);
				}
			}
		}
		
		if(player != null){
			player.tick(deltaTime);
		}
	}

	public void render(Graphics2D g) {
		tiles.render(g);
		
		if(!player.hasSpawned()){
			spawn.render(g);
		}
		
		if(!blockents.isEmpty()){
			for(BlockEntity ent : blockents){
				if(player.render.intersects(ent)){
					ent.render(g);
					ent.setAlive(true);
				}else{
					ent.setAlive(false);
				}
			}
		}
		
		if(player != null){
			player.render(g);
		}
	}

	public void generate(String world_image_name) {
		
		map = null;
		if(hasSize){
			
			try{
				map = loadImageFrom.LoadImageFrom(Main.class, world_image_name+".png");
			}catch(Exception e){
				
			}
			
			for(int x = 0;x < world_width;x++){
				for(int y = 0;y < world_height;y++){
					
					int col = map.getRGB(x, y);
					
					switch(col & 0xFFFFFF){
					case 0x808080:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.STONE_1));
					break;
					case 0x404040:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WALL_1).isSolid(true));
					break;
					//////////////////////////////////////////////////////////////////////////////////////////////
					case 0x664A36:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_DOWN_MID_MID));
					break;
					case 0xFFBB87:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_DOWN_MID_TOP).isSolid(true));
					break;
					case 0xE5A879:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_DOWN_MID_BOTTOM));
					break;
					
					
					case 0xD89F72:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_DOWN_RIGHT_TOP));
					break;
					case 0x997051:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_DOWN_RIGHT_MID));
					break;
					case 0xBF8C65:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_DOWN_RIGHT_BOTTOM));
					break;
					
					case 0xCC956C:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_DOWN_LEFT_TOP));
					break;
					case 0x7F5D43:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_DOWN_LEFT_MID));
					break;
					case 0xB2825E:
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_DOWN_LEFT_BOTTOM));
					break;
					}
				}
			}
		}
		
		hasGenerated = true;
	}

	public void setSize(int world_width, int world_height) {
		this.world_width = world_width;
		this.world_height = world_height;
		hasSize = true;
	}
		
	public Vector2F getWorldPos(){
		return map_pos;
	}
	
	public float getWorldXpos(){
		return map_pos.xpos;
	}
	
	public float getWorldYpos(){
		return map_pos.ypos;
	}
	
	public void addPlayer(Player player) {
		this.player = player;
	}
	
	public void dropBlockEntity(Vector2F pos, BufferedImage block_image){
		BlockEntity ent = new BlockEntity(pos, block_image);
		if(!blockents.contains(ent)){
			blockents.add(ent);
		}
	}
	
	public void setWorldSpawn(float xpos, float ypos){
		if(xpos < world_width){
			if(ypos < world_height){
				Block spawn = new Block(new Vector2F(xpos*blockSize,ypos*blockSize));
				this.spawn = spawn;
			}
		}
	}
	
	public Vector2F getWorldSpawn(){
		return spawn.pos;
	}
	
	public void removeDropedBlockEntity(BlockEntity blockEntity){
		if(blockents.contains(blockEntity)){
			blockents.remove(blockEntity);
		}
	}
	
	public TileManager getWorldBlocks() {
		return tiles;
	}

	public Player getPlayer() {
		return player;
	}
	
	public boolean hasGenerated() {
		return hasGenerated;
	}
	
}
