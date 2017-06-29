import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by 90465 on 6/29/2017.
 */
public class Fall extends Thread{
    GamePanel panel;
    long intervals,currentIntervels;
    Cube cube;
    public Fall(GamePanel panel,Cube cube,long intervals){
        this.panel=panel;
        this.intervals=intervals;
        this.cube=cube;
    }
    public void run() {
        while(true) {
            currentIntervels=intervals;
            cube.draw(panel);
            do {
                try {
                    sleep(currentIntervels);
                } catch (InterruptedException e) {

                }
            } while (cube.fallOneStep(panel));
            Queue<JLabel[]> queue=new LinkedList<JLabel[]>();
            boolean over=false,full;
            int fullCount=0;
            for(int i=GameFrame.getRow()-1;i>=0&&!over;--i){
                over=true;
                full=true;
                for(int j=0;j<GameFrame.getCol();++j){
                    if(!over&&!full) break;
                    over=over&&(panel.cubes[i][j].getBackground().equals(Cube.defautColor));
                    full=full&&(!panel.cubes[i][j].getBackground().equals(Cube.defautColor));
                }
                if(!full)   queue.offer(panel.cubes[i]);
                else ++fullCount;
            }
            if(fullCount!=0) {
                GameFrame temp= GameFrame.getInstance();
                temp.score+=fullCount*10;
                GameFrame.getInstance().getScoreLabel().setText("Score:"+temp.score);
                int index = panel.row - 1;
                for (JLabel[] labels : queue) {
                    for (int j = 0; j < panel.col; ++j) {
                        panel.cubes[index][j].setBackground(labels[j].getBackground());
                        if(panel.cubes[index][j].getBackground().equals(Cube.defautColor))
                            panel.cubes[index][j].setBorder(null);
                    }
                    --index;
                }
                index = panel.row-queue.size()-1;
                for (int i = 0; i < fullCount; ++i) {
                    int row = index - i;
                    for (int j = 0; j < panel.col; ++j) {
                        panel.cubes[row][j].setBackground(Cube.defautColor);
                        panel.cubes[row][j].setBorder(null);
                    }
                }
            }
            if(cube.reborn(panel)==false){
                GameFrame temp=GameFrame.getInstance();
                temp.score=0;
                GameFrame.getInstance().getScoreLabel().setText("Score:"+temp.score);
                for(int i=0;i<panel.row;++i)
                    for(int j=0;j<panel.col;++j) {
                        panel.cubes[i][j].setBackground(null);
                        panel.cubes[i][j].setBorder(null);
                    }
            }
            cube.reborn(panel);
        }
    }
}