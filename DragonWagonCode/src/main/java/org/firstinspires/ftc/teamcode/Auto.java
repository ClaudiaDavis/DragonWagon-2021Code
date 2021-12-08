package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 */

@Autonomous(name="Autonomous", group="Iterative Opmode")
public class Auto extends OpMode {

    /* Declare OpMode members. */
    HardwareRobot robot   = HardwareRobot.getInstance(); // Uses the robot's hardware
    LedLights     led     = LedLights.getInstance();     //
    ElapsedTime   runtime = new ElapsedTime();           // Starts counting the time 
    Drive         drive   = new Drive();                 //

    /* Class Variables */
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    //For the switch function 
    private int step = -1;

    //firstTime variables
    private boolean firstTime = true;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        //Rests the encoders
        robot.autoConfig();

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d",
                          robot.frontLeft.getCurrentPosition(),
                          robot.backLeft.getCurrentPosition(),
                          robot.frontRight.getCurrentPosition(),
                          robot.backRight.getCurrentPosition());
        telemetry.update();

        //Sets firstTime to true
        firstTime = true;
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        //
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        int  status = HardwareRobot.CONT;;

        if (firstTime == true) {
            firstTime = false;
			step = 1;
        }

        switch (step) {
            //Starts the Auto Programs
            case 1:
                encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
                break;
            case 2:
                encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
                break;
            case 3:
                encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
                break;
            default:
                step = 0;
                firstTime = true;
        }

        if (status == HardwareRobot.DONE) {
            //Increments the variable step
            step++;

            //Prints telemetry
            telemetry.addData("Path", "Complete");
            telemetry.update();
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        //This should never do anything
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Determine new target position, and pass to motor controller
        newLeftTarget = robot.frontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        newRightTarget = robot.frontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        robot.frontLeft.setTargetPosition(newLeftTarget);
        robot.backLeft.setTargetPosition(newLeftTarget);
        robot.frontRight.setTargetPosition(newRightTarget);
        robot.backRight.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        robot.startAutoMovement();

        // reset the timeout time and start motion.
        runtime.reset();
        robot.frontLeft.setPower(Math.abs(speed));
        robot.backLeft.setPower(Math.abs(speed));
        robot.frontRight.setPower(Math.abs(speed));
        robot.backRight.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while ((runtime.seconds() < timeoutS) &&
               (robot.frontLeft.isBusy() && robot.frontRight.isBusy())) {

            // Display it for the driver.
            telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d",
                            robot.frontLeft.getCurrentPosition(),
                            robot.backLeft.getCurrentPosition(),
                            robot.frontRight.getCurrentPosition(),
                            robot.backRight.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        robot.frontLeft.setPower(0.00);
        robot.backLeft.setPower(0.00);
        robot.frontRight.setPower(0.00);
        robot.backRight.setPower(0.00);

        // Turn off RUN_TO_POSITION
        robot.stopAutoMovement();

        //  sleep(250);   // optional pause after each move
    }
}

//End of the Auto class