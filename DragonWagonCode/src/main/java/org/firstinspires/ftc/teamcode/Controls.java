package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Controls {
    /* Class Variables */
    double speedMultiplier = 1.00;

    /**
     * Constructor
     */
    public Controls(OpMode opMode) {
        //Nothing yet
    }
    
    public double drivePower() {
        double power = -1 * gamepad1.left_stick_y;
        double drivePower = power * speedMultiplier;

        return drivePower;
    }
}
//End of the Controls class