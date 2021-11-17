package org.firstinspires.ftc.teamcode;

/**
 * Controls class
 * Contains all functions relating to inputs
 */
public class Controls {
    //Singleton to ensure there is only ever one instance of Controls
    private static Controls instance = null;

    public static synchronized Controls getInstance() {
        if(instance == null) {
            instance = new Controls();
        }

        return instance;
    }

    // Class Variables
    double speedMultiplier = 1.00;
    
    //Constructor
    private Controls() {
        //Nothing so far
    }

    /**
     * A function that gets the drive power for the robot
     * @return DrivePower
     */
    public double drivePower() {
        double drivePower = -1 * gamepad1.left_stick_y;
        drivePower = drivePower * speedMultiplier;

        return drivePower;
    }

    /**
     * A function that gets the strafing power for the robot
     * @return StrafePower
     */
    public double strafePower() {
        double strafePower = gamepad1.left_stick_x;
        strafePower = strafePower * speedMultiplier;
        
        return strafePower;
    }
    
    /**
     * A function that gets the turn power for the robot
     */
    public double turnPower() {
        double turnPower = gamepad1.right_stick_x;
        turnPower = turnPower * speedMultiplier;

        return turnPower;
    }
}

// End of Controls class