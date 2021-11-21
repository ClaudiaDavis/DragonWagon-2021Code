package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.Range;

public class Drive {
    /* Class Variables */
    double speedMultiplier = 1.00;

    //Object Creation
    HardwareRobot robot;

    /**
     * Constructor
     */
    public Drive() {
        //Instance Creation
        robot = HardwareRobot.getInstance();
    }
    
    /**
     * Manipulator movement code
     */
    public void mecanumDrive(double drive, double strafe, double turn) {
        //Calculate the powers
        /*double denominator     = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);
        double frontLeftPower  = (drive + strafe + turn) / denominator;
        double backLeftPower   = (drive - strafe + turn) / denominator;
        double frontRightPower = (drive - strafe - turn) / denominator;
        double backRightPower  = (drive + strafe - turn) / denominator;*/
        double frontLeftPower  = (drive + strafe + turn);
        double backLeftPower   = (drive - strafe + turn);
        double frontRightPower = (drive - strafe - turn);
        double backRightPower  = (drive + strafe - turn);

        //Clamps the powers
        frontLeftPower  = Range.clip(frontLeftPower, -1.0, 1.0);
        backLeftPower   = Range.clip(backLeftPower, -1.0, 1.0);
        frontRightPower = Range.clip(frontRightPower, -1.0, 1.0);
        backRightPower  = Range.clip(backRightPower, -1.0, 1.0);

        //Sets motor power
        robot.frontLeft.setPower(frontLeftPower);
        robot.backLeft.setPower(backLeftPower);
        robot.frontRight.setPower(frontRightPower);
        robot.backRight.setPower(backRightPower);
    }

    /**
     * Autonomous driving
     */
    public void autoForward(double feet) {
        //Variables
        double autoPower = 0.50;

        //Feet to ticks
        double target = feetToTicks(feet);

        //Sets the desired target position
        robot.frontLeft.setTargetPosition(target);
        robot.backLeft.setTargetPosition(target);
        robot.frontRight.setTargetPosition(target);
        robot.backRight.setTargetPosition(target);

        //Allow movement
        robot.startAutoMovement()

        //Sets the motor power
        robot.frontLeft.setPower(autoPower);
        robot.backLeft.setPower(autoPower);
        robot.frontRight.setPower(autoPower);
        robot.backRight.setPower(autoPower);

        //Stop movement
        if (robot.frontRight.getCurrentPosition() > (target - 100) ) {
            robot.stopAutoMovement();
        }
    }

    /**
     * Converts feet to encoder ticks
     */
    private double feetToTicks(double targetFeet) {
        final double TICKS_PER_FOOT = 0.00;         //This value is currently unknown
        double ticks = targetFeet * TICKS_PER_FOOT;

        return ticks;
    }

}

//End of the Drive class