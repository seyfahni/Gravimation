package de.seyfarth.gravimation;

import de.seyfarth.math.Point;
import de.seyfarth.math.Vector;

import java.math.BigDecimal;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

@SuppressWarnings("PMD.UnusedPrivateMethod")
public class MainApp extends Application {

    @Override
    public void start(Stage window) {

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
                //chaos();
                //withoutSwingBy();
                //threeBodiesSwingBy();
                workInProgress();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        ScheduledService<Void> service = new ScheduledService<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
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
        World world = new World(new BigDecimal("10000"));
        world.addBody(new Body( // Sun
                new BigDecimal("1728000"),
                new Point(BigDecimal.ZERO, BigDecimal.ZERO),
                new Vector(new BigDecimal("-4"), new BigDecimal("15")))
                .setColor(Color.LIGHTSKYBLUE)
                .setOldColor(Color.LAVENDER)
        );
        world.addBody(new Body( // Planet
                new BigDecimal("10648"),
                new Point(new BigDecimal("100"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-12750")))
                .setColor(Color.DARKGREEN)
                .setOldColor(Color.LIGHTGREEN)
        );
        world.addBody(new Body( // Moon
                new BigDecimal("27"),
                new Point(new BigDecimal("108.5"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-9000")))
                .setColor(Color.RED)
                .setOldColor(Color.LIGHTGREEN)
        );
        return world;
    }

    private World normalOrbit() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body(
                new BigDecimal("10000000"),
                new Point(BigDecimal.ZERO, BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, BigDecimal.ZERO))
                .setColor(Color.ORANGERED)
                .setOldColor(Color.ORANGERED)
                .setSize(40)
        );
        world.addBody(new Body(
                new BigDecimal("10002"),
                new Point(new BigDecimal("151"), BigDecimal.ONE),
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
                new Point(BigDecimal.ZERO, BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, BigDecimal.ZERO))
                .setColor(Color.ORANGERED)
                .setOldColor(Color.ORANGERED)
                .setSize(30)
        );
        world.addBody(new Body(
                new BigDecimal("27"),
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
                new Vector(BigDecimal.ZERO, BigDecimal.ZERO))
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
                new Point(BigDecimal.ZERO, BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, BigDecimal.ZERO))
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
                new Point(new BigDecimal("-100"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("25000")))
                .setColor(Color.ORANGERED)
                .setOldColor(Color.ORANGE)
                .setSize(30)
        );
        world.addBody(new Body(
                new BigDecimal("400000038"),
                new Point(new BigDecimal("100"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-25000")))
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
                new Point(new BigDecimal("-40"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("20000")))
                .setColor(Color.ORANGERED)
                .setOldColor(Color.ORANGE)
                .setSize(35)
        );
        world.addBody(new Body(
                new BigDecimal("300000000"),
                new Point(new BigDecimal("100"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-40000")))
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
                new Point(new BigDecimal("-40"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("20000")))
                .setColor(Color.ORANGERED)
                .setOldColor(Color.ORANGE)
                .setSize(35)
        );
        world.addBody(new Body(
                new BigDecimal("300000000"),
                new Point(new BigDecimal("100"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-40000")))
                .setColor(Color.LIGHTSKYBLUE)
                .setOldColor(Color.LAVENDER)
                .setSize(25)
        );
        world.addBody(new Body(
                new BigDecimal("0.2"),
                new Point(new BigDecimal("123"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-120000")))
                .setColor(Color.DARKMAGENTA)
                .setOldColor(Color.CRIMSON)
                .setSize(7)
        );
        world.addBody(new Body(
                new BigDecimal("0.03"),
                new Point(new BigDecimal("222"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("50000")))
                .setColor(Color.BROWN)
                .setOldColor(Color.BURLYWOOD)
                .setSize(10)
        );
        return world;
    }

    private World chaos() {
        World world = new World(new BigDecimal("500"));
        world.addBody(new Body( // stabil
                new BigDecimal("600000000"),
                new Point(new BigDecimal("-40"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("20000")))
                .setColor(Color.ORANGERED)
                .setOldColor(Color.ORANGE)
                .setSize(35)
        );
        world.addBody(new Body( // stabil
                new BigDecimal("300000000"),
                new Point(new BigDecimal("100"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-40000")))
                .setColor(Color.LIGHTSKYBLUE)
                .setOldColor(Color.LAVENDER)
                .setSize(25)
        );
        world.addBody(new Body( // stabil
                new BigDecimal("0.2"),
                new Point(new BigDecimal("123"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-120000")))
                .setColor(Color.DARKMAGENTA)
                .setOldColor(Color.CRIMSON)
                .setSize(7)
        );
        world.addBody(new Body( // instabil
                new BigDecimal("0.01"),
                new Point(new BigDecimal("25"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-50000")))
                .setColor(Color.DARKGREEN)
                .setOldColor(Color.LIGHTGREEN)
                .setSize(5)
        );
        world.addBody(new Body( // stabil
                new BigDecimal("0.03"),
                new Point(new BigDecimal("222"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("50000")))
                .setColor(Color.BROWN)
                .setOldColor(Color.BURLYWOOD)
                .setSize(10)
        );
        world.addBody(new Body( // instabil
                new BigDecimal("27"),
                new Point(new BigDecimal("53"), new BigDecimal("290")),
                new Vector(new BigDecimal("-400"), new BigDecimal("-70000")))
                .setColor(Color.BLACK)
                .setOldColor(Color.GRAY)
        );
        return world;
    }

    private World withoutSwingBy() {
        World world = new World(new BigDecimal("100"));
        world.addBody(new Body(
                new BigDecimal("6000000000"),
                new Point(BigDecimal.ZERO, BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("100")))
                .setColor(Color.ORANGERED)
                .setOldColor(Color.ORANGERED)
                .setSize(35)
        );
        world.addBody(new Body(
                new BigDecimal("20000000"),
                new Point(new BigDecimal("199"), new BigDecimal("17")),
                new Vector(new BigDecimal("-4794"), new BigDecimal("-54791")))
                .setColor(Color.DARKBLUE)
                .setOldColor(Color.CADETBLUE)
                .setSize(10)
        );
        world.addBody(new Body(
                new BigDecimal("15.625"),
                new Point(new BigDecimal("400"), BigDecimal.ZERO),
                new Vector(new BigDecimal("-20000"), new BigDecimal("-29250")))
                .setColor(Color.BLACK)
                .setOldColor(Color.GRAY)
        );
        return world;
    }

    private World threeBodiesSwingBy() {
        World world = new World(new BigDecimal("100"));
        world.addBody(new Body(
                new BigDecimal("6000000000"),
                new Point(BigDecimal.ZERO, BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("100")))
                .setColor(Color.ORANGERED)
                .setOldColor(Color.ORANGERED)
                .setSize(35)
        );
        world.addBody(new Body(
                new BigDecimal("20000000"),
                new Point(new BigDecimal("200"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-55000")))
                .setColor(Color.DARKBLUE)
                .setOldColor(Color.CADETBLUE)
                .setSize(10)
        );
        world.addBody(new Body(
                new BigDecimal("15.625"),
                new Point(new BigDecimal("400"), BigDecimal.ZERO),
                new Vector(new BigDecimal("-20000"), new BigDecimal("-29250")))
                .setColor(Color.BLACK)
                .setOldColor(Color.GRAY)
        );
        return world;
    }

    private World workInProgress() {
        World world = new World(new BigDecimal("100"));
        world.addBody(new Body(
                new BigDecimal("6000000000"),
                new Point(BigDecimal.ZERO, BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("100")))
                .setColor(Color.ORANGERED)
                .setOldColor(Color.ORANGERED)
                .setSize(35)
        );
        world.addBody(new Body(
                new BigDecimal("20000000"),
                new Point(new BigDecimal("200"), BigDecimal.ZERO),
                new Vector(BigDecimal.ZERO, new BigDecimal("-55000")))
                .setColor(Color.DARKBLUE)
                .setOldColor(Color.CADETBLUE)
                .setSize(10)
        );
        world.addBody(new Body(
                new BigDecimal("15.625"),
                new Point(new BigDecimal("400"), BigDecimal.ZERO),
                new Vector(new BigDecimal("-20000"), new BigDecimal("-29250")))
                .setColor(Color.BLACK)
                .setOldColor(Color.GRAY)
        );
        return world;
    }
}
