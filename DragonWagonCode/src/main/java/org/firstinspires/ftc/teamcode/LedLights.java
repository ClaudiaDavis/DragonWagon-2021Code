package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver.BlinkinPattern;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

public class LedLights {
    /* Singleton For Hardware Robot */
    private static LedLights instance = null;

    public static synchronized LedLights getInstance() {
        if (instance == null) {
            instance = new LedLights();
        }

        return instance;
    }

    /**
     * Enumerator for mode
     */
    protected enum DisplayMode {
        INIT,
        AUTO,
        MANUAL;
    }

    /* Object Creation */
    HardwareRobot robot;

    /* Class Variables */
    RevBlinkinLedDriver.BlinkinPattern pattern;

    private final static int LED_PERIOD = 10;       //Change the pattern every 10 seconds in auto
    private final static int GAMEPAD_LOCKOUT = 500; //Rate limit gamepad button presses to every 500ms

    DisplayMode displayMode;
    Deadline ledCycleDeadline;
    Deadline gamepadRateLimit;

    /**
     * Constructor
     */
    private LedLights() {
        //Instance Creation
        robot = HardwareRobot.getInstance();

        //Sets the value of displayMode
        displayMode = DisplayMode.INIT;

        //Sets the initial pattern
        pattern = RevBlinkinLedDriver.BlinkinPattern.RAINBOW_RAINBOW_PALETTE;

        //Idk what a deadline does
        ledCycleDeadline = new Deadline(LED_PERIOD, TimeUnit.SECONDS);
        gamepadRateLimit = new Deadline(GAMEPAD_LOCKOUT, TimeUnit.MILLISECONDS);
    }
    
    public void ledInit() {
        if (displayMode == DisplayMode.INIT) {
            robot.lights.setPattern(pattern);
        }

        displayMode = DisplayMode.AUTO;
    }

    public void ledAuto() {
        if (displayMode == DisplayMode.AUTO) {
            doAutoDisplay();
        }
        else {
            // Do nothing
        }
    }

    /**
     * Method for TeleOp control of the LEDS's
     * <p>
     * <p>Button A on controller 1 makes them move automatically
     * <p>Button B on controller 1 makes them move manually
     * <p>Left Bumper on controller 1 goes to the previous pattern
     * <p>Right Bumper on controller 1 goes to the next pattern
     */
    public void ledTeleOp(boolean buttonAOne, boolean buttonBOne, boolean leftBumperOne, boolean rightBumperOne) {
        if (!gamepadRateLimit.hasExpired()) {
            return;
        }

        if (buttonAOne == true) {
            setdisplayMode(DisplayMode.AUTO);
            gamepadRateLimit.reset();
        }
        else if (buttonBOne == true) {
            setdisplayMode(DisplayMode.MANUAL);
            gamepadRateLimit.reset();
        }
        else if ((displayMode == DisplayMode.MANUAL) && (leftBumperOne == true)) {
            pattern = pattern.previous();
            displayPattern();
            gamepadRateLimit.reset();
        }
        else if ((displayMode == DisplayMode.MANUAL) && (rightBumperOne == true)) {
            pattern = pattern.next();
            displayPattern();
            gamepadRateLimit.reset();
        }
    }

    private void setdisplayMode(DisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    private void doAutoDisplay() {
        if (ledCycleDeadline.hasExpired()) {
            pattern = pattern.next();
            displayPattern();
            ledCycleDeadline.reset();
        }
    }

    private void displayPattern() {
        robot.lights.setPattern(pattern);
    }

}

//End of the Drive class