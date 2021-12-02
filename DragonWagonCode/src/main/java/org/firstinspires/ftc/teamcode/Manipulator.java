package org.firstinspires.ftc.teamcode;

public class Manipulator {
    //Object Creation
    HardwareRobot robot;

    /**
     * Constructor
     */
    public Manipulator() {
        //Instance Creation
        robot = HardwareRobot.getInstance();
    }

    /**
     * Tilts the manipulator
     */
    public void tiltArm(double tiltPower) {
        robot.tilt.setPower(tiltPower);
    }

    /**
     * Gives power to the intake motor
     */
    public void intake(double intakePower) {
        robot.intake.setPower(intakePower);
    }

    /**
     * Changes the physical position of the grabber
     */
    public void setGrabberPosition(Controls.GrabberState position) {
        //Positions (constants)
        final double DEPOLYED  = -1.00;
        final double RETRACTED =  1.00;
        
        //What to do if the position is set to deployed
        if (position == Controls.GrabberState.DEPLOYED) {
            robot.grabber.setPosition(DEPOLYED);
        }
        //What to do if the position is set to retracted
        else if (position == Controls.GrabberState.RETRACTED) {
            robot.grabber.setPosition(RETRACTED);
        }
    }
}

//End of the Manipulator class