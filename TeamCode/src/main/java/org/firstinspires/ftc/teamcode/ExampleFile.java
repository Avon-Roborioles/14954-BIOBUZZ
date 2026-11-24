package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Intake Test")
public class ExampleFile extends OpMode {
    private DcMotor intakeMotor;

    @Override
    public void init() {
        // Initialize the motor using the name "intake_motor"
        intakeMotor = hardwareMap.get(DcMotor.class, "intake_motor");
    }

    @Override
    public void loop() {
        // TODO: Adjust power level if the intake is too fast/slow.
        // Tip: Use TODO comments to track small personal tasks. Android Studio
        // lists them all in the "TODO" tool window at the bottom of the screen.
        if (gamepad1.a) {
            intakeMotor.setPower(1.0);
        } else if (gamepad1.b) {
            intakeMotor.setPower(-1.0);
        } else {
            intakeMotor.setPower(0.0);
        }
    }
}
