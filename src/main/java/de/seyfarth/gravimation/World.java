package de.seyfarth.gravimation;

import de.seyfarth.math.MathUtil;
import de.seyfarth.math.Vector;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    private final List<Body> bodies = new CopyOnWriteArrayList<>();
    private final BigDecimal gravConst;
    
    public World(BigDecimal gravitationalConstant) {
        gravConst = gravitationalConstant;
    }

    public void addBody(Body body) {
        bodies.add(body);
    }
    
    public List<Body> getBodies() {
        return Collections.unmodifiableList(bodies);
    }
    
    public void calculateGravitationStep(BigDecimal deltaTime) {
        calculateForceOnEachBody();
        applyForceOnEachBody(deltaTime);
    }

    private void calculateForceOnEachBody() {
        for (int outer = 0; outer < bodies.size() - 1; outer++) {
            Body actual = bodies.get(outer);
            for (int inner = outer + 1; inner < bodies.size(); inner++) {
                Body related = bodies.get(inner);
                Vector distanceVector = MathUtil.movingVector(actual.getLocation(), related.getLocation());
                Vector vActToRel = distanceVector.unit();
                Vector vRelToAct = vActToRel.invert();
                BigDecimal distance = distanceVector.length();
                BigDecimal force = gravConst
                    .multiply(actual.getMass())
                    .multiply(related.getMass())
                    .divide(distance.multiply(distance), 1024, RoundingMode.HALF_UP);
                actual.addForce(vActToRel.multiply(force));
                related.addForce(vRelToAct.multiply(force));
            }
        }
    }

    private void applyForceOnEachBody(BigDecimal deltaTime) {
        bodies.parallelStream().forEach(body -> body.applyAllForces(deltaTime));
    }

    
}
