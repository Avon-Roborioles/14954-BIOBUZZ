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
<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/OpMode/LobberOpMode.java
=======

>>>>>>> dd80723cef96e10822c4bf33b519e49afc319f73:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/archive/OpMode/LobberOpMode.java

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class,"intake_motor");
<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/OpMode/LobberOpMode.java
        servo = hardwareMap.get(Servo.class,"intake_servo");
        motor1 = hardwareMap.get(DcMotorEx.class, "launch_motor");

        lobber = new LobberSubsystem(motor, motor1,500.00, servo, 0.0);

=======
        servo = hardwareMap.get(Servo.class,"servo");
        motor1 = hardwareMap.get(DcMotorEx.class,"launch_motor");

        lobber = new LauncherSubsystem(motor,500);
>>>>>>> dd80723cef96e10822c4bf33b519e49afc319f73:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/archive/OpMode/LobberOpMode.java

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
