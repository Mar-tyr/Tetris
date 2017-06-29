import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Created by 90465 on 6/28/2017.
 */
public class GameFrame extends JFrame {
    static final int TYPENUM=5;
    static final Random random=new Random(System.currentTimeMillis());
    private Cube currentCube;
    private Fall panelThread;
    int score;
    private static int row=22;
    public static GameFrame instance;
    public static int getRow() {
        return row;
    }
    public static int getCol() {
        return col;
    }

    private static int col=15;
    private GridBagLayout layout;

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    private JLabel scoreLabel;
    private GamePanel gamePanel;
    private NextCubePanel nextCubePanel;
    private GameFrame(){
        init();
    }
    public static GameFrame getInstance(){
        if(instance==null){
            instance=new GameFrame();
            return instance;
        }else return instance;
    }
    public void init(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        score=0;
        layout=new GridBagLayout();
        setLayout(layout);
        scoreLabel=new JLabel("Score:0",JLabel.CENTER);
        gamePanel=new GamePanel(row,col);
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nextCubePanel=new NextCubePanel();
        add(scoreLabel);
        add(gamePanel);
        add(nextCubePanel);
        GridBagConstraints constraints=new GridBagConstraints();
        constraints.fill=GridBagConstraints.BOTH;
        constraints.gridwidth=3;
        constraints.gridheight=0;
        constraints.weightx=0.7;
        constraints.weighty=0;
        layout.setConstraints(scoreLabel,constraints);
        constraints.gridwidth=5;
        constraints.gridheight=0;
        constraints.weightx=2;
        constraints.weighty=1;
        layout.setConstraints(gamePanel,constraints);
        constraints.gridwidth=3;
        constraints.gridheight=0;
        constraints.weightx=1;
        constraints.weighty=0;
        layout.setConstraints(nextCubePanel,constraints);
        setSize(1000,800);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyReleased(e);
                switch (e.getKeyCode()){
                    case 40:
                        panelThread.currentIntervels=0;
                        break;
                    case 37:
                        currentCube.moveLeft(gamePanel);
                        break;
                    case 39:
                        currentCube.moveRight(gamePanel);
                        break;
                    case 38:
                        currentCube.change(gamePanel);
                        break;
                }
            }
        });
        setVisible(true);
        currentCube=new Cube(0,7,Cube.getRandomType(random.nextInt(TYPENUM)));
        panelThread = new Fall(gamePanel,currentCube,200);
        panelThread.start();
    }
    public static void main(String[]args){
        getInstance();
    }
}