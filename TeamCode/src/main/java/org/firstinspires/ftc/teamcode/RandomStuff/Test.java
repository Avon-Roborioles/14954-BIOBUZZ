package org.firstinspires.ftc.teamcode.RandomStuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Test extends LinearOpMode {
    private boolean trueFalse = false;

    @Override
    public void runOpMode() {
        waitForStart();
        if (gamepad1.aWasPressed()){
            trueFalse = true;
        }
        if (gamepad1.aWasReleased()) {
            trueFalse = false;
        }

        telemetry.addData("Boolean Status: ", trueFalse);
        telemetry.update();
    }
}
