package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Controls {
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
}
//End of the Controls class