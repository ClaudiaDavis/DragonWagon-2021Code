package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Driver Controlled", group="Iterative Opmode")
public class DriverControlled extends OpMode {
    /* Class variables */
    //

    /* Instance Creation */
    HardwareRobot robot       = HardwareRobot.getInstance(); //
    LedLights     led         = LedLights.getInstance();     //
    ElapsedTime   runtime     = new ElapsedTime();           // Starts counting the time
    Manipulator   manipulator = new Manipulator();           // A class for all manipulator functions
    Controls      controls    = new Controls(this);          // A class for the controling functions
    Spinner       spin        = new Spinner();               // A class for the spinner related functions
    Drive         drive       = new Drive();                 // A class for drive functions

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /**
         * Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        //Sets the motor mode
        robot.teleopConfig();

        //Pretty colors
        led.ledInit();
        
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
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
        // Wheel control
        wheelControl();

        // Manipulator Control
        manipulatorControl();

        // Spinner Control
        spinnerControl();

        // LED Control
        ledControl();
        
        //Displays runtime
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        //This should never do anything
    }

    /**
     * A method to control the mecanum wheels
     */
    private void wheelControl() {
        // Gamepad 1 inputs
        double leftStickY  = controls.drivePower();
        double leftStickX  = controls.strafePower();
        double rightStickX = controls.turnPower();

        //Mecanum Drive Method
        drive.mecanumDrive(leftStickY, leftStickX, rightStickX);
    }

    /**
     * A method to control the manipulator and related functions
     */
    private void manipulatorControl() {
        //Gamepad 2 functions
        double                tiltPower      = controls.tiltPower();
        double                intakePower    = controls.intakePower();
        Controls.GrabberState grabberControl = controls.getGrabberPosition();

        //Tilt control
        manipulator.tiltArm(tiltPower);

        //Intake control
        manipulator.intake(intakePower);

        //Grabber control
        manipulator.setGrabberPosition(grabberControl);
    }

    /**
     * A method to control the spinner wheel
     */
    private void spinnerControl() {
        //Gamepad 2 Functions
        double spinPower = controls.spinnerPower();

        //Passes the power that allows the spinner to spin
        spin.spinControl(spinPower);
    }

    /**
     * A method to control the LEDs and related functions
     */
    private void ledControl() {
        boolean automatic = controls.automaticLED();
        boolean manual    = controls.manualLED();
        boolean previous  = controls.previousLED();
        boolean next      = controls.nextLED();

        led.ledTeleOp(automatic, manual, previous, next);
    }

}

// End of the DriverControlled class