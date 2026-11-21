package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "IntakeBasic")
public class IntakeBasic extends LinearOpMode {
    DcMotor intake = hardwareMap.get(DcMotor.class, "intake_motor");

    boolean intakeForward = false;
    boolean intakeOn =false;
    double intakeDirection = -1.0;
    public void forwardBackward(){
        if(gamepad1.aWasPressed()) {
            intakeDirection = -intakeDirection;
            intakeForward = !intakeForward;
            intake.setPower(intakeDirection);
            telemetry.addData("Intake forward:", intakeForward);
            telemetry.update();
        }
    }
    public void turnItOff(){
        if(gamepad1.bWasPressed()){
            intakeOn = !intakeOn;
            intake.setPower(0);
            telemetry.addData("Intake On:", intakeOn);
            telemetry.update();
        }
    }
    public void runOpMode(){
        waitForStart();
        while (opModeIsActive()){
            forwardBackward();
            turnItOff();

        }
    }
}
