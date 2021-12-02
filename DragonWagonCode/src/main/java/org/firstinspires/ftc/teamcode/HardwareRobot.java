package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareRobot {
    /* Singleton For Hardware Robot */
    private static HardwareRobot instance = null;

    public static synchronized HardwareRobot getInstance() {
        if (instance == null) {
            instance = new HardwareRobot();
        }

        return instance;
    }

    /* Class Variables */
    //DcMotors motors
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor intake;
    public DcMotor tilt;

    //Servos
    public Servo grabber;

    //firstTime variables
    boolean firstTime = true;

    //Other members
    HardwareMap hwMap = null;

    /**
     * Constructor
     */
    private HardwareRobot(){
        //Nothing
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Initialize the Drive motors
        frontLeft  = hwMap.get(DcMotor.class, "front_left");
        backLeft   = hwMap.get(DcMotor.class, "back_left");
        frontRight = hwMap.get(DcMotor.class, "front_right");
        backRight  = hwMap.get(DcMotor.class, "back_right");

        // Initialize the Manipulator motors
        intake     = hwMap.get(DcMotor.class, "intake");
        tilt       = hwMap.get(DcMotor.class, "tilt");

        // Initialize the Manipulator servo
        grabber    = hwMap.get(Servo.class, "grabber");

        //Sets the direction for the Drive motors
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        //Sets the direction for the Manipulator motors
        tilt.setDirection(DcMotor.Direction.FORWARD);
        intake.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        frontLeft.setPower(0.00);
        backLeft.setPower(0.00);
        frontRight.setPower(0.00);
        backRight.setPower(0.00);
        intake.setPower(0.00);
        tilt.setPower(0.00);

        //Set the servo stating position
        grabber.setPosition(-1.00);
    }

    /**
     * Configures the motors for Autonomous
     */
    public void autoConfig() {
        /* Resets the motor encoders */
        //Drive Motors
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Manipulator Motor
        //intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //tilt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /* Makes the motors run using encoders */
        //Drive Motors
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Manipulator Motors
        //intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Configures the motors to allow movement to a position
     */
    public void startAutoMovement() {
        /* Makes the motors run to a position */
        //Drive Motors
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Manipulator Motors
        //intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //tilt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Configures the motors to stop movement
     */
    public void stopAutoMovement() {
        /* Makes the motors run to a position */
        //Drive Motors
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Manipulator Motors
        //intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Configures the motors for TeleOp
     */
    public void teleopConfig() {
        /* Sets the motors to run without encoders */
        //Drive Motors
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Manipulator Motor
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        tilt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}

//End of the HardwareRobot class