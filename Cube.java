import javax.swing.*;
import java.awt.*;

/**
 * Created by 90465 on 6/29/2017.
 */
public class Cube {
    int type;
    int[] X=new int[4],Y=new int[4];
    static final Color[]  colors={null,Color.RED,Color.RED,Color.CYAN,Color.MAGENTA,Color.MAGENTA,Color.ORANGE,Color.ORANGE,Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN};
    static final Color defautColor=new Color(238,238,238);
    public Cube(int firstX,int firstY,int type){
        X[0]=firstX;
        Y[0]=firstY;
        this.type=type;
        switch (type){
            case 1:                             //  *
                X[1]=firstX+1;                  //  *
                X[2]=firstX+2;                  //  *
                X[3]=firstX+3;                  //  *
                Y[1]=firstY;
                Y[2]=firstY;
                Y[3]=firstY;
                break;
            case 2:                             //  ****
                X[1]=firstX;
                X[2]=firstX;
                X[3]=firstX;
                Y[1]=firstY+1;
                Y[2]=firstY+2;
                Y[3]=firstY+3;
                break;
            case 3:                             //  **
                X[1]=firstX;                    //  **
                X[2]=firstX+1;
                X[3]=firstX+1;
                Y[1]=firstY+1;
                Y[2]=firstY;
                Y[3]=firstY+1;
                break;
            case 4:                             //  *
                X[1]=firstX+1;                  //  **
                X[2]=firstX+1;                  //   *
                X[3]=firstX+2;
                Y[1]=firstY;
                Y[2]=firstY+1;
                Y[3]=firstY+1;
                break;
            case 5:                             //   **
                X[1]=firstX;                    //  **
                X[2]=firstX+1;
                X[3]=firstX+1;
                Y[1]=firstY+1;
                Y[2]=firstY-1;
                Y[3]=firstY;
                break;
            case 6:                             //  *
                X[1]=firstX+1;                  // **
                X[2]=firstX+1;                  // *
                X[3]=firstX+2;
                Y[1]=firstY-1;
                Y[2]=firstY;
                Y[3]=firstY-1;
                break;
            case 7:                             //  **
                X[1]=firstX;                    //   **
                X[2]=firstX+1;
                X[3]=firstX+1;
                Y[1]=firstY+1;
                Y[2]=firstY+1;
                Y[3]=firstY+2;
                break;
            case 8:                             //  *
                X[1]=firstX+1;                  //  **
                X[2]=firstX+1;                  //  *
                X[3]=firstX+2;
                Y[1]=firstY;
                Y[2]=firstY+1;
                Y[3]=firstY;
                break;
            case 9:                             //  ***
                X[1]=firstX;                    //   *
                X[2]=firstX;
                X[3]=firstX+1;
                Y[1]=firstY+1;
                Y[2]=firstY+2;
                Y[3]=firstY+1;
                break;
            case 10:                            //  *
                X[1]=firstX+1;                  // **
                X[2]=firstX+1;                  //  *
                X[3]=firstX+2;
                Y[1]=firstY-1;
                Y[2]=firstY;
                Y[3]=firstY;
                break;
            case 11:                            //  *
                X[1]=firstX+1;                  // ***
                X[2]=firstX+1;
                X[3]=firstX+1;
                Y[1]=firstY-1;
                Y[2]=firstY;
                Y[3]=firstY+1;
                break;
        }
    }
    public void erase(GamePanel panel){
        for(int i=0;i<4;++i){
            panel.cubes[X[i]][Y[i]].setBackground(null);
            panel.cubes[X[i]][Y[i]].setBorder(null);
        }
    }
    public void draw(GamePanel panel){
        for(int i=0;i<4;++i){
            panel.cubes[X[i]][Y[i]].setBackground(colors[type]);
            panel.cubes[X[i]][Y[i]].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }
    public boolean fallOneStep(GamePanel panel) {
        boolean valid = true;
        Out:
        for (int i = 0; i < 4; ++i) {
            if (X[i] + 1 >= panel.row)
                return false;
            for(int j=i+1;j<4;++j)
                if(X[i]+1==X[j]&&Y[i]==Y[j])
                    continue Out;
            if(!panel.cubes[X[i]+1][Y[i]].getBackground().equals(defautColor))
                return false;
        }
        erase(panel);
        for(int i=0;i<4;++i)    ++X[i];
        draw(panel);
        return true;
    }
    public boolean moveLeft(GamePanel panel){
        Out:
        for(int i=0;i<4;++i){
            if(Y[i]-1<0) {return false;}
            for(int j=0;j<i;++j){
                if(Y[i]-1==Y[j])    continue Out;
            }
            if(!panel.cubes[X[i]][Y[i]-1].getBackground().equals(defautColor)) {return false;}
        }
        erase(panel);
        for(int i=0;i<4;++i)    --Y[i];
        draw(panel);
        return true;
    }
    public boolean moveRight(GamePanel panel){
        Out:
        for(int i=0;i<4;++i){
            if(Y[i]+1>=GameFrame.getCol()) return false;
            for(int j=i+1;j<4;++j){
                if(Y[i]+1==Y[j])    continue Out;
            }
            if(!panel.cubes[X[i]][Y[i]+1].getBackground().equals(defautColor))  return false;
        }
        erase(panel);
        for(int i=0;i<4;++i)    ++Y[i];
        draw(panel);
        return true;
    }
    public boolean reborn(GamePanel panel){
        Cube temp=new Cube(0,7,getRandomType(GameFrame.random.nextInt(GameFrame.TYPENUM)));
        for(int i=0;i<4;++i){
            if(!panel.cubes[temp.X[i]][temp.Y[i]].getBackground().equals(defautColor))
                return false;
        }
        for(int i=0;i<4;++i){
            X[i]=temp.X[i];
            Y[i]=temp.Y[i];
        }
        type=temp.type;
        return true;
    }
    public boolean change(GamePanel panel){
        Cube next=getNextCube();
        Out:
        for(int i=0;i<4;++i){
            if(next.X[i]<0||next.X[i]>=GameFrame.getRow()||next.Y[i]<0||next.Y[i]>=GameFrame.getCol())
                return false;
            for(int j=0;j<4;++j)
                if(next.X[i]==X[j])
                    continue Out;
            if(!panel.cubes[next.X[i]][next.Y[i]].getBackground().equals(defautColor))
                return false;
        }
        erase(panel);
        for(int i=0;i<4;++i) {
            X[i] = next.X[i];
            Y[i] = next.Y[i];
        }
        type=next.type;
        draw(panel);
        return true;
    }
    public static int getRandomType(int shapeType){
        switch (shapeType){
            case 0:
                return GameFrame.random.nextInt(2)+1;
            case 1:
                return 3;
            case 2:
                return GameFrame.random.nextInt(2)+4;
            case 3:
                return GameFrame.random.nextInt(2)+6;
            case 4:
                return GameFrame.random.nextInt(4)+8;
        }
        return 1;
    }
    public Cube getNextCube(){
        switch (type){
            case 1:
            case 2:
                return new Cube(X[0],Y[0],3-type);
            case 3:
                return new Cube(X[0],Y[0],type);
            case 4:
            case 5:
                return new Cube(X[0],Y[0],9-type);
            case 6:
            case 7:
                return new Cube(X[0],Y[0],13-type);
            case 8:
            case 9:
            case 10:
                return new Cube(X[0],Y[0],type+1);
            case 11:
                return new Cube(X[0],Y[0],8);
        }
        return null;
    }
}
