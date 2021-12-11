package org.firstinspires.ftc.teamcode;

public class Spinner {
    /* Class Variables */
    //Nothing yet...

    //Object Creation
    HardwareRobot robot;

    /**
     * Constructor
     */
    public Spinner() {
        //Instance Creation
        robot = HardwareRobot.getInstance();
    }

    /**
     * Tilts the grabber arm
     */
    public void spinControl(double spinPower) {
        robot.spin.setPower(spinPower);
    }
}

//End of the Spinner class