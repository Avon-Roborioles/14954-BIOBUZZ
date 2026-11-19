package org.firstinspires.ftc.teamcode.archive.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

@Disabled
@TeleOp
public class LobberOpMode extends LinearOpMode {
    private LauncherSubsystem lobber;

    private DcMotorEx motor;
    private DcMotorEx motor1;
    private Servo servo;


    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class,"intake_motor");
        servo = hardwareMap.get(Servo.class,"servo");
        motor1 = hardwareMap.get(DcMotorEx.class,"launch_motor");

        lobber = new LauncherSubsystem(motor,500);

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
