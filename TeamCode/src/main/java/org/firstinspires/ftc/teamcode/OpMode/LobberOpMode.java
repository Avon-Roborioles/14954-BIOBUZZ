package org.firstinspires.ftc.teamcode.OpMode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.LobberSubsystem;

@Disabled
@TeleOp
public class LobberOpMode extends LinearOpMode {
    private LobberSubsystem lobber;

    private DcMotorEx motor;
    private DcMotorEx motor1;
    private Servo servo;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class,"intake_motor");
        servo = hardwareMap.get(Servo.class,"intake_servo");
        motor1 = hardwareMap.get(DcMotorEx.class, "launch_motor");

        lobber = new LobberSubsystem(motor, motor1,500.00, servo, 0.0);


        waitForStart();

        while(opModeIsActive()) {
            lobber.runMotor();

            if(gamepad1.rightBumperWasPressed()) {
                lobber.increaseVelocity();
            }
            else if(gamepad1.leftBumperWasPressed()) {
                lobber.decreaseVelocity();
            }

            if(gamepad1.triangleWasPressed()) {
                lobber.increasePos();
            }

            if(gamepad1.circleWasPressed()) {
                lobber.decreasePos();
            }

            telemetry.addData("Target Velocity",lobber.getVelo());
            telemetry.addData("Servo Position", lobber.getPos());
            telemetry.update();
        }
    }
}