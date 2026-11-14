package org.firstinspires.ftc.teamcode.RandomStuff;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class TestShooter extends LinearOpMode {
    private DcMotor motor1;
    private DcMotor motor2;
    private Servo servo1;

    @Override
    public void runOpMode() {

        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        servo1 = hardwareMap.get(Servo.class, "servo1");

        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.FORWARD);

        double motorPower = 0.0;
        double servoPosition = 0.5;

        servo1.setPosition(servoPosition);

        waitForStart();

        while(opModeIsActive()) {
            motor1.setPower(motorPower);
            motor2.setPower(motorPower);

            if (gamepad1.left_bumper) {
                motorPower -= 0.5;
            }

            if (gamepad1.right_bumper) {
                motorPower += 0.5;
            }

            if (gamepad1.y) {
                servoPosition += 0.5;
            }

        }
    }
}
