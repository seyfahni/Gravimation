package de.seyfarth.gravimation;

import de.seyfarth.math.Point;
import de.seyfarth.math.Vector;
import java.math.BigDecimal;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {

    @Override
    public void start(Stage window) throws Exception {

        Group root = new Group();

        Canvas canvas = new Canvas(700, 500);

        root.getChildren().add(canvas);

        window.setTitle("Gravitation Simulation");
        window.setScene(new Scene(root));
        window.show();

        World world =
            //sunPlanetMoon();
            //hyperbelSwingBy();
            //normalOrbit();
            //acceleratedOrbit();
            //doubleNormalOrbit();
            //twoSunsOverlaping();
            //twoSunsNice();
            //twoSunsPlanets();
            workInProgress();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        ScheduledService<Void> service = new ScheduledService<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        world.getBodies().forEach(body -> {
                            gc.setFill(body.getOldColor());
                            gc.fillOval(
                                body.getOldLocation().getValue(0).doubleValue() + canvas.getWidth() / 2 - body.getSize() / 2,
                                body.getOldLocation().getValue(1).doubleValue() + canvas.getHeight() / 2 - body.getSize() / 2,
                                body.getSize(),
                                body.getSize());
                            gc.setFill(body.getColor());
                            gc.fillOval(
                                body.getLocation().getValue(0).doubleValue() + canvas.getWidth() / 2 - body.getSize() / 2,
                                body.getLocation().getValue(1).doubleValue() + canvas.getHeight() / 2 - body.getSize() / 2,
                                body.getSize(),
                                body.getSize());
                        });
                        world.calculateGravitationStep(new BigDecimal("0.00001"));
                        return null;
                    }
                };
            }
        };
        service.setDelay(Duration.millis(100));
        service.setRestartOnFailure(true);
        service.start();
    }

    private World sunPlanetMoon() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
            new BigDecimal("10000000"),
            new Point(new BigDecimal("0"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("0")))
            .setColor(Color.ORANGERED)
            .setOldColor(Color.ORANGE)
            .setSize(40)
        );
        world.addBody(new Body(
            new BigDecimal("10000"),
            new Point(new BigDecimal("150"), new BigDecimal("0")),
            new Vector(new BigDecimal("3"), new BigDecimal("-6000")))
            .setColor(Color.LIGHTBLUE.darker())
            .setOldColor(Color.LIGHTBLUE.brighter())
        );
        world.addBody(new Body(
            new BigDecimal("0.000001"),
            new Point(new BigDecimal("153"), new BigDecimal("-5")),
            new Vector(new BigDecimal("400"), new BigDecimal("-6050")))
            .setColor(Color.BLACK)
            .setOldColor(Color.GRAY.brighter())
            .setSize(4)
        );
        return world;
    }

    private World normalOrbit() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
            new BigDecimal("10000000"),
            new Point(new BigDecimal("0"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("0")))
            .setColor(Color.ORANGERED)
            .setOldColor(Color.ORANGERED)
            .setSize(40)
        );
        world.addBody(new Body(
            new BigDecimal("10002"),
            new Point(new BigDecimal("151"), new BigDecimal("1")),
            new Vector(new BigDecimal("5"), new BigDecimal("-6013")))
            .setColor(Color.LIGHTSKYBLUE.darker())
            .setOldColor(Color.LIGHTBLUE.brighter())
        );
        return world;
    }

    private World hyperbelSwingBy() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
            new BigDecimal("400000000"),
            new Point(new BigDecimal("0"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("0")))
            .setColor(Color.ORANGERED)
            .setOldColor(Color.ORANGERED)
            .setSize(30)
        );
        world.addBody(new Body(
            new BigDecimal("0.01"),
            new Point(new BigDecimal("53"), new BigDecimal("290")),
            new Vector(new BigDecimal("-400"), new BigDecimal("-70000")))
            .setColor(Color.BLACK)
            .setOldColor(Color.GRAY)
        );
        return world;
    }
    
    private World acceleratedOrbit() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
            new BigDecimal("400000000"),
            new Point(new BigDecimal("200"), new BigDecimal("-100")),
            new Vector(new BigDecimal("0"), new BigDecimal("0")))
            .setColor(Color.ORANGERED)
            .setOldColor(Color.ORANGERED)
            .setSize(30)
        );
        world.addBody(new Body(
            new BigDecimal("0.01"),
            new Point(new BigDecimal("253"), new BigDecimal("-75")),
            new Vector(new BigDecimal("-400"), new BigDecimal("-78000")))
            .setColor(Color.DARKGREEN)
            .setOldColor(Color.LIGHTGREEN)
        );
        return world;
    }
    
    private World doubleNormalOrbit() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
            new BigDecimal("400000038"),
            new Point(new BigDecimal("0"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("0")))
            .setColor(Color.ORANGERED)
            .setOldColor(Color.ORANGERED)
            .setSize(30)
        );
        world.addBody(new Body(
            new BigDecimal("0.01"),
            new Point(new BigDecimal("53"), new BigDecimal("25")),
            new Vector(new BigDecimal("20000"), new BigDecimal("-50000")))
            .setColor(Color.DARKGREEN)
            .setOldColor(Color.LIGHTGREEN)
            .setSize(5)
        );
        world.addBody(new Body(
            new BigDecimal("0.03"),
            new Point(new BigDecimal("122"), new BigDecimal("-24")),
            new Vector(new BigDecimal("-8007"), new BigDecimal("-40002")))
            .setColor(Color.BROWN)
            .setOldColor(Color.BURLYWOOD)
            .setSize(10)
        );
        return world;
    }
    
    private World twoSunsOverlaping() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
            new BigDecimal("400000038"),
            new Point(new BigDecimal("-100"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("25000")))
            .setColor(Color.ORANGERED)
            .setOldColor(Color.ORANGE)
            .setSize(30)
        );
        world.addBody(new Body(
            new BigDecimal("400000038"),
            new Point(new BigDecimal("100"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("-25000")))
            .setColor(Color.LIGHTSKYBLUE)
            .setOldColor(Color.LAVENDER)
            .setSize(30)
        );
        return world;
    }
    
    private World twoSunsNice() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
            new BigDecimal("600000000"),
            new Point(new BigDecimal("-40"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("20000")))
            .setColor(Color.ORANGERED)
            .setOldColor(Color.ORANGE)
            .setSize(35)
        );
        world.addBody(new Body(
            new BigDecimal("300000000"),
            new Point(new BigDecimal("100"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("-40000")))
            .setColor(Color.LIGHTSKYBLUE)
            .setOldColor(Color.LAVENDER)
            .setSize(25)
        );
        return world;
    }
    
    private World twoSunsPlanets() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
            new BigDecimal("600000000"),
            new Point(new BigDecimal("-40"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("20000")))
            .setColor(Color.ORANGERED)
            .setOldColor(Color.ORANGE)
            .setSize(35)
        );
        world.addBody(new Body(
            new BigDecimal("300000000"),
            new Point(new BigDecimal("100"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("-40000")))
            .setColor(Color.LIGHTSKYBLUE)
            .setOldColor(Color.LAVENDER)
            .setSize(25)
        );
        world.addBody(new Body(
            new BigDecimal("0.01"),
            new Point(new BigDecimal("25"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("-50000")))
            .setColor(Color.DARKGREEN)
            .setOldColor(Color.LIGHTGREEN)
            .setSize(5)
        );
        world.addBody(new Body(
            new BigDecimal("0.03"),
            new Point(new BigDecimal("222"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("50000")))
            .setColor(Color.BROWN)
            .setOldColor(Color.BURLYWOOD)
            .setSize(10)
        );
        return world;
    }
    
    private World workInProgress() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
            new BigDecimal("600000000"),
            new Point(new BigDecimal("-40"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("20000")))
            .setColor(Color.ORANGERED)
            .setOldColor(Color.ORANGE)
            .setSize(35)
        );
        world.addBody(new Body(
            new BigDecimal("300000000"),
            new Point(new BigDecimal("100"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("-40000")))
            .setColor(Color.LIGHTSKYBLUE)
            .setOldColor(Color.LAVENDER)
            .setSize(25)
        );
        world.addBody(new Body(
            new BigDecimal("0.01"),
            new Point(new BigDecimal("25"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("-50000")))
            .setColor(Color.DARKGREEN)
            .setOldColor(Color.LIGHTGREEN)
            .setSize(5)
        );
        world.addBody(new Body(
            new BigDecimal("0.03"),
            new Point(new BigDecimal("222"), new BigDecimal("0")),
            new Vector(new BigDecimal("0"), new BigDecimal("50000")))
            .setColor(Color.BROWN)
            .setOldColor(Color.BURLYWOOD)
            .setSize(10)
        );
        return world;
    }
    
//<editor-fold defaultstate="collapsed" desc="unused">
    private void startOld(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as
     * fallback in case the application can not be launched through deployment artifacts, e.g., in
     * IDEs with limited FX support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
//</editor-fold>

}
