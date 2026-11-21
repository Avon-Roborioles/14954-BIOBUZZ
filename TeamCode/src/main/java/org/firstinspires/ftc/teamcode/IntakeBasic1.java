package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "IntakeBasic")
public class IntakeBasic1 extends OpMode {
    // 1. Declare the motor variable at the class level
    private DcMotor intake = null;

    private boolean intakeForward = false;
    private boolean intakeOn = false;
    private double intakeDirection = -1.0;

    @Override
    public void init() {
        // 2. Initialize the motor inside the init() method
        intake = hardwareMap.get(DcMotor.class, "intake_motor");

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        forwardBackward();
        turnItOff();

        // 3. Optional: Add a general status telemetry
        telemetry.addData("Intake Power", intake.getPower());
    }

    public void forwardBackward() {
        // Note: WasPressed methods are available in newer SDK versions.
        // If they cause errors, you may need to update your SDK.
        if (gamepad1.aWasPressed()) {
            intakeDirection = -intakeDirection;
            intakeForward = !intakeForward;
            intake.setPower(intakeDirection);
            telemetry.addData("Intake direction", intakeForward ? "Forward" : "Backward");
        }
    }

    public void turnItOff() {
        if (gamepad1.bWasPressed()) {
            intakeOn = !intakeOn;
            // When turning off, set power to 0
            intake.setPower(0);
            telemetry.addData("Intake State", "Stopped");
        }
    }
}