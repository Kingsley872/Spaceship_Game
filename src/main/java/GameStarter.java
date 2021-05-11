public class GameStarter {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Space Game");
    /*
    * width and height are the attributes of the window (game environment)
    * speed: obstacle falling speed  (from 1 - 9) (1 is slow, 9 is fast);
    * obstacleLevel: how many obstacles for each row (from 1 - 9) (1 is less, 9 is more)
    * */

    int width = Integer.parseInt(args[0]);
    int height = Integer.parseInt(args[1]);
    int speed = Integer.parseInt(args[2]);
    int obstacleLevel = Integer.parseInt(args[3]);

    Game game = new Game(width, height, speed, obstacleLevel);
    game.run();
  }
}
