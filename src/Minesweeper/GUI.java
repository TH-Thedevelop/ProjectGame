package Minesweeper;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame {
	private static final int check2 = 0;

	public boolean resetter = false;
	
	public boolean flagger = false;
	
	Date startDate = new Date();
	Date endDate;
	
	int scaping = 2;
	
	int neighs = 0;
	
	
	
	String vicMess = "Win!!";
	
//	public int undoX = 200;
//	public int undoY = 5;
	
	public int mx = -100;
	public int my = -100;
	
	public int smileX = 605;
	public int smileY = 5;
	
	public int smileCenter_X = smileX + 35;
	public int smileCenter_Y = smileY + 35;
	
	public int flaggerX = 445;
	public int flaggerY = 5;
	
	public int flaggerCenterX = flaggerX + 35;
	public int flaggerCenterY = flaggerY + 35;
	
	public int timeC_X = 1130;
	public int timeC_Y = 5;
	
	public int vicMes_X = 800;
	public int vicMes_Y = -50;
	
	public int sec = 0;

	
	public boolean happy = true;
	
	public boolean Win = false;
	
	public boolean Lose = false;
	
	
	
	Random rand = new Random();
	
	int [][] mines = new int[16][9]; 
	int [][] neighbours = new int[16][9];
	boolean[][] revealed = new boolean[16][9];
	boolean[][] flagged = new boolean[16][9];
	
	int [][] store = new int[200][200];
	
	int doge = rand.nextInt(5);
	
	
	
	public GUI() {
		this.setTitle("MineSweeper");
		this.setSize(1299,900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		
		
		
		
		//count mines, touch mine
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j ++) {
				revealed[i][j] = false;
				if(rand.nextInt(144) < 20) {
					mines[i][j] = 1;
				}else {
					mines[i][j] = 0;
					
				}
				
			}
		}
//count neighbours where to explore number.
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j ++) {
				neighs = 0;
				for(int m = 0; m < 16; m++) {
					for(int n = 0; n < 9; n ++) {
						if(!(m == i && n == j)) {
							if(isN(i,j,m,n) == true)
								neighs ++ ;
						}
					}
				}
				
				neighbours[i][j] = neighs;
			}
		}
		
		Board board = new Board();
		this.setContentPane(board);
		
		Move move = new Move();
		this.addMouseMotionListener( move);
		Click click = new Click();
		this.addMouseListener(click);
	}
	
	
	public class Board extends JPanel{
		
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0,0, 1280,800);
			
			//SmileIcon painting
			
			g.setColor(Color.yellow);
			g.fillOval(smileX, smileY, 70, 70);
			g.setColor(Color.black);
			g.fillOval(smileX+15, smileY+20, 10, 10);
			g.setColor(Color.black);
			g.fillOval(smileX+45, smileY+20, 10, 10);
			if(happy == true) {
				g.fillRect(smileX+20, smileY+50, 30, 5);
				g.fillRect(smileX+15, smileY+45, 5, 5);
				g.fillRect(smileX+50, smileY+45, 5, 5);
			} else if(happy != true) {
				g.fillRect(smileX+20, smileY+45, 30, 5);
				g.fillRect(smileX+15, smileY+50, 5, 5);
				g.fillRect(smileX+50, smileY+50, 5, 5);
			}
			
			//Flagger paint
			
			g.setColor(Color.orange);
			g.fillRect(flaggerX + 32, flaggerY + 10, 5, 50);
			g.fillRect(flaggerX + 20, flaggerY +50 ,30, 10);
			g.setColor(Color.green);
			g.fillRect(flaggerX + 12, flaggerY + 15, 25, 15);
			g.setColor(Color.black);
			
			if(flagger == true) {
				g.setColor(Color.red);
			}
			
			g.drawOval(flaggerX, flaggerY, 70, 70);
			g.drawOval(flaggerX+1, flaggerY+1, 68, 68);
			g.drawOval(flaggerX+2, flaggerY+2, 66, 66);

			
			
			
			//Time counter paint
			
			g.setColor(Color.CYAN);
			g.fillRect(timeC_X, timeC_Y,110, 70);
			if(Lose == false && Win == false) {
				sec = (int) ((new Date().getTime() - startDate.getTime()) / 1000);
			}
			if(sec > 999) {
				sec = 999;
			}
			g.setColor(Color.BLACK);
			if(Win == true)
				g.setColor(Color.green);
			else if(Lose == true){
				g.setColor(Color.red);
			}
			
			g.setFont(new Font("Tahoma", Font.PLAIN, 60));
			if(sec < 10) {
				g.drawString("00"+Integer.toString(sec), timeC_X, timeC_Y + 55);
			}else if(sec<100) {
				g.drawString("0"+Integer.toString(sec), timeC_X, timeC_Y + 55);}
			else {
			g.drawString(Integer.toString(sec), timeC_X, timeC_Y + 55);
			}
			
			//Victory message painting
			
			if(Win == true ) {
				g.setColor(Color.GREEN);
				vicMess = "YOU WIN!!";
			}
			else if(Lose == true) {
				g.setColor(Color.red);
				vicMess = "YOU LOSE!!";
			}
			
			if(Win == true || Lose == true) {
				vicMes_Y = -50 + (int) (new Date().getTime() - endDate.getTime()) / 10;
				if(vicMes_Y> 67 ) {
					vicMes_Y = 67;
				}
				g.setFont(new Font("Tahoma", Font.PLAIN, 50));
				g.drawString(vicMess, vicMes_X, vicMes_Y);
			}
			
			
			
			for(int i = 0; i < 16; i++) {
				for(int j = 0; j < 9; j ++) {
					g.setColor(Color.gray);
					//know mines where they are.
//					if(mines[i][j] == 1) {
//						
//						g.setColor(Color.yellow);
//					}
					if(revealed[i][j] == true) {
						g.setColor(Color.white);
						if(mines[i][j] == 1) {
							g.setColor(Color.red);
						}
					}
					if(mx >= scaping+i*80 && mx < scaping+i*80 + 80-2*scaping && my >= scaping+j*80+80+26 && my < scaping+j*80+26+80+80-2*scaping) {
						g.setColor(Color.lightGray);
					}
					g.fillRect(scaping+i*80, scaping+j*80+80, 80-2*scaping, 80-2*scaping);
					if(revealed[i][j] == true) {
						//display number 
						g.setColor(Color.black);
						if(mines[i][j] == 0 && neighbours[i][j] != 0) {
							
							if(neighbours[i][j] == 1) 
								g.setColor(Color.blue);
							else if(neighbours[i][j] == 2) 
								g.setColor(Color.green);
							else if(neighbours[i][j] == 3) 
								g.setColor(Color.red);
							else if(neighbours[i][j] == 4) 
								g.setColor(new Color(0,0,128));
							else if(neighbours[i][j] == 5) 
								g.setColor(new Color(178,34,34));
							else if(neighbours[i][j] == 6) 
								g.setColor(new Color(72,209,204));
							else if(neighbours[i][j] == 7) 
								g.setColor(new Color(0,0,128));
							else if(neighbours[i][j] == 8) 
								g.setColor(Color.DARK_GRAY);
							
							g.setFont(new Font("Tahoma", Font.BOLD, 40));
							g.drawString(Integer.toString(neighbours[i][j]),i*80+27,j*80+80+55);}
						else if(mines[i][j] == 1) {
						//display mine
						g.fillRect(i*80+30,j*80+80+20,20,40);
						g.fillRect(i*80+20,j*80+80+30,40,20);
						g.fillRect(i*80+5+20,j*80+80+25,30,30);
						g.fillRect(i*80+38, j*80+80+15, 4, 50);
						g.fillRect(i*80+15, j*80+80+38, 50, 4);
						}
					}
					//paint flagged in cell
					if(flagged[i][j] == true) {
						g.setColor(Color.orange);
						g.fillRect(i*80 + 32, j*80+80 + 10, 5, 50);
						g.fillRect(i*80 + 20, j*80+80 +50 ,30, 10);
						g.setColor(Color.green);
						g.fillRect(i*80 + 12, j*80+80 + 15, 25, 15);
						g.setColor(Color.black);
					}
				}
			}
			
			
			
		}
	}
