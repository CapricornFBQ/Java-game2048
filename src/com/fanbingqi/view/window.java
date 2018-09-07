package com.fanbingqi.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* 
 * @author 范炳琦
 */

public class window extends JFrame{
	private static int score = 0;
	final Font[] fonts = {
			new Font("Helvetica Neue",Font.BOLD,48),
			new Font("Helvetica Neue",Font.BOLD,42),
			new Font("Helvetica Neue",Font.BOLD,36),
			new Font("Helvetica Neue",Font.BOLD,30),
			new Font("Helvetica Neue",Font.BOLD,24),
	};
//	private GameBoard gameBoard;
	private JLabel ltitle;
	private JLabel lsctip;
	private JLabel lscore;
	private JLabel loptip;
	
	public window() {
		this.setLayout(null);
	}
	//初始化视图
	public void initView() {
		//游戏logo
		ltitle = new JLabel("2048",JLabel.CENTER);
		ltitle.setFont(new Font("",Font.BOLD,50));
		ltitle.setForeground(new Color(0x776e65));
		ltitle.setBounds(0,0,130,60);
		//分数提示区
		lsctip = new JLabel("SCORE",JLabel.CENTER);
		lsctip.setFont(new Font("",Font.BOLD,16));
		lsctip.setForeground(new Color(0xeee4da));
		lsctip.setOpaque(true);//必须设置为true，才能显示出来
		lsctip.setBackground(new Color(0xbbada0));
		lsctip.setBounds(290,5,100,25);
		//具体的分数显示
		lscore = new JLabel("0",JLabel.CENTER);
		lscore.setFont(new Font("Helvetica Neue",Font.BOLD,20));
		lscore.setForeground(Color.white);
		lscore.setOpaque(true);
		lscore.setBackground(new Color(0xbbada0));
		lscore.setBounds(290,30,100,25);
		//操作提示区
		loptip = new JLabel("按方向键可以控制方块的移动，按ESC键重新开始游戏。",JLabel.LEFT);
		loptip.setFont(new Font("Helvetica Neue",Font.ITALIC,12));
		loptip.setForeground(new Color(0x776e65));
		loptip.setBounds(10,60,390,30);
		
		this.add(ltitle);
		this.add(lsctip);
		this.add(lscore);
		this.add(loptip);
	}
	//游戏面板
	class GameBoard extends JPanel implements KeyListener {
		private static final int GAP_TILE = 16; //瓦片的间隙
		private static final int ARC_TILE = 16; //瓦片圆角弧度
		private static final int SIZE_TILE = 80; //瓦片的大小
		
		private Tile[][] tiles = new Tile[4][4];
		private boolean isOver;
		private boolean isMove;
		//游戏面板构造器
		public GameBoard() {
			initGame();
			addKeyListener(this);
		}
		//键盘按下事件
		@Override
		public void keyPressed(KeyEvent e) {
			boolean moved = false;
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				initGame();
				break;
			case KeyEvent.VK_LEFT:
				moved = moveLeft();
				invokeCreateTile();
				break;
			}
			repaint();
		}
		//初始化游戏
		private void initGame() {
			//初始化地图
			for (int i=0; i<4; i++) {
				for (int j=0; j<4; j++) {
					tiles[i][j] = new Tile();
				}
			}
			//初始化时生成两个瓦片
			createTile();
			createTile();
			
			isMove = false;
			isOver = false;
		}
		//调用生成瓦片的方法，除了初始化之后，其他时候都是生成一个瓦片
		private void invokeCreateTile() {
			if(isMove) {
				createTile();
				isMove = false;
			}
		}
		//检查游戏是否结束
		private void checkGameOver(boolean moved) {
			lscore.setText(score + "");
			//先判断有无空白瓦片
			if(!getBlankTiles().isEmpty()) {
				return;
			}
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					//判断水平和竖直方向是否存在可以合并的两个瓦片
					if(tiles[i][j].value == tiles[i][j+1].value || tiles[i][j].value == tiles[i+1][j].value) {
						isOver = false;
						return;
					}
				}
			}
			isOver = true;
		}
		//在空白区域随机创建新的瓦片
		private void createTile() {
			//获取当前空白的瓦片，并加入列表
			List<Tile> list = getBlankTiles();
			if (!list.isEmpty()) {
				Random random = new Random();
				int index = random.nextInt(list.size());
				//随机取出一个没有内容的瓦片
				Tile tile = list.get(index);
				//初始新瓦片的值为2或者4
				tile.value = random.nextInt(100)>50?4:2;
			}
		}
		
		//获取当前空白的瓦片，加入列表返回
		private List<Tile> getBlankTiles() {
			List<Tile> list = new ArrayList<>();
			for (int i=0;i<4;i++) {
				for (int j=0;j<4;j++) {
					if(tiles[i][j].value == 0) {
						list.add(tiles[i][j]);
					}
				}
			}
			return list;
		}
		
		//向左移动
		private boolean moveLeft() {
			isMove = false;
			for (int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					int k=j;
				}
			}
		}
	}
	//瓦片对象
	class Tile {
		public int value;//显示的数字
		public boolean ismerge;//是否是合并的
		
		public Tile() {
			clear();
		}
		public void clear() {
			value = 0;
			ismerge = false;
		}
		
		public void swap(Tile tile) {
			this.value = tile.value;
			this.ismerge = tile.ismerge;
		}
		
		public Color getForeground() {
			switch (value) {
			case 0:
				return new Color(0xcdc1b4);
			case 2:
			case 4:
				return new Color(0x776e65);
			default:
				return new Color(0xf9f6f2);
			}
		}
		//根据瓦块的数值，返回具体的颜色
        public Color getBackground() {
            switch (value) {
                case 0:
                    return new Color(0xcdc1b4);
                case 2:
                    return new Color(0xeee4da);
                case 4:
                    return new Color(0xede0c8);
                case 8:
                    return new Color(0xf2b179);
                case 16:
                    return new Color(0xf59563);
                case 32:
                    return new Color(0xf67c5f);
                case 64:
                    return new Color(0xf65e3b);
                case 128:
                    return new Color(0xedcf72);
                case 256:
                    return new Color(0xedcc61);
                case 512:
                    return new Color(0xedc850);
                case 1024:
                    return new Color(0xedc53f);
                case 2048:
                    return new Color(0xedc22e);
                case 4096:
                    return new Color(0x65da92);
                case 8192:
                    return new Color(0x5abc65);
                case 16384:
                    return new Color(0x248c51);
                default:
                    return new Color(0x248c51);
            }
        }
        //根据瓦块的数值，返回字体对象
        public Font getTileFont() {
        	int index = value < 100?1:value<1000?2:value<10000?3:4;
        	return fonts[index];
        }
        
	}
	

	
}






































