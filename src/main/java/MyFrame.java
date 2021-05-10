import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class MyFrame extends JFrame implements KeyListener {

  private JLabel spaceShip;
  private JLabel[][] matrix;
  private boolean[][] labelShow;
  private boolean isPlaying;
  private int count;

  public MyFrame() {

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(500, 500);
    this.setLayout(null);
    this.addKeyListener(this);
    this.setVisible(true);
    this.getContentPane().setBackground(Color.black);
    this.setVisible(true);

    matrix = new JLabel[50][50];
    labelShow = new boolean[50][50];
    initObstacle();
    initSpaceShip();
    generateFirstRowOfObstacle();
    isPlaying = false;
    count = 0;
  }

  public void run () throws InterruptedException {
    isPlaying = true;
    while(isPlaying){
      Thread.sleep(500);
      updateObstacle();
      this.repaint();
      checkCollision();
    }
  }

  private void initSpaceShip(){
    spaceShip = new JLabel();
    spaceShip.setBounds(250, 450,10,10);
    spaceShip.setBackground(Color.red);
    spaceShip.setOpaque(true);
    this.add(spaceShip);
  }

  private void initObstacle(){
    for(int i = 0; i < matrix.length; i++){
      for(int j = 0; j < matrix[0].length; j++){
        matrix[i][j] = new JLabel();
        matrix[i][j].setBounds(j * 10, i * 10 + 3,10,2);
        matrix[i][j].setBackground(Color.white);
        matrix[i][j].setOpaque(true);
        matrix[i][j].setVisible(labelShow[i][j]);
        this.add(matrix[i][j]);
      }
    }
  }

  private void updateObstacle(){
    for(int i = matrix.length - 1; i >= 1; i--){
      copyPreRow(i);
    }
    generateFirstRowOfObstacle();
  }

  private void generateFirstRowOfObstacle(){
    for(int i = 0; i < labelShow[0].length; i++){
      labelShow[0][i] = isShow();
      matrix[0][i].setVisible(labelShow[0][i]);
    }
    count++;
  }

  private void copyPreRow(int index){
    for(int i = 0; i < labelShow[index].length; i++){
      labelShow[index][i] = labelShow[index-1][i];
      matrix[index][i].setVisible(labelShow[index][i]);
    }
  }

  private boolean isShow(){
    Random rn = new Random();
    return rn.nextInt(8) + 1 == 1;
  }

  private void checkCollision() {
    if(labelShow[spaceShip.getY() / 10][spaceShip.getX() / 10]){
      isPlaying = false;
      this.getContentPane().removeAll();

      JLabel ending = new JLabel("GAME OVER " + count, SwingConstants.CENTER);
      ending.setBounds(0, 0,500,500);
      ending.setBackground(Color.black);
      ending.setForeground(Color.red);
      ending.setOpaque(true);
      this.add(ending);

    }
  }


  @Override
  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()){
      case 'a':
        if(spaceShip.getX()-10 >= 0)
          spaceShip.setLocation(spaceShip.getX()-10, spaceShip.getY());
        break;
      case 'w':
        if(spaceShip.getY() - 10 >= 0)
          spaceShip.setLocation(spaceShip.getX(), spaceShip.getY()-10);
        break;
      case 's':
        if(spaceShip.getY() <= 490)
          spaceShip.setLocation(spaceShip.getX(), spaceShip.getY()+10);
        break;
      case 'd':
        if(spaceShip.getX() <= 490)
          spaceShip.setLocation(spaceShip.getX()+10, spaceShip.getY());
        break;
    }

    checkCollision();
  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {
    System.out.println(spaceShip.getX() / 10 + " " + spaceShip.getY() / 10);
  }
}
