import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Created by 90465 on 6/28/2017.
 */
public class GamePanel extends JPanel {
    int row, col;
    JLabel[][] cubes;
    static Color[] colors={Color.RED,Color.BLUE,Color.GREEN,Color.MAGENTA,Color.BLACK,Color.ORANGE};
    static Random random=new Random();
    Thread fallThread;
    public GamePanel(int row, int col) {
        this.row = row;
        this.col = col;
        setLayout(new GridLayout(row, col));
        cubes = new JLabel[row][col];
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                cubes[i][j] = new JLabel();
//                cubes[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cubes[i][j].setOpaque(true);
                add(cubes[i][j]);
            }
        }
    }

}
