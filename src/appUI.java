import static javax.security.auth.callback.ConfirmationCallback.YES_NO_CANCEL_OPTION;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class appUI extends JPanel{
    private board game = new board();
    private int ran = game.randomize();
    private int blockSide = 30;
    private int boardLR = 190 , boardTop = 580 , boardBottom = 20;
    private int x=boardTop/9+2, y = boardTop/9+2;
    JLabel scoreLabel = new JLabel();
    private score s = new score(0);
    private static appUI instance = new appUI();
    private static Memento m;
   
    private appUI()
    {
        setBorder(BorderFactory.createEmptyBorder(boardTop, boardLR,boardBottom, boardLR));
        setBackground(new Color(0x2f2f2f));
        super.setLayout(new BorderLayout());
        setMainPanel();
        try {
            readFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static appUI getInstance() {
        if (instance == null) {
            instance = new appUI();
        }
        return instance;
    }
    
    private void setMainPanel()
    {
        int z=y*8;
        arrowButton btn1 = new arrowButton();
        add(createButtonArrow(btn1,x,z));
        arrowButton btn2 = new arrowButton();
        add(createButtonArrow(btn2,x*2,z));
        arrowButton btn3 = new arrowButton();
        add(createButtonArrow(btn3,x*3,z));
        arrowButton btn4 = new arrowButton();
        add(createButtonArrow(btn4,x*4,z));
        arrowButton btn5 = new arrowButton();
        add(createButtonArrow(btn5,x*5,z));
        JButton newGamebtn = new JButton(new ImageIcon("resources\\images\\newgame.png"));
        add(createButton(newGamebtn,x , z+y));
        newGamebtn.setBounds((x - 10) - (blockSide),(z+y-boardBottom) - blockSide,3*blockSide,blockSide+5);

        JButton undo = new JButton(new ImageIcon("resources\\images\\undo.png"));
        add(createButton(undo,x , z+y));
        undo.setBounds((x*4) - 10 - blockSide,(z+y-boardBottom) - blockSide,2*blockSide,blockSide+5);


        scoreLabel.setLayout(null);
        scoreLabel.setBounds(boardLR*2-80,boardTop-boardBottom-17,3*blockSide,blockSide+10);
        scoreLabel.setFont(new Font("SansSerif",Font.BOLD,18));
        scoreLabel.setForeground(Color.lightGray);
        add(scoreLabel);

        JLabel dummyLabel = new JLabel();
        add(dummyLabel);

        commandButtonAction(btn1,1);
        commandButtonAction(btn2,2);
        commandButtonAction(btn3,3);
        commandButtonAction(btn4,4);
        commandButtonAction(btn5,5);
        btn1.buttonlistener();
        btn2.buttonlistener();
        btn3.buttonlistener();
        btn4.buttonlistener();
        btn5.buttonlistener();

        newGamebtn.addActionListener(actionEvent -> {

            int n = JOptionPane.showConfirmDialog(null, "Incepi un joc nou?",
                    "WARNING", JOptionPane.YES_NO_OPTION);
            if(n!=YES_NO_CANCEL_OPTION) {
                game.zeroBoard();
                s.setNull();
                repaint();
            }
        });

        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	game.undo();
                repaint();
            }
        });
    }
	 private void commandButtonAction(arrowButton button,int number)
	    {
	        button.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                pushBlock(number);
	                m = game.createMemento();
	                repaint();
	            }
	        });
	    }
    private void pushBlock(int choice)
    {
        if (game.isBoardFull(ran)) {
            JOptionPane.showMessageDialog(null, "GAME OVER!");
            game.zeroBoard();
            m=game.createMemento();
            s.value=0;
        } else if (game.isColumnEmpty(choice,ran)) {
            game.push(ran, choice);
            m = game.createMemento();
            game.fullChecker(choice);
            s.updatePlus(ran);
            ran = game.randomize();
        } else if (!game.isColumnEmpty(choice,ran)) {
        	JOptionPane.showMessageDialog(null, "GAME OVER!");
            game.zeroBoard();
            s.setNull();
        }
    }
    private JButton createButton(JButton button,int x, int y) {
        button.setFocusPainted(false);
        button.setLayout(null);
        button.setBounds((x - 10) - blockSide,(y-boardBottom) - blockSide+5,2*blockSide,blockSide+10);
        button.setBackground(new Color(217, 235, 203));
        button.setContentAreaFilled(false);
        return button;
    }
    private arrowButton createButtonArrow(arrowButton button,int x, int y) {
       	button.setFocusPainted(false);
       	button.setLayout(null);
       	button.setBounds((x - 10) - blockSide,(y-boardBottom) - blockSide+5,2*blockSide,blockSide+10);
       	button.setBackground(new Color(217, 235, 203));
       	button.setContentAreaFilled(false);
        return button;
    }
    //Drawer
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        DrawBlock(g ,ran,x*3, boardTop);
        scoreLabel.setText(String.valueOf(s.value));
        int[][] board = game.getBoard();
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 5; j++) {
                if (board[i - 1][j - 1] != 0) {
                    DrawBlock(g, board[i - 1][j - 1],x * j, y * i);
                }
            }
        }
        try {
            printToFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void printToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("resources\\images\\data.txt"));
        int[][] board = game.getBoard();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                writer.write(board[i][j] + "\t\t");
            }
            writer.write("\n");
        }
        writer.write("\n"+s.value);
        writer.close();
    }
    private void readFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources\\images\\data.txt"));
        int[][] board = game.getBoard();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                String val = scanner.next();
                board[i][j] = (Integer.parseInt(val));
            }
        }
        String val = scanner.next();
        s.value = (Integer.parseInt(val));
    }
    private void DrawBlock(Graphics2D graphics2D ,int value, int x , int y)
    {
    	if(value==2) {
    		graphics2D.setColor(new Color(0xdd62c1));
    	}
    	else if (value==4) {
    		graphics2D.setColor(new Color(0x6bd852));	
    	}
    	else if (value==8) {
    		graphics2D.setColor(new Color(0x44d0d0));	
    	}
    	else if (value==16) {
    		graphics2D.setColor(new Color(0x3d8fdb));	
    	}
    	else if (value==32) {
    		graphics2D.setColor(new Color(0xe96c54));	
    	}
    	else if (value==64) {
    		graphics2D.setColor(new Color(0x9786fe));	
    	}
    	else if (value==128) {
    		graphics2D.setColor(new Color(0xdd62c1));	
    	}
    	else if (value==256) {
    		graphics2D.setColor(new Color(0xfbb441));	
    	}
    	else if (value==512) {
    		graphics2D.setColor(new Color(0xf6607c));	
    	}
    	else if (value==1024) {
    		graphics2D.setColor(new Color(0xa95296));	
    	}
    	else if (value==2048) {
    		graphics2D.setColor(new Color(0x6ad952));	
    	}
    	else if (value==4096) {
    		graphics2D.setColor(new Color(0xdd62c1));	
    	}
    	else {
    		graphics2D.setColor(Color.black);	
    	}
        graphics2D.fillRoundRect((x - 10) - blockSide,(y-boardBottom) - blockSide,2*blockSide,2*blockSide,17,17);
        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font("SansSerif", Font.BOLD, 20));
        FontMetrics fm = graphics2D.getFontMetrics();
        double t_width = fm.getStringBounds(String.valueOf(value), graphics2D).getWidth();
        graphics2D.drawString(String.valueOf(value),(int) ((x - 10) - t_width / 2),((y-boardBottom) + fm.getMaxAscent() / 2));
    }
}