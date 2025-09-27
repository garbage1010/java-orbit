package garbage1010.orbit;

import java.util.ArrayList;

public class Body {
	
	//constants
	public final double GRAV_CONST = 6.6743 * Math.pow(10, -11);
	
	//properties
	private int id;
	private int mass;
	private double radius;
	private double[] velocity = new double[2];
	private double[] position = new double[2];
	private double[] acceleration = new double[2];
	private double[] force = new double[2];
	
	
	Body(int id, int m, double r, double initVx, double initVy, double initPosx, double initPosy, double initAccx, double initAccy) {
		this.id = id;
		mass = m;
		radius = r;
		velocity[0] = initVx;
		velocity[1] = initVy;
		position[0] = initPosx;
		position[1] = initPosy;
		acceleration[0] = initAccx;
		acceleration[1] = initAccy;
	}
	
	
	
	
	public double distance(Body otherBody) {
		double xDiff = position[0] - otherBody.getPosX();
		double yDiff = position[1] - otherBody.getPosY();
		return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
	}
	//calculates the magnitude of force that otherBody emits on Body
	public double calcForceMag(Body otherBody) {
		int masses = getMass() + otherBody.getMass();
		double radiussqrd = Math.pow(distance(otherBody), 2); 
		return (GRAV_CONST * masses)/radiussqrd;
	}
	//calculate angle of force that otherBody emits on Body
	public double calcForceAng(Body otherBody) {
		double theta = 0;
		double slope = (otherBody.getPosY() - position[1]) / (otherBody.getPosX() - position[0]);
		theta = Math.atan(slope);
		return theta;
	}
	//splits the magnitude of the force that otherBody emits on Body into its X and Y components
	public double[] splitForce(Body otherBody) {
		double forceMag = calcForceMag(otherBody);
		double theta = calcForceAng(otherBody);
		double forceX = forceMag * Math.cos(theta);
		double forceY = forceMag * Math.sin(theta);
		double[] forces = new double[2];
		forces[0] = forceX;
		forces[1] = forceY;
		return forces;
	}
	//takes every body besides itself, calculates the force being emitted on it, and updates force[] with the values
	public void calcAllForces(ArrayList<Body> bodies) {
		double[] totalforces = new double[2];
		double[] returnedforce = new double[2];
		for(int i = 0; i < bodies.size(); i++) {
			if(bodies.get(i).getId() != id) {
				returnedforce = splitForce(bodies.get(i));
				totalforces[0] += returnedforce[0];
				totalforces[1] += returnedforce[1];
			}
		}
		force[0] = totalforces[0];
		force[1] = totalforces[1];
	}
	//updates acceleration using force[]
	public void updateAcceleration() {
		acceleration[0] = force[0] / mass;
		acceleration[1] = force[1] / mass;
	}
	//updates velocity using acceleration[] by time in seconds
	public void updateVelocity(int seconds) {
		velocity[0] += acceleration[0] * seconds;
		velocity[1] += acceleration[1] *seconds;
	}
	//updates position using velocity[] by time in seconds
	public void updatePosition(int seconds) {
		position[0] += velocity[0] * seconds;
		position[1] += velocity [1] * seconds;
	}
	//checks if position is same as other body
	@SuppressWarnings("null")
	public int posCheck(ArrayList<Body> bodies) {
		for(int i = 0; i < bodies.size(); i++) {
			if(position[0] == bodies.get(i).getPosX() && position[1] == bodies.get(i).getPosY() && id != bodies.get(i).getId()) {
				return bodies.get(i).getId();
			}
		}
		return (Integer)null;
	}
	
	//get & set methods
	public int getId() {
		return id;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double r) {
		radius = r;
	}
	public int getMass() {
		return mass;
	}
	public void setMass(int m) {
		mass = m;
	}
	public double getVelX() {
		return velocity[0];
	}
	public void setVelX(double vx) {
		velocity[0] = vx;
	}
	public double getVelY() {
		return velocity[1];
	}
	public void setVelY(double vy) {
		velocity[1] = vy;
	}
	public double getPosX() {
		return position[0];
	}
	public void setPosX(double px) {
		position[0] = px;
	}
	public double getPosY() {
		return position[1];
	}
	public void setPosY(double py) {
		position[1] = py;
	}
	public double getAccX() {
		return acceleration[0];
	}
	public void setAccX(double ax) {
		acceleration[0] = ax;
	}
	public double getAccY() {
		return acceleration[1];
	}
	public void setAccY(double ay) {
		acceleration[1] = ay;
	}
	
	
	
	
}
