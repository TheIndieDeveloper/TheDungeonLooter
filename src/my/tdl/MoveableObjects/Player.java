package my.tdl.MoveableObjects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

import my.project.gop.main.Vector2F;
import my.tdl.gameloop.GameLoop;
import my.tdl.gamstate.GameStateButton;
import my.tdl.generator.World;
import my.tdl.main.Animator;
import my.tdl.main.Assets;
import my.tdl.main.Check;
import my.tdl.main.Main;
import my.tdl.managers.GUImanager;
import my.tdl.managers.HUDmanager;
import my.tdl.managers.Mousemanager;

public class Player implements KeyListener {

	
	Vector2F pos;
	private World world;
	private int width = 32;
	private int height = 32;
	private int scale = 2;
	private static boolean up,down,left,right,running;
	private static boolean debug = false;
	private float maxSpeed = 3*32F;
	
	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;
	
	private float slowdown = 4.093F;
	
	private float fixDt = 1f/60F;
	private static long animationSpeed = 180;
	
	private static boolean moving;
	private static boolean spawned;
	
	
	//ATTACK
	private static boolean attack;
	private static boolean hasAttacked;
	private double defaultAttackCooldown = 120;
	private double attackCooldown = defaultAttackCooldown;
	private double attackSpeed = 5;
	private BufferedImage attacking;
	
	
	Mousemanager playerMM = new Mousemanager();
	
	/*
	 * Rendering
	 */
	private int renderDistanceW = 48;
	private int renderDistanceH = 20;
	public static Rectangle render;
	
	//TODO 
	private int animationState = 4;
	
	
	
	
	/*
	 * 0 = up
	 * 1 = down
	 * 2 = right
	 * 3 = left
	 * 4 = idel
	 */
	
	private ArrayList<BufferedImage> listUp;
	Animator ani_up;
	private ArrayList<BufferedImage> listDown;
	Animator ani_down;
	private ArrayList<BufferedImage> listRight;
	Animator ani_right;
	private ArrayList<BufferedImage> listLeft;
	Animator ani_left;

	private ArrayList<BufferedImage> listIdel;
	Animator ani_idle;
	
	private HUDmanager hudm;
	private GUImanager guim;

	public Player() {
		pos = new Vector2F(Main.width / 2 - width / 2, Main.height / 2 - height / 2);
	}

