package org.firstinspires.ftc.teamcode.OpMode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Subsystems.LobberSubsystem;

@Disabled
@TeleOp
public class LobberOpMode extends LinearOpMode {
    private LobberSubsystem lobber;

    private DcMotorEx motor;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class,"lobmotor");

        lobber = new LobberSubsystem(motor,500);

        waitForStart();

        while(opModeIsActive()) {
            lobber.runMotor();

            if(gamepad1.rightBumperWasPressed()) {
                lobber.increaseVelocity();
            }
            else if(gamepad1.leftBumperWasPressed()) {
                lobber.decreaseVelocity();
            }

            telemetry.addData("Target Velocity",lobber.getVelo());
            telemetry.update();
        }
    }
}