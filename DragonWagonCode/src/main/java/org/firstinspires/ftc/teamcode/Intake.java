package org.firstinspires.ftc.teamcode;

public class Intake {
    /* Class Variables */
    double speedMultiplier = 1.00;

    //First Time
    boolean firstTime = true;

    //Object Creation
    HardwareRobot robot;

    /**
     * Constructor
     */
    public Intake() {
        //Instance Creation
        robot = new HardwareRobot.getInstance();
    }

    /**
     * Tilts the manipulator
     * Not very sure on this
     */
    public void tiltArm(double tiltPower) {
        robot.tilt.setPower(tiltPower);
    }
}

//End of the Intake class