//Class Move 
	public class Move implements MouseMotionListener {

		
		@Override
		public void mouseDragged(MouseEvent e) {
			
			
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			
			mx = e.getX();
			my = e.getY();
			
		}
}
// Class Click

	public class Click implements MouseListener{

		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			mx = e.getX();
			my = e.getY();
			int total = 0 ;
			if(BoxX() != -1 && BoxY() != -1) {
				
				if(revealed[BoxX()][BoxY()] != false) {
					revealed[BoxX()-1][BoxY()] = openW();
					revealed[BoxX()][BoxY()-1] = openN();
					revealed[BoxX()+1][BoxY()] = openE();
					revealed[BoxX()][BoxY()+1] = openS();
					
					revealed[BoxX()-1][BoxY()-1] = openNW();
					revealed[BoxX()+1][BoxY()-1] = openNE();
					revealed[BoxX()-1][BoxY()+1] = openSW();
					revealed[BoxX()+1][BoxY()+1] = openSE();
					
					
//				total = checkMinesW()+checkMinesN()+checkMinesE()+checkMinesS()+checkMinesSW()+checkMinesNW()+checkMinesNE()+checkMinesSE();
//				System.out.println("W " + checkMinesW() + " ,N " + checkMinesN() + " ,E " + checkMinesE() + " ,S " + checkMinesS());
//				System.out.println("NW " + checkMinesNW() + " ,NE " + checkMinesNE() + " ,SW " + checkMinesSW() + " ,SE " + checkMinesSE());
				}
				
			}
			
			if(BoxX() != -1 && BoxY() != -1) {
				if(flagger == true && revealed[BoxX()][BoxY()] == false) {
					if(flagged[BoxX()][BoxY()] == false) {
					flagged[BoxX()][BoxY()] = true;}
					else 
						flagged[BoxX()][BoxY()] = false;
				} 
				else {
					if(flagged[BoxX()][BoxY()] == false) {
					revealed[BoxX()][BoxY()] = true;}
					}
			}
				
			else {
				System.out.println("Not inside the box!!!");
			}
			if(inSmile() == true)
			System.out.println("in Smile");
			else
			System.out.println("not in Smile");
			
			if(inSmile() == true) {
				resetAll();}
			if(inFlagger() == true) {
				if(flagger == false) {
					flagger = true;
					System.out.println("Push");
					}
					else {
					flagger = false;
					System.out.println("Not Push");}
				}
        }
				
		

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
// Inside Flag
	public boolean inFlagger() {
		int dif = (int) Math.sqrt(Math.abs(mx-flaggerCenterX)*Math.abs(mx-flaggerCenterX)+Math.abs(my-flaggerCenterY)*Math.abs(my-flaggerCenterY));
		if(dif < 35) {
			return true;
		}
		else if(dif >= 35) {
			return false;
		}
		return false;
	}
