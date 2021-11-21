package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Controls {
    /* Enumerator for setting grabber states (words are easier to read than numbers) */
    public static enum GrabberState {
        DEPLOYED,
        RETRACTED;
    }
    GrabberState grabberState = GrabberState.DEPLOYED;

    /* Class Variables */
    double speedMultiplier = 1.00;
    
    //Object Creation
    OpMode logitech;

    /**
     * Constructor
     */
    public Controls(OpMode opmode) {
        //Passes an OpMode into the class (allows for the use of controllers)
        logitech = opmode;
    }

    /**
     * Gamepad 1
     * Controls the driving of the robot 
     */
    public double drivePower() {
        double power = -1 * logitech.gamepad1.left_stick_y;
        double drivePower = power * speedMultiplier;

        return drivePower;
    }

    public double strafePower() {
        double power = logitech.gamepad1.left_stick_x;
        double strafePower = power * speedMultiplier;

        return strafePower;
    }

    public double turnPower() {
        double power = logitech.gamepad1.right_stick_x;
        double turnPower = power * speedMultiplier;

        return turnPower;
    }

    /**
     * Gamepad 2
     * Controls the tilt of the manipulator
     */
    public double tiltPower() {
        double power = -1 * logitech.gamepad2.left_stick_y;

        return power;
    }

    /**
     * Gamepad 2
     * Sets the position of the grabber servo
     */
    public GrabberState getGrabberPosition() {
        boolean deployRetract = logitech.gamepad2.right_bumper;

        //Checks if the right bumper is pressed
        if (deployRetract == true) {
            //If the grabber is currently deployed, changes it to be retracted
            if (grabberState == GrabberState.DEPLOYED) {
                grabberState = GrabberState.RETRACTED;
            }
            //If the grabber is currently retracted, changes it to be depolyed
            else if (grabberState == GrabberState.RETRACTED) {
                grabberState = GrabberState.DEPLOYED;
            }
        }

        return grabberState;
    }
}

//End of the Controls class