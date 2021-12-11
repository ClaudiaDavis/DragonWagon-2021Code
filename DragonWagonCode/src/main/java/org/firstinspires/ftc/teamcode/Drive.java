package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.Range;

public class Drive {
    /* Class Variables */
    //Nothing yet...

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
     * Mecanum Drive movement code
     *
     * double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
     * double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
     * double rightX = gamepad1.right_stick_x;
     * final double v1 = r * Math.cos(robotAngle) + rightX;
     * final double v2 = r * Math.sin(robotAngle) - rightX;
     * final double v3 = r * Math.sin(robotAngle) + rightX;
     * final double v4 = r * Math.cos(robotAngle) - rightX;
     *
     * leftFront.setPower(v1);
     * rightFront.setPower(v2);
     * leftRear.setPower(v3)
     * rightRear.setPower(v4);
     */
    public void mecanumDrive(double drive, double strafe, double turn) {
<<<<<<< HEAD
        //Calculate the powers
//        double frontLeftPower  = (drive + strafe + turn);
//        double backLeftPower   = (drive - strafe + turn);
//        double frontRightPower = (drive - strafe - turn);
//        double backRightPower  = (drive + strafe - turn);
//
//        //Clamps the powers
//        frontLeftPower  = Range.clip(frontLeftPower, -1.0, 1.0);
//        backLeftPower   = Range.clip(backLeftPower, -1.0, 1.0);
//        frontRightPower = Range.clip(frontRightPower, -1.0, 1.0);
//        backRightPower  = Range.clip(backRightPower, -1.0, 1.0);

=======

        // Trigonometry Wizardry
        
>>>>>>> fd8ba3ce7fb46b5ed22d09c733e4109441cfd183
        double r = Math.hypot(strafe, drive);
        double robotAngle = Math.atan2(drive, strafe) - Math.PI / 4;
        double rightX = turn;
        final double frontLeftPower = r * Math.cos(robotAngle) + rightX;
        final double backLeftPower = r * Math.sin(robotAngle) - rightX;
        final double frontRightPower = r * Math.sin(robotAngle) + rightX;
        final double backRightPower = r * Math.cos(robotAngle) - rightX;

        //Sets motor power
        robot.frontLeft.setPower(frontLeftPower);
        robot.backLeft.setPower(backLeftPower);
        robot.frontRight.setPower(frontRightPower);
        robot.backRight.setPower(backRightPower);
    }

    /**
     * Autonomous driving
     */
    public void autoForward(double feet, double autoPower) {

        //Feet to ticks
        double target = feetToTicks(feet);
        int targetInteger = (int)target;

        //Sets the desired target position
        robot.frontLeft.setTargetPosition(targetInteger);
        robot.backLeft.setTargetPosition(targetInteger);
        robot.frontRight.setTargetPosition(targetInteger);
        robot.backRight.setTargetPosition(targetInteger);

        //Allow movement
        robot.startAutoMovement();

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