//In Smile icon	
	public boolean inSmile() {
		int dif = (int) Math.sqrt(Math.abs(mx-smileCenter_X)*Math.abs(mx-smileCenter_X)+Math.abs(my-smileCenter_Y)*Math.abs(my-smileCenter_Y));
		if(dif < 35) {
			return true;
		}
		else if(dif >= 35) {
			return false;
		}
		return false;
	}
//Check condition of win or lose	
	public void checkWinStatus() {
		if(Lose == false ) {
			for(int i = 0; i < 16; i++) {
				for(int j = 0; j < 9; j ++) {
					if(revealed[i][j] == true && mines[i][j] == 1) {
						Lose = true;
						happy = false;
						
						endDate = new Date();
					}
				}
			}
			
		}	
			if ((totalBoxRevealed()) >= 144 - totalMines() && Win == false) {
				Win = true;
				endDate = new Date();
			}
		}
//Total of mines	
	public int totalMines() {
		int total = 0;
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j ++) {
				if(mines[i][j] == 1) {
					total ++;
				}
			}
		}
		return total;
	}
//total Box revealed	
	public int totalBoxRevealed() {
		int total = 0;
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j ++) {
				if(revealed[i][j] == true) {
					total++;
				}
			}
		}
		return total;
	}
// Class ResetAll
	
	public void resetAll() {
		
		resetter = true;
		
		flagger = false;
		
		startDate = new Date();
		
		vicMes_Y = -50;
		
		String vicMess = "Win!!";
		
		happy = true;
		Win = false;
		Lose = false;
		
		//Mines
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j ++) {
				if(rand.nextInt(144) < 20) {
					mines[i][j] = 1;
				}else {
					mines[i][j] = 0;
				}
				revealed[i][j] = false;
				flagged[i][j] = false;
			}
		}
		//count neighbours where to explore number.
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j ++) {
				neighs = 0;
				for(int m = 0; m < 16; m++) {
					for(int n = 0; n < 9; n ++) {
						if(!(m == i && n == j)) {
							if(isN(i,j,m,n) == true)
								neighs ++;
						}
					}
				}
				
				neighbours[i][j] = neighs;
			}
		}
		
		resetter = false;
	}
//X axis in BOX
	public int BoxX() {
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j ++) {
				
				if(mx >= scaping+i*80 && mx < scaping+i*80 + 80-2*scaping && my >= scaping+j*80+80+26 && my < scaping+j*80+26+80+80-2*scaping) {
					return i;
				}
				
			}
		}
		return -1;
	}
