package my.tdl.main;

import java.awt.HeadlessException;
import java.awt.image.BufferedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;

public class Assets {
	
	SpriteSheet blocks = new SpriteSheet();
	public static SpriteSheet player = new SpriteSheet();
	
	public static BufferedImage stone_1;
	public static BufferedImage wall_1;
	
	//HUD
	private static BufferedImage player_fast_equip;
	
	//MOUSE
	private static BufferedImage mouse_pressed;
	private static BufferedImage mouse_unpressed;
	
	//WOOD 1
	private static BufferedImage wood_down_right_top;	
	private static BufferedImage wood_down_left_top;
	private static BufferedImage wood_down_mid_top;
	
	private static BufferedImage wood_down_right_mid;	
	private static BufferedImage wood_down_left_mid;
	private static BufferedImage wood_down_mid_mid;
	
	private static BufferedImage wood_down_right_bottom;	
	private static BufferedImage wood_down_left_bottom;
	private static BufferedImage wood_down_mid_bottom;
	
	
	//CHECKERBOARD
	private static BufferedImage checkboard_down_right_top;	
	private static BufferedImage checkboard_down_left_top;
	private static BufferedImage checkboard_down_mid_top;
	
	private static BufferedImage checkboard_down_right_mid;	
	private static BufferedImage checkboard_down_left_mid;
	private static BufferedImage checkboard_down_mid_mid;
	
	private static BufferedImage checkboard_down_right_bottom;	
	private static BufferedImage checkboard_down_left_bottom;
	private static BufferedImage checkboard_down_mid_bottom;

	
	
	public void init(){
		blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "spritesheet.png"));
		player.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "PlayerSheet.png"));
		
		
		player_fast_equip = player.getTile(64*2+48, 0, 64*2+64+16, 16);
		
		stone_1 = blocks.getTile(0, 0, 16, 16);
		wall_1 = blocks.getTile(16, 0, 16, 16);
		
		mouse_pressed = player.getTile(64+32+8, 8, 8, 8);
		mouse_unpressed = player.getTile(64+32, 8, 8, 8);
		
		//WOOD 1
		wood_down_left_top = blocks.getTile(48, 0, 16, 16);
		wood_down_left_mid = blocks.getTile(48, 16, 16, 16);
		wood_down_left_bottom = blocks.getTile(48, 32, 16, 16);
		
		wood_down_mid_top = blocks.getTile(64, 0, 16, 16);
		wood_down_mid_mid = blocks.getTile(64, 16, 16, 16);
		wood_down_mid_bottom = blocks.getTile(64, 32, 16, 16);
		
		wood_down_right_top = blocks.getTile(64+16, 0, 16, 16);
		wood_down_right_mid = blocks.getTile(64+16, 16, 16, 16);
		wood_down_right_bottom = blocks.getTile(64+16, 32, 16, 16);
		
		
		
		//CHECKERBOARD
		checkboard_down_left_top = blocks.getTile(48, 0+48, 16, 16);
		checkboard_down_left_mid = blocks.getTile(48, 16+48, 16, 16);
		checkboard_down_left_bottom = blocks.getTile(48, 32+48, 16, 16);
		
		checkboard_down_mid_top = blocks.getTile(64, 0+48, 16, 16);
		checkboard_down_mid_mid = blocks.getTile(64, 16+48, 16, 16);
		checkboard_down_mid_bottom = blocks.getTile(64, 32+48, 16, 16);
		
		checkboard_down_right_top = blocks.getTile(64+16, 0+48, 16, 16);
		checkboard_down_right_mid = blocks.getTile(64+16, 16+48, 16, 16);
		checkboard_down_right_bottom = blocks.getTile(64+16, 32+48, 16, 16);
		
	}
	
	
	public static BufferedImage getStone_1() {
		return stone_1;
	}
	
	public static BufferedImage getWall_1() {
		return wall_1;
	}
	
	public static BufferedImage getMouse_pressed() {
		return mouse_pressed;
	}
	
	public static BufferedImage getMouse_unpressed() {
		return mouse_unpressed;
	}
	
	
	public static BufferedImage getPlayer_fast_equip() {
		return player_fast_equip;
	}

	//WOOD 1
	public static BufferedImage getWood_down_right_top() {
		return wood_down_right_top;
	}
	public static BufferedImage getWood_down_right_mid() {
		return wood_down_right_mid;
	}
	public static BufferedImage getWood_down_right_bottom() {
		return wood_down_right_bottom;
	}

	public static BufferedImage getWood_down_mid_top() {
		return wood_down_mid_top;
	}
	public static BufferedImage getWood_down_mid_mid() {
		return wood_down_mid_mid;
	}
	public static BufferedImage getWood_down_mid_bottom() {
		return wood_down_mid_bottom;
	}
	
	public static BufferedImage getWood_down_left_top() {
		return wood_down_left_top;
	}
	public static BufferedImage getWood_down_left_mid() {
		return wood_down_left_mid;
	}
	public static BufferedImage getWood_down_left_bottom() {
		return wood_down_left_bottom;
	}

	
	//CHECKERBOARD
	public static BufferedImage getCheckboard_down_right_top() {
		return checkboard_down_right_top;
	}
	public static BufferedImage getCheckboard_down_right_mid() {
		return checkboard_down_right_mid;
	}
	public static BufferedImage getCheckboard_down_right_bottom() {
		return checkboard_down_right_bottom;
	}

	public static BufferedImage getCheckboard_down_mid_top() {
		return checkboard_down_mid_top;
	}
	public static BufferedImage getCheckboard_down_mid_mid() {
		return checkboard_down_mid_mid;
	}
	public static BufferedImage getCheckboard_down_mid_bottom() {
		return checkboard_down_mid_bottom;
	}
	
	public static BufferedImage getCheckboard_down_left_top() {
		return checkboard_down_left_top;
	}
	public static BufferedImage getCheckboard_down_left_mid() {
		return checkboard_down_left_mid;
	}
	public static BufferedImage getCheckboard_down_left_bottom() {
		return checkboard_down_left_bottom;
	}
	
}
