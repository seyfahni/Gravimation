package de.seyfarth.gravimation;

import de.seyfarth.math.Point;
import de.seyfarth.math.Vector;
import java.math.BigDecimal;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Body {

    private final BigDecimal mass;
    private Point location;
    private Point oldLocation;
    private Vector velocity;
    private Vector force = new Vector();
    
    private Paint color = Color.BLACK;
    private Paint oldColor = Color.GRAY;
    private double size = 10;

    public Body(BigDecimal mass, Point location, Vector velocity) {
        this.mass = mass;
        this.location = location;
        this.oldLocation = location;
        this.velocity = velocity;
    }
    
    public Body(BigDecimal mass, Point location) {
        this.mass = mass;
        this.location = location;
        this.velocity = new Vector();
    }
    
    public BigDecimal getMass() {
        return mass;
    }
    
    public Point getLocation() {
        return location;
    }

    public Point getOldLocation() {
        return oldLocation;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void addForce(Vector force) {
        this.force = this.force.add(force);
    }
    
    public void applyAllForces(BigDecimal deltaTime) {
        oldLocation = location;
        velocity = getAcceleration().multiply(deltaTime).add(velocity);
        location = location.move(velocity.multiply(deltaTime));
        force = new Vector();
    }
    
    public Vector getAcceleration() {
        return force.divide(mass);
    }

    public Paint getColor() {
        return color;
    }

    public Body setColor(Paint color) {
        this.color = color;
        return this;
    }

    public double getSize() {
        return size;
    }

    public Body setSize(double size) {
        this.size = size;
        return this;
    }

    public Paint getOldColor() {
        return oldColor;
    }

    public Body setOldColor(Paint oldColor) {
        this.oldColor = oldColor;
        return this;
    }
    
}
