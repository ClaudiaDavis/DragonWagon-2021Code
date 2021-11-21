package org.firstinspires.ftc.teamcode;

public class Manipulator {
    /* Class Variables */
    double speedMultiplier = 1.00;

    //First Time
    boolean firstTime = true;

    //Object Creation
    HardwareRobot robot;

    /**
     * Constructor
     */
    public Manipulator() {
        //Instance Creation
        robot = new HardwareRobot.getInstance();
    }

    /**
     * Tilts the manipulator
     */
    public void tiltArm(double tiltPower) {
        robot.tilt.setPower(tiltPower);
    }

    /**
     * Changes the physical position of the grabber
     */
    public void setGrabberPosition(GrabberState position) {
        final double DEPOLYED  = 1.00;
        final double RETRACTED = 0.00;

        //What to do if the position is deployed
        if (position == GrabberState.DEPLOYED) {
            robot.grabber.setPosition(DEPOLYED);
        }
        //What to do if the position is retracted
        else if (position == GrabberState.RETRACTED) {
            robot.grabber.setPosition(RETRACTED);
        }
    }
}

//End of the Manipulator class