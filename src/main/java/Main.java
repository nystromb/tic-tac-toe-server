import configuration.Settings;
import handlers.GameHandler;
import main.Boards.ThreeByThreeBoard;
import main.Models.GameModel;
import main.Players.GameToken;
import main.Players.Human;
import main.Players.UnbeatablePlayer;
import router.Route;
import router.Router;
import server.HttpServer;
import views.BoardRenderer;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        Settings.parse(args);
        Settings.setUpLogger();
        addMyRoutes();
        try{
            HttpServer server = new HttpServer(Settings.PORT);

            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addMyRoutes() {
        Router.addRoute(new Route("/",
                new GameHandler(
                        new GameModel(
                                new ThreeByThreeBoard(),
                                new Human(GameToken.X),
                                new Human(GameToken.O)),
                        new BoardRenderer())));

        UnbeatablePlayer computer = new UnbeatablePlayer();
        computer.setPiece(GameToken.O);
        Router.addRoute(new Route("/unbeatable",
                new GameHandler(
                        new GameModel(
                                new ThreeByThreeBoard(),
                                new Human(GameToken.X),
                                computer),
                        new BoardRenderer())));
    }
}
