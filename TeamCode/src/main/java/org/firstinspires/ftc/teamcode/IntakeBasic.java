package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "IntakeBasic")
public class IntakeBasic extends LinearOpMode {
    DcMotor intake = hardwareMap.get(DcMotor.class, "intake_motor");

    boolean lastA = false;
    boolean intakeOn = false;
    boolean a = gamepad1.a;
    double intakeDirection = -1.0;
    public void onOff (){
        if(a && !lastA) {
            intakeDirection = -intakeDirection;
            intakeOn = !intakeOn;
            intake.setPower(intakeDirection);
        }
    }
    public void runOpMode(){
        waitForStart();
        while (opModeIsActive()){
            onOff();

        }
    }
}