	public void init(World world) {
		attacking = null;
		hudm = new HUDmanager(world);
		guim = new GUImanager();

		this.world = world;
	
		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistanceW*32 / 2 + width / 2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceH*32 / 2 + height / 2), 
				renderDistanceW*32, 
				renderDistanceH*32);
		
		listUp = new ArrayList<BufferedImage>();
		listDown = new ArrayList<BufferedImage>();
		listRight = new ArrayList<BufferedImage>();
		listLeft = new ArrayList<BufferedImage>();
		listIdel = new ArrayList<BufferedImage>();
		
		listUp.add(Assets.player.getTile(0, 0, 16, 16));
		listUp.add(Assets.player.getTile(16, 0, 16, 16));
		
		listDown.add(Assets.player.getTile(0, 16, 16, 16));
		listDown.add(Assets.player.getTile(16, 16, 16, 16));
		
		listRight.add(Assets.player.getTile(32, 16, 16, 16));
		listRight.add(Assets.player.getTile(48, 16, 16, 16));
		listRight.add(Assets.player.getTile(64, 16, 16, 16));
		listRight.add(Assets.player.getTile(64+16, 16, 16, 16));
		
		listLeft.add(Assets.player.getTile(32, 0, 16, 16));
		listLeft.add(Assets.player.getTile(48, 0, 16, 16));
		listLeft.add(Assets.player.getTile(64, 0, 16, 16));
		listLeft.add(Assets.player.getTile(64+16, 0, 16, 16));
		
		
		listIdel.add(Assets.player.getTile(0, 32, 16, 16));
		listIdel.add(Assets.player.getTile(16, 32, 16, 16));
		listIdel.add(Assets.player.getTile(32, 32, 16, 16));
		listIdel.add(Assets.player.getTile(48, 32, 16, 16));
		
		
		//UP
		ani_up = new Animator(listUp);
		ani_up.setSpeed(animationSpeed);
		ani_up.play();
		//DOWN
		ani_down = new Animator(listDown);
		ani_down.setSpeed(animationSpeed);
		ani_down.play();
		//RIGHT
		ani_right = new Animator(listRight);
		ani_right.setSpeed(animationSpeed);
		ani_right.play();
		//LEFT
		ani_left = new Animator(listLeft);
		ani_left.setSpeed(animationSpeed);
		ani_left.play();
		
		//IDLE
		ani_idle = new Animator(listIdel);
		ani_idle.setSpeed(animationSpeed);
		ani_idle.play();
		
		spawned = true;
	}

	public void tick(double deltaTime) {
		
		playerMM.tick();
		
		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistanceW*32 / 2 + width / 2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceH*32 / 2 + height / 2), 
				renderDistanceW*32, 
				renderDistanceH*32);
	
		float moveAmountu = (float) (speedUp * fixDt);
		float moveAmountd = (float) (speedDown * fixDt);
		float moveAmountl = (float) (speedLeft * fixDt);
		float moveAmountr = (float) (speedRight * fixDt);

			//PLAYER MOVE
			
		if(up){
			moveMapUp(moveAmountu);
			animationState = 0;
		}else{
			moveMapUpGlide(moveAmountu);
		}
		
		if(down){
			moveMapDown(moveAmountd);
			animationState = 1;
		}else{
			moveMapDownGlide(moveAmountd);
		}
		
		if(right){
			moveMapRight(moveAmountr);
			animationState = 2;
		}else{
			moveMapRightGlide(moveAmountr);
		}
		
		if(left){
			moveMapLeft(moveAmountl);
			animationState = 3;
		}else{
			moveMapLeftGlide(moveAmountl);
		}
		
		//TODO ATTACK
		if(attack){
			
			animationState = 5;

			if(!hasAttacked){
				
				if(up){
					attacking = Assets.getUp_attack();
					hasAttacked = true;
				}
				if(down){
					attacking = Assets.getDown_attack();
					hasAttacked = true;
				}
				
				if(right){
					attacking = Assets.getRight_attack();
					hasAttacked = true;
				}
				if(left){
					attacking = Assets.getLeft_attack();
					hasAttacked = true;
				}
				
				if(!up && !down && !right && !left){
					attack = false;
					hasAttacked = false;
					attackCooldown = defaultAttackCooldown;
				}
			}
			
			if(hasAttacked){
				if(attackCooldown != 0){
					attackCooldown -= attackSpeed;
				}
				if(attackCooldown == 0){
					attack = false;
					hasAttacked = false;
					attackCooldown = defaultAttackCooldown;
					//attacking = null;
				}
			}
		}
		
		if(!attack){
			if(!up && !down && !right && !left){
				/*
				 * standing still
				 */
				animationState = 4;
				if(moving){
					moving = false;
				}
			}
		}
		
		if(running){
			if(animationSpeed != 100){
				animationSpeed = 100;		
				ani_up.setSpeed(animationSpeed);
				ani_down.setSpeed(animationSpeed);
				ani_right.setSpeed(animationSpeed);
				ani_left.setSpeed(animationSpeed);
				ani_idle.setSpeed(animationSpeed);
				maxSpeed += 64;
			}
		}else{
			if(animationSpeed != 180){
				animationSpeed = 180;
				ani_up.setSpeed(animationSpeed);
				ani_down.setSpeed(animationSpeed);
				ani_right.setSpeed(animationSpeed);
				ani_left.setSpeed(animationSpeed);
				ani_idle.setSpeed(animationSpeed);
				maxSpeed -= 64;
			}
		}
	}
	/*
	
	public void PlayerMoveCode(){
		if(!mapMove){
			if(right){
				
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos + width + moveAmountr) ,
								  (int) (pos.ypos + world.map_pos.ypos)),
								  
						new Point((int) (pos.xpos + world.map_pos.xpos + width + moveAmountr) , 
								  (int) (pos.ypos + world.map_pos.ypos + height))  )){
				
					if(speedRight < maxSpeed){
						speedRight += slowdown;
					}else{
						speedRight = maxSpeed;
					}
					
					pos.xpos+=moveAmountr;
					
					
				}else{
					speedRight = 0;
				}
				
			}else{
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos + width + moveAmountr) ,
								  (int) (pos.ypos + world.map_pos.ypos)),
								  
						new Point((int) (pos.xpos + world.map_pos.xpos + width + moveAmountr) , 
								  (int) (pos.ypos + world.map_pos.ypos + height))  )){
				
					
					if(speedRight != 0){
						speedRight -= slowdown;
						
						if(speedRight < 0){
							speedRight = 0;
						}
					}
					
					pos.xpos+=moveAmountr;
					
				}else{
					speedRight = 0;
				}
			}
			
			if(left){
				
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos - moveAmountl) ,
								  (int) (pos.ypos + world.map_pos.ypos + height)),
								  
						new Point((int) (pos.xpos + world.map_pos.xpos - moveAmountl) , 
								  (int) (pos.ypos + world.map_pos.ypos))  )){
				
					
					if(speedLeft < maxSpeed){
						speedLeft += slowdown;
					}else{
						speedLeft = maxSpeed;
					}
					
					pos.xpos-=moveAmountl;
					
				}else{
					speedLeft = 0;
				}
				
			}else{

				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos - moveAmountl) ,
								  (int) (pos.ypos + world.map_pos.ypos + height)),
								  
						new Point((int) (pos.xpos + world.map_pos.xpos - moveAmountl) , 
								  (int) (pos.ypos + world.map_pos.ypos))  )){
				
					
					if(speedLeft != 0){
						speedLeft -= slowdown;
						
						if(speedLeft < 0){
							speedLeft = 0;
						}
					}
					
					pos.xpos-=moveAmountl;
					
				}else{
					speedLeft = 0;
				}
					
			}
			
			
			
			if(down){
				
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos) ,
								  (int) (pos.ypos + world.map_pos.ypos + height + moveAmountd)),
								  
						new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
								  (int) (pos.ypos + world.map_pos.ypos + height + moveAmountd))  )){
					
			
					if(speedDown < maxSpeed){
						speedDown += slowdown;
					}else{
						speedDown = maxSpeed;
					}
					
					
					pos.ypos+=moveAmountd;
					
				}else{
					speedDown = 0;
				}
			
			
			}else{
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos) ,
								  (int) (pos.ypos + world.map_pos.ypos + height + moveAmountd)),
								  
						new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
								  (int) (pos.ypos + world.map_pos.ypos + height + moveAmountd))  )){
					
			
					if(speedDown != 0){
						speedDown -= slowdown;
						
						if(speedDown < 0){
							speedDown = 0;
						}
					}
					
					pos.ypos+=moveAmountd;
				}else{
					speedDown = 0;
				}
			

			}
			
			
			
			if(up){
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos) ,
								  (int) (pos.ypos + world.map_pos.ypos - moveAmountu)),
								  
						new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
								  (int) (pos.ypos + world.map_pos.ypos - moveAmountu))  )){
				
					if(speedUp < maxSpeed){
						speedUp += slowdown;
					}else{
						speedUp = maxSpeed;
					}
							
					pos.ypos-=moveAmountu;
	
				}else{
					speedUp = 0;
				}
				
			}else{
							
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos) ,
								  (int) (pos.ypos + world.map_pos.ypos - moveAmountu)),
								  
						new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
								  (int) (pos.ypos + world.map_pos.ypos - moveAmountu))  )){
					
				
					if(speedUp != 0){
						speedUp -= slowdown;
						
						if(speedUp < 0){
							speedUp = 0;
						}
					}
					
					pos.ypos-=moveAmountu;
				
				}else{
					speedUp = 0;
				}
				
			}
		
		
		
		
		
		
		
	}else{
	}
	}
	
	*/
	
	public void moveMapUp(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos) ,
						  (int) (pos.ypos + world.map_pos.ypos - speed)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
						  (int) (pos.ypos + world.map_pos.ypos - speed))  )){
			
			if(speedUp < maxSpeed){
				speedUp += slowdown;
			}else{
				speedUp = maxSpeed;
			}
			
			world.map_pos.ypos-=speed;
			
		}else{
			speedUp = 0;
		}	
	}
	public void moveMapUpGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos) ,
						  (int) (pos.ypos + world.map_pos.ypos - speed)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
						  (int) (pos.ypos + world.map_pos.ypos - speed))  )){
		
			if(speedUp != 0){
				speedUp -= slowdown;
				
				if(speedUp < 0){
					speedUp = 0;
				}
				
			}
			
			world.map_pos.ypos-=speed;
			
		}else{
			speedUp = 0;
		}
	}
	
	public void moveMapDown(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos) ,
						  (int) (pos.ypos + world.map_pos.ypos + height + speed)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
						  (int) (pos.ypos + world.map_pos.ypos + height + speed))  )){
	
			if(speedDown < maxSpeed){
				speedDown += slowdown;
			}else{
				speedDown = maxSpeed;
			}
			
			world.map_pos.ypos+=speed;
			
		}else{
			speedDown = 0;
		}
	}
	public void moveMapDownGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos) ,
						  (int) (pos.ypos + world.map_pos.ypos + height + speed)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
						  (int) (pos.ypos + world.map_pos.ypos + height + speed))  )){
			
			if(speedDown != 0){
				speedDown -= slowdown;
				
				if(speedDown < 0){
					speedDown = 0;
				}
				
			}
			
			world.map_pos.ypos+=speed;
			
		}else{
			speedDown = 0;
		}
	}
	
	public void moveMapRight(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos + width + speed) ,
						  (int) (pos.ypos + world.map_pos.ypos)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width + speed) , 
						  (int) (pos.ypos + world.map_pos.ypos + height))  )){
		
			
			if(speedRight < maxSpeed){
				speedRight += slowdown;
			}else{
				speedRight = maxSpeed;
			}
			
			
			world.map_pos.xpos+=speed;
		}else{
			speedRight = 0;
		}
	}
	public void moveMapRightGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos + width + speed) ,
						  (int) (pos.ypos + world.map_pos.ypos)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width + speed) , 
						  (int) (pos.ypos + world.map_pos.ypos + height))  )){
		
			
			if(speedRight != 0){
				speedRight -= slowdown;
				
				if(speedRight < 0){
					speedRight = 0;
				}
			}
			
			world.map_pos.xpos+=speed;
			
		}else{
			speedRight = 0;
		}
	}
	
	public void moveMapLeft(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos - speed) ,
						  (int) (pos.ypos + world.map_pos.ypos + height)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos - speed) , 
						  (int) (pos.ypos + world.map_pos.ypos))  )){
		
			
			if(speedLeft < maxSpeed){
				speedLeft += slowdown;
			}else{
				speedLeft = maxSpeed;
			}
			
			world.map_pos.xpos-=speed;
			
		}else{
			speedLeft = 0;
		}
	}
	public void moveMapLeftGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos - speed) ,
						  (int) (pos.ypos + world.map_pos.ypos + height)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos - speed) , 
						  (int) (pos.ypos + world.map_pos.ypos))  )){
		
			
			if(speedLeft != 0){
				speedLeft -= slowdown;
				
				if(speedLeft < 0){
					speedLeft = 0;
				}
			}
			
			world.map_pos.xpos-=speed;
			
		}else{
			speedLeft = 0;
		}
	}
	
	public void render(Graphics2D g) {
		//g.fillRect((int)pos.xpos, (int)pos.ypos, width, height);
		
		//g.clearRect(0, 0, Main.width, Main.height);
		//g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
		//UP
		if(animationState == 0){
			g.drawImage(ani_up.sprite, (int)pos.xpos - width / 2, (int)pos.ypos  - height, width * scale ,height * scale, null);
			if(up){
				ani_up.update(System.currentTimeMillis());
			}
		}
		//DOWN
		if(animationState == 1){
			g.drawImage(ani_down.sprite, (int)pos.xpos - width / 2, (int)pos.ypos  - height,  width * scale ,height * scale, null);
			if(down){
				ani_down.update(System.currentTimeMillis());
			}
		}
		//RIGHT
		if(animationState == 2){
			g.drawImage(ani_right.sprite, (int)pos.xpos - width / 2, (int)pos.ypos  - height,  width * scale ,height * scale, null);
			if(right){
				ani_right.update(System.currentTimeMillis());
			}
		}
		//LEFT
		if(animationState == 3){
			g.drawImage(ani_left.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale ,height * scale, null);
			if(left){
				ani_left.update(System.currentTimeMillis());
			}
		}
		//IDLE
		if(animationState == 4){
			g.drawImage(ani_idle.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale ,height * scale, null);
			ani_idle.update(System.currentTimeMillis());
		}
		
		//g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		
		if(attack){
			if(attacking != null){
				g.drawImage(attacking,(int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale ,height * scale, null);
			}
		}
		
		
		g.drawRect((int)pos.xpos - renderDistanceW*32 / 2 + width / 2, (int)pos.ypos - renderDistanceH*32 / 2 + height / 2, renderDistanceW*32,  renderDistanceH*32);
		hudm.render(g);
		guim.render(g);
		playerMM.render(g);
		
		g.drawString(attackCooldown+"", 500, 300);
		g.drawString(attack+"", 500, 350);
		g.drawString(hasAttacked+"", 500, 400);
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			if(!moving){
				moving = true;
			}
			up = true;
		}
		if(key == KeyEvent.VK_S){
			if(!moving){
				moving = true;
			}
			down = true;
		}
		if(key == KeyEvent.VK_A){
			if(!moving){
				moving = true;
			}
			left = true;
		}
		//ATTACK
		if(!attack && !hasAttacked){
			if(key == KeyEvent.VK_SPACE){
				attack = true;
			}
		}
		
		if(key == KeyEvent.VK_D){
			if(!moving){
				moving = true;
			}
			right = true;
		}
		if(key == KeyEvent.VK_SHIFT){
			running = true;
		}
		if(key == KeyEvent.VK_F3){
			if(!debug){
				debug = true;	
			}else{
				debug = false;
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			up = false;
		}
		if(key == KeyEvent.VK_S){
			down = false;
		}
		if(key == KeyEvent.VK_A){
			left = false;
		}
		if(key == KeyEvent.VK_D){
			right = false;
		}
		if(key == KeyEvent.VK_SHIFT){
			running = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	///////////////////////////////////
	///////////////////////////////////
	///////////////////////////////////	
	
	
	
	
	
	public Vector2F getPos() {
		return pos;
	}
	
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	public float getSlowdown() {
		return slowdown;
	}

	public World getWorld() {
		return world;
	}
	
	public boolean isDebuging() {
		return debug;
	}
	
	
	public boolean isMoving(){
		return moving;
	}
	
	public boolean hasSpawned(){
		return spawned;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
