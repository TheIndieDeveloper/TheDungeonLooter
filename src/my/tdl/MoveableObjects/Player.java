package my.tdl.MoveableObjects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import my.project.gop.main.Vector2F;
import my.tdl.gameloop.GameLoop;
import my.tdl.main.Animator;
import my.tdl.main.Assets;
import my.tdl.main.Check;
import my.tdl.main.Main;
import my.tdl.managers.GUImanager;
import my.tdl.managers.HUDmanager;
import my.tdl.managers.Mousemanager;

public class Player implements KeyListener {

	
	Vector2F pos;
	private int width = 32;
	private int height = 32;
	private int scale = 2;
	private static boolean up,down,left,right;
	private float maxSpeed = 3*32F;
	
	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;
	
	private float slowdown = 4.093F;
	
	private float fixDt = 1f/60F;
	
	Mousemanager playerMM = new Mousemanager();
	
	
	/*
	 * Rendering
	 */
	private int renderDistanceW = 48;
	private int renderDistanceH = 20;
	public static Rectangle render;
	
	//TODO 
	private int animationState = 4;
	
	private int lastAnimationState;
	
	
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
		hudm = new HUDmanager(this);
		guim = new GUImanager();
		pos = new Vector2F(Main.width / 2 - width / 2, Main.height / 2 - height / 2);
	}
	
	public void init() {
		
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
		ani_up.setSpeed(180);
		ani_up.play();
		//DOWN
		ani_down = new Animator(listDown);
		ani_down.setSpeed(180);
		ani_down.play();
		//RIGHT
		ani_right = new Animator(listRight);
		ani_right.setSpeed(180);
		ani_right.play();
		//LEFT
		ani_left = new Animator(listLeft);
		ani_left.setSpeed(180);
		ani_left.play();
		
		//IDLE
		ani_idle = new Animator(listIdel);
		ani_idle.setSpeed(180);
		ani_idle.play();
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
		
		if(!up && !down && !right && !left){
			/*
			 * standing still
			 */
			animationState = 4;
		}
	}
	/*
	
	public void PlayerMoveCode(){
		if(!mapMove){
			if(right){
				
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) ,
								  (int) (pos.ypos + GameLoop.map.ypos)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) , 
								  (int) (pos.ypos + GameLoop.map.ypos + height))  )){
				
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
						
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) ,
								  (int) (pos.ypos + GameLoop.map.ypos)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) , 
								  (int) (pos.ypos + GameLoop.map.ypos + height))  )){
				
					
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
						
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) ,
								  (int) (pos.ypos + GameLoop.map.ypos + height)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) , 
								  (int) (pos.ypos + GameLoop.map.ypos))  )){
				
					
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
						
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) ,
								  (int) (pos.ypos + GameLoop.map.ypos + height)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) , 
								  (int) (pos.ypos + GameLoop.map.ypos))  )){
				
					
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
						
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								  (int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
								  (int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd))  )){
					
			
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
						
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								  (int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
								  (int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd))  )){
					
			
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
						
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								  (int) (pos.ypos + GameLoop.map.ypos - moveAmountu)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
								  (int) (pos.ypos + GameLoop.map.ypos - moveAmountu))  )){
				
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
						
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								  (int) (pos.ypos + GameLoop.map.ypos - moveAmountu)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
								  (int) (pos.ypos + GameLoop.map.ypos - moveAmountu))  )){
					
				
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
				
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						  (int) (pos.ypos + GameLoop.map.ypos - speed)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
						  (int) (pos.ypos + GameLoop.map.ypos - speed))  )){
			
			if(speedUp < maxSpeed){
				speedUp += slowdown;
			}else{
				speedUp = maxSpeed;
			}
			
			GameLoop.map.ypos-=speed;
			
		}else{
			speedUp = 0;
		}	
	}
	public void moveMapUpGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						  (int) (pos.ypos + GameLoop.map.ypos - speed)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
						  (int) (pos.ypos + GameLoop.map.ypos - speed))  )){
		
			if(speedUp != 0){
				speedUp -= slowdown;
				
				if(speedUp < 0){
					speedUp = 0;
				}
				
			}
			
			GameLoop.map.ypos-=speed;
			
		}else{
			speedUp = 0;
		}
	}
	
	public void moveMapDown(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						  (int) (pos.ypos + GameLoop.map.ypos + height + speed)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
						  (int) (pos.ypos + GameLoop.map.ypos + height + speed))  )){
	
			if(speedDown < maxSpeed){
				speedDown += slowdown;
			}else{
				speedDown = maxSpeed;
			}
			
			GameLoop.map.ypos+=speed;
			
		}else{
			speedDown = 0;
		}
	}
	public void moveMapDownGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						  (int) (pos.ypos + GameLoop.map.ypos + height + speed)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
						  (int) (pos.ypos + GameLoop.map.ypos + height + speed))  )){
			
			if(speedDown != 0){
				speedDown -= slowdown;
				
				if(speedDown < 0){
					speedDown = 0;
				}
				
			}
			
			GameLoop.map.ypos+=speed;
			
		}else{
			speedDown = 0;
		}
	}
	
	public void moveMapRight(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) ,
						  (int) (pos.ypos + GameLoop.map.ypos)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) , 
						  (int) (pos.ypos + GameLoop.map.ypos + height))  )){
		
			
			if(speedRight < maxSpeed){
				speedRight += slowdown;
			}else{
				speedRight = maxSpeed;
			}
			
			
			GameLoop.map.xpos+=speed;
		}else{
			speedRight = 0;
		}
	}
	public void moveMapRightGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) ,
						  (int) (pos.ypos + GameLoop.map.ypos)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) , 
						  (int) (pos.ypos + GameLoop.map.ypos + height))  )){
		
			
			if(speedRight != 0){
				speedRight -= slowdown;
				
				if(speedRight < 0){
					speedRight = 0;
				}
			}
			
			GameLoop.map.xpos+=speed;
			
		}else{
			speedRight = 0;
		}
	}
	
	public void moveMapLeft(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) ,
						  (int) (pos.ypos + GameLoop.map.ypos + height)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) , 
						  (int) (pos.ypos + GameLoop.map.ypos))  )){
		
			
			if(speedLeft < maxSpeed){
				speedLeft += slowdown;
			}else{
				speedLeft = maxSpeed;
			}
			
			GameLoop.map.xpos-=speed;
			
		}else{
			speedLeft = 0;
		}
	}
	public void moveMapLeftGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) ,
						  (int) (pos.ypos + GameLoop.map.ypos + height)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) , 
						  (int) (pos.ypos + GameLoop.map.ypos))  )){
		
			
			if(speedLeft != 0){
				speedLeft -= slowdown;
				
				if(speedLeft < 0){
					speedLeft = 0;
				}
			}
			
			GameLoop.map.xpos-=speed;
			
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
		
		g.drawRect((int)pos.xpos - renderDistanceW*32 / 2 + width / 2, (int)pos.ypos - renderDistanceH*32 / 2 + height / 2, renderDistanceW*32,  renderDistanceH*32);
		hudm.render(g);
		guim.render(g);
		playerMM.render(g);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			up = true;
		}
		if(key == KeyEvent.VK_S){
			down = true;
		}
		if(key == KeyEvent.VK_A){
			left = true;
		}
		if(key == KeyEvent.VK_D){
			right = true;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
