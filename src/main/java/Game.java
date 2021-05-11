import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class Game extends JFrame implements KeyListener {

  private final int width;
  private final int height;
  private final int obstacleLevel;

  private final Timer timer;
  private JLabel spaceShip;
  private final JLabel[][] obstacleMatrix;
  private final boolean[][] obstacleShowStatus;
  private int obstacleRowGeneratedCount;
  private int score;

  public Game(int width, int height, int speed, int obstacleLevel) {

    this.width = width;
    this.height = height;
    this.obstacleLevel = obstacleLevel;

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(width, height);
    this.setLayout(null);
    this.addKeyListener(this);
    this.setVisible(true);
    this.getContentPane().setBackground(Color.black);
    this.setVisible(true);

    obstacleMatrix = new JLabel[width/10][height/10];
    obstacleShowStatus = new boolean[width/10][height/10];
    initObstacle();
    initSpaceShip();
    generateFirstRowOfObstacle();
    obstacleRowGeneratedCount = 0;
    score = 0;


    timer = new Timer((10 - speed) * 100, e -> {
      updateObstacle();
      refresh();
      try {
        checkCollision();
      } catch (InterruptedException interruptedException) {
        interruptedException.printStackTrace();
      }
    });
  }

  public void run () {
    timer.start();
  }

  private void refresh(){
    this.repaint();
  }

  private void initSpaceShip(){
    spaceShip = new JLabel();
    spaceShip.setBounds(width / 2, height - 40,10,10);
    spaceShip.setBackground(Color.red);
    spaceShip.setOpaque(true);
    this.add(spaceShip);
  }

  private void initObstacle(){
    for(int i = 0; i < obstacleMatrix.length; i++){
      for(int j = 0; j < obstacleMatrix[0].length; j++){
        obstacleMatrix[i][j] = new JLabel();
        obstacleMatrix[i][j].setBounds(j * 10, i * 10 + 3,10,2);
        obstacleMatrix[i][j].setBackground(Color.white);
        obstacleMatrix[i][j].setOpaque(true);
        obstacleMatrix[i][j].setVisible(obstacleShowStatus[i][j]);
        this.add(obstacleMatrix[i][j]);
      }
    }
  }

  private void updateObstacle(){
    for(int i = obstacleMatrix.length - 1; i >= 1; i--){
      copyPreRow(i);
    }
    generateFirstRowOfObstacle();
  }

  private void generateFirstRowOfObstacle(){
    for(int i = 0; i < obstacleShowStatus[0].length; i++){
      obstacleShowStatus[0][i] = isShow();
      obstacleMatrix[0][i].setVisible(obstacleShowStatus[0][i]);
    }
    if(++obstacleRowGeneratedCount > spaceShip.getY() / 10)
      score++;
  }

  private void copyPreRow(int index){
    for(int i = 0; i < obstacleShowStatus[index].length; i++){
      obstacleShowStatus[index][i] = obstacleShowStatus[index-1][i];
      obstacleMatrix[index][i].setVisible(obstacleShowStatus[index][i]);
    }
  }

  private boolean isShow(){
    Random rn = new Random();
    return rn.nextInt(10 - obstacleLevel) + 1 == 1;
  }

  private void checkCollision() throws InterruptedException {
    if(obstacleShowStatus[spaceShip.getY() / 10][spaceShip.getX() / 10]){
      this.getContentPane().removeAll();

//      isPlaying = false;
      timer.stop();
      JLabel ending = new JLabel("GAME OVER SCORE: " + score, SwingConstants.CENTER);
      ending.setBounds(0, 0,width,height);
      ending.setBackground(Color.black);
      ending.setForeground(Color.red);
      ending.setOpaque(true);
      this.add(ending);
      this.repaint();
    }
  }


  @Override
  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()){
      case 'a':
        if(spaceShip.getX()-10 >= 0)
          spaceShip.setLocation(spaceShip.getX()-10, spaceShip.getY());
        break;
      case 'd':
        if(spaceShip.getX() < width - 10)
          spaceShip.setLocation(spaceShip.getX()+10, spaceShip.getY());
        break;
    }

    try {
      checkCollision();
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }
}
