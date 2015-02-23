package my.tdl.generator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import my.tdl.main.Assets;

public class Block extends Rectangle{

	public Vector2F pos = new Vector2F();
	public int BlockSize = 48;
	private BlockType blocktype;
	private BufferedImage block;
	private boolean isSolid;
	private boolean isAlive;
	
	public Block(Vector2F pos, BlockType blocktype) {
		setBounds((int)pos.xpos, (int)pos.ypos, BlockSize, BlockSize);
		this.pos = pos;
		isAlive = true;
		this.blocktype = blocktype;
		init();
	}
	
	
	public Block isSolid(boolean isSolid){
		this.isSolid = isSolid;
		return this;
	}
	
	
	public void init(){
		switch(blocktype){
		case STONE_1:
			block = Assets.getStone_1();
			break;
		case WALL_1:
			block = Assets.getWall_1();
			break;
			
			///////////////////////////////////WOOD 1/////////////////////////////////////////////
		case WOOD_DOWN_LEFT_BOTTOM:
			block = Assets.getWood_down_left_bottom();
			break;
		case WOOD_DOWN_LEFT_MID:
			block = Assets.getWood_down_left_mid();
			break;
		case WOOD_DOWN_LEFT_TOP:
			block = Assets.getWood_down_left_top();
			break;
		case WOOD_DOWN_MID_BOTTOM:
			block = Assets.getWood_down_mid_bottom();
			break;
		case WOOD_DOWN_MID_MID:
			block = Assets.getWood_down_mid_mid();
			break;
		case WOOD_DOWN_MID_TOP:
			block = Assets.getWood_down_mid_top();
			break;
		case WOOD_DOWN_RIGHT_BOTTOM:
			block = Assets.getWood_down_right_bottom();
			break;
		case WOOD_DOWN_RIGHT_MID:
			block = Assets.getWood_down_right_mid();
			break;
		case WOOD_DOWN_RIGHT_TOP:
			block = Assets.getWood_down_right_top();
			break;
			///////////////////////////////////WOOD 1/////////////////////////////////////////////
			
			///////////////////////////////////WOOD 1/////////////////////////////////////////////
		case CHECKBOARD_DOWN_LEFT_BOTTOM:
			block = Assets.getCheckboard_down_left_bottom();
			break;
		case CHECKBOARD_DOWN_LEFT_MID:
			block = Assets.getCheckboard_down_left_mid();
			break;
		case CHECKBOARD_DOWN_LEFT_TOP:
			block = Assets.getCheckboard_down_left_top();
			break;
		case CHECKBOARD_DOWN_MID_BOTTOM:
			block = Assets.getCheckboard_down_mid_bottom();
			break;
		case CHECKBOARD_DOWN_MID_MID:
			block = Assets.getCheckboard_down_mid_mid();
			break;
		case CHECKBOARD_DOWN_MID_TOP:
			block = Assets.getCheckboard_down_mid_top();
			break;
		case CHECKBOARD_DOWN_RIGHT_BOTTOM:
			block = Assets.getCheckboard_down_right_bottom();
			break;
		case CHECKBOARD_DOWN_RIGHT_MID:
			block = Assets.getCheckboard_down_right_mid();
			break;
		case CHECKBOARD_DOWN_RIGHT_TOP:
			block = Assets.getCheckboard_down_right_top();
			break;
			///////////////////////////////////WOOD 1/////////////////////////////////////////////
		}
	}
	
	
	public void tick(double deltaTime){
		if(isAlive){
			setBounds((int)pos.xpos, (int)pos.ypos, BlockSize, BlockSize);
		}
	}
	
	public void render(Graphics2D g){
		if(isAlive){
			//g.drawRect((int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize);
			g.drawImage(block, (int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize, null);
			
			if(isSolid){
				g.drawRect((int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize);
			}
		}
	}
	
//	wood_down_left_top = blocks.getTile(48, 0, 16, 16);
//	wood_down_left_mid = blocks.getTile(48, 16, 16, 16);
//	wood_down_left_bottom = blocks.getTile(48, 32, 16, 16);
//	
//	wood_down_mid_top = blocks.getTile(64, 0, 16, 16);
//	wood_down_mid_mid = blocks.getTile(64, 16, 16, 16);
//	wood_down_mid_bottom = blocks.getTile(64, 32, 16, 16);
//	
//	wood_down_right_top = blocks.getTile(64+16, 0, 16, 16);
//	wood_down_right_mid = blocks.getTile(64+16, 16, 16, 16);
//	wood_down_right_bottom = blocks.getTile(64+16, 32, 16, 16);
	
	
	public enum BlockType{
		STONE_1,
		WALL_1,
		
		
		//WOOD 1###########################
		WOOD_DOWN_LEFT_TOP,
		WOOD_DOWN_LEFT_MID,
		WOOD_DOWN_LEFT_BOTTOM,
		WOOD_DOWN_MID_TOP,
		WOOD_DOWN_MID_MID,
		WOOD_DOWN_MID_BOTTOM,
		WOOD_DOWN_RIGHT_TOP,
		WOOD_DOWN_RIGHT_MID,
		WOOD_DOWN_RIGHT_BOTTOM,
		//WOOD 1###########################
		
		
		//CHECKBOARD 1###########################
		CHECKBOARD_DOWN_LEFT_TOP,
		CHECKBOARD_DOWN_LEFT_MID,
		CHECKBOARD_DOWN_LEFT_BOTTOM,
		CHECKBOARD_DOWN_MID_TOP,
		CHECKBOARD_DOWN_MID_MID,
		CHECKBOARD_DOWN_MID_BOTTOM,
		CHECKBOARD_DOWN_RIGHT_TOP,
		CHECKBOARD_DOWN_RIGHT_MID,
		CHECKBOARD_DOWN_RIGHT_BOTTOM
		//CHECKBOARD 1###########################
	}


	public boolean isSolid() {
		return isSolid;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	
	

}
