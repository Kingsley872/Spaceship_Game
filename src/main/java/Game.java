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

    // game attributes and settings
    this.width = width;
    this.height = height;
    this.obstacleLevel = obstacleLevel;

    // setup environment for game play
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(width, height);
    this.setLayout(null);
    this.addKeyListener(this);
    this.setVisible(true);
    this.getContentPane().setBackground(Color.black);
    this.setVisible(true);

    // init all the components for game play
    // the ratio between and window size and game objects size are 1:10
    // for example window size is 500, there are 50 obstacles in a row, each obstacle is size 10*10
    obstacleMatrix = new JLabel[width/10][height/10];
    obstacleShowStatus = new boolean[width/10][height/10];
    initObstacle();
    initSpaceShip();
    generateFirstRowOfObstacle();
    obstacleRowGeneratedCount = 0;
    score = 0;

    // setup timer for game frame update and obstacle logic
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

  // run game
  public void run () {
    timer.start();
  }

  // refresh the frame
  private void refresh(){
    this.repaint();
  }

  // create a spaceship on the frame
  private void initSpaceShip(){
    spaceShip = new JLabel();
    // -40 is for adjustment for spaceship, otherwise it will be out of the window
    spaceShip.setBounds(width / 2, height - 40,10,10);
    spaceShip.setBackground(Color.red);
    spaceShip.setOpaque(true);
    this.add(spaceShip);
  }

  // add all the obstacles on the frame and set all invisible
  private void initObstacle(){
    for(int i = 0; i < obstacleMatrix.length; i++){
      for(int j = 0; j < obstacleMatrix[0].length; j++){
        obstacleMatrix[i][j] = new JLabel();
        // +3 is a adjustment for obstacle, which make the obstacle in the middle of the 10*10 space
        obstacleMatrix[i][j].setBounds(j * 10, i * 10 + 3,10,2);
        obstacleMatrix[i][j].setBackground(Color.white);
        obstacleMatrix[i][j].setOpaque(true);
        obstacleMatrix[i][j].setVisible(obstacleShowStatus[i][j]);
        this.add(obstacleMatrix[i][j]);
      }
    }
  }

  // from button to the top, current row copy status from pre row and generate new row in on the top
  private void updateObstacle(){
    for(int i = obstacleMatrix.length - 1; i >= 1; i--){
      copyPreRow(i);
    }
    generateFirstRowOfObstacle();
  }

  // randomly show obstacle on the top of the frame
  private void generateFirstRowOfObstacle(){
    for(int i = 0; i < obstacleShowStatus[0].length; i++){
      obstacleShowStatus[0][i] = isShow();
      obstacleMatrix[0][i].setVisible(obstacleShowStatus[0][i]);
    }
    if(++obstacleRowGeneratedCount > spaceShip.getY() / 10)
      score++;
  }

  // helper function for copy pre row status
  private void copyPreRow(int index){
    for(int i = 0; i < obstacleShowStatus[index].length; i++){
      obstacleShowStatus[index][i] = obstacleShowStatus[index-1][i];
      obstacleMatrix[index][i].setVisible(obstacleShowStatus[index][i]);
    }
  }

  // helper function to show current obstacle should visible or not randomly
  private boolean isShow(){
    Random rn = new Random();
    return rn.nextInt(11 - obstacleLevel) + 1 == 1;
  }

  // checking collision between obstacle and spaceship by checking
  private void checkCollision() throws InterruptedException {
    // Game is over
    if(obstacleShowStatus[spaceShip.getY() / 10][spaceShip.getX() / 10]){
      // remove all the component on the window
      this.getContentPane().removeAll();
      // stop timer for refreshing
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


  // keyboard detection
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