//Y axis in BOX	
	public int BoxY() {
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j ++) {
				
				if(mx >= scaping+i*80 && mx < scaping+i*80 + 80-2*scaping && my >= scaping+j*80+80+26 && my < scaping+j*80+26+80+80-2*scaping) {
					return j;
				}
				
			}
		}
		return -1;
	}
	
//
	public boolean isN(int mX, int mY, int cX, int cY){
		if(mX - cX <2 && mX - cX > -2 && mY - cY <2 && mY - cY > -2 && mines[cX][cY] == 1) {
			return true;
		}
		return false;
	}
//////////////////////////////////////////////////////////////////////////////////
public int getMx() {
		return mx;
	}
	public void setMx(int mx) {
		this.mx = mx;
	}
	public int getMy() {
		return my;
	}
	public void setMy(int my) {
		this.my = my;
	}
	public int[][] getMines() {
		return mines;
	}
	public void setMines(int[][] mines) {
		this.mines = mines;
	}
	public boolean[][] getRevealed() {
		return revealed;
	}
	public void setRevealed(boolean[][] revealed) {
		this.revealed = revealed;
	}
	//
////////////////////////////////////////Explore Class///////////////////////// 
	//Check around North East South West
	public int checkMinesW() {
		if(mines[BoxX()-1][BoxY()] == 1)
			return 1;
		return 0;
	}
	public int checkMinesN() {
		if(mines[BoxX()][BoxY()-1] == 1)
			return 1;
		return 0;
	}
	public int checkMinesE() {
		if(mines[BoxX()+1][BoxY()] == 1)
			return 1;
		return 0;
	}
	public int checkMinesS() {
		if(mines[BoxX()][BoxY()+1] == 1)
			return 1;
		return 0;
	}
	//
	public int checkMinesNE() {
		if(mines[BoxX()+1][BoxY()-1] == 1)
			return 1;
		return 0;
	}
	public int checkMinesNW() {
		if(mines[BoxX()-1][BoxY()-1] == 1)
			return 1;
		return 0;
	}
	public int checkMinesSE() {
		if(mines[BoxX()+1][BoxY()+1] == 1)
			return 1;
		return 0;
	}
	public int checkMinesSW() {
		if(mines[BoxX()-1][BoxY()+1] == 1)
			return 1;
		return 0;
	}
	public boolean openW() {
		if(checkMinesW() == 1 ) {
			return false;
		}else
			return true;
	}
	
	public boolean openN() {
		if(checkMinesN() == 1) {
			return false;
		}else
			return true;
	}
	
	public boolean openE() {
		if(checkMinesE() == 1) {
			return false;
		}else
			return true;
	}
	
	public boolean openS() {
		if(checkMinesS() == 1) {
			return false;
		}else
			return true;
	}
	
	public boolean openNW() {
		if(checkMinesNW() == 1) {
			return false;
		}else
			return true;
	}
	public boolean openNE() {
		if(checkMinesNE() == 1) {
			return false;
		}else
			return true;
	}
	public boolean openSW() {
		if(checkMinesSW() == 1) {
			return false;
		}else
			return true;
	}
	public boolean openSE() {
		if(checkMinesSE() == 1) {
			return false;
		}else
			return true;
	}
	
	
	
}

//mines[BoxX()][BoxY()] == 1 || mines[BoxX()+1][BoxY()+1] == 1 
//|| mines[BoxX()+1][BoxY()-1] == 1 
//		|| mines[BoxX()-1][BoxY()+1] == 1 
//				|| mines[BoxX()+1][BoxY()] == 1 || mines[BoxX()-1][BoxY()] == 1 
//						|| mines[BoxX()][BoxY()+1] == 1 || mines[BoxX()][BoxY()-1] == 1    


//if(mines[BoxX()-1][BoxY()-1] != 1) {
//revealed[BoxX()-1][BoxY()-1] = true; 
//revealed[BoxX()+1][BoxY()+1] = true; 
//revealed[BoxX()+1][BoxY()-1] = true; 
//revealed[BoxX()-1][BoxY()+1] = true; 
//revealed[BoxX()+1][BoxY()] = true; 
//revealed[BoxX()-1][BoxY()] = true; 
//revealed[BoxX()][BoxY()-1] = true; 
//revealed[BoxX()][BoxY()+1] = true; 
//revealed[BoxX()+1][BoxY()] = true; 
//revealed[BoxX()-1][BoxY()] = true; 
//revealed[BoxX()][BoxY()-1] = true; 
//revealed[BoxX()][BoxY()+1] = true; }



//else if(mines[BoxX()][BoxY()] == 1) {
//for(int i = 0; i < 16; i++) {
//	for(int j = 0; j < 9; j ++) {
//		revealed[i][j] = true;
//	}
//}
//}
