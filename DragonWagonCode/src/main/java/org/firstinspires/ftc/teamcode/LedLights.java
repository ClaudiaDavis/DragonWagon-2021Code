package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

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
    public static enum DisplayMode {
        MANUAL,
        AUTO
    }

    /* Object Creation */
    HardwareRobot robot;

    /* Class Variables */
    RevBlinkinLedDriver.BlinkinPattern pattern;

    private final static int LED_PERIOD = 10;       //Change the pattern every 10 seconds in auto
    private final static int GAMEPAD_LOCKOUT = 500; //Rate limit gamepad button presses to every 500ms

    LedLights.DisplayMode displayMode;
    Deadline ledCycleDeadline;
    Deadline gamepadRateLimit;

    /**
     * Constructor
     */
    private LedLights() {
        //Sets the value of displayMode
        displayMode = LedLights.DisplayMode.AUTO;

        //Sets the initial pattern
        pattern = RevBlinkinLedDriver.BlinkinPattern.RAINBOW_RAINBOW_PALETTE;
        robot.lights.setPattern(pattern);

        //Idk what a deadline does
        ledCycleDeadline = new Deadline(LED_PERIOD, TimeUnit.SECONDS);
        gamepadRateLimit = new Deadline(GAMEPAD_LOCKOUT, TimeUnit.MILLISECONDS);

        //Instance Creation
        robot = HardwareRobot.getInstance();
    }

    public void ledAuto() {
        if (displayMode == LedLights.DisplayMode.AUTO) {
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
            setdisplayMode(LedLights.DisplayMode.AUTO);
            gamepadRateLimit.reset();
        }
        else if (buttonBOne == true) {
            setdisplayMode(LedLights.DisplayMode.MANUAL);
            gamepadRateLimit.reset();
        }
        else if ((displayMode == LedLights.DisplayMode.MANUAL) && (leftBumperOne == true)) {
            pattern = pattern.previous();
            displayPattern();
            gamepadRateLimit.reset();
        }
        else if ((displayMode == LedLights.DisplayMode.MANUAL) && (rightBumperOne == true)) {
            pattern = pattern.next();
            displayPattern();
            gamepadRateLimit.reset();
        }
    }

    protected void setdisplayMode(LedLights.DisplayMode displayMode) {
        this.displayMode = displayMode;
        //display.setValue(displayMode.toString());
    }

    protected void doAutoDisplay() {
        if (ledCycleDeadline.hasExpired()) {
            pattern = pattern.next();
            displayPattern();
            ledCycleDeadline.reset();
        }
    }

    protected void displayPattern() {
        robot.lights.setPattern(pattern);
        //patternName.setValue(pattern.toString());
    }

    public String outputMode() {
        return displayMode.toString();
    }

    public String outputPattern() {
        return pattern.toString();
    }
}

//End of the Drive class