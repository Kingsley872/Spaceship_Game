public class GameStarter {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Space Game");

    int width = Integer.parseInt(args[0]);
    int height = Integer.parseInt(args[1]);
    int speed = Integer.parseInt(args[2]);
    int obstacleLevel = Integer.parseInt(args[3]);

    Game game = new Game(width, height, speed, obstacleLevel);
    game.run();
  }
}
