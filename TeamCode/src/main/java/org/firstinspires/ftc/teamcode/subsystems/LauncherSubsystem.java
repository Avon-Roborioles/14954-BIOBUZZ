package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LauncherSubsystem extends SubsystemBase {

    private final DcMotorEx rpmMotor;
    private final DcMotorEx rotationMotor;
    private final Servo rampServo;
    
    private double targetRPM = 0;

    public LauncherSubsystem(HardwareMap hardwareMap) {
        this.rpmMotor = hardwareMap.get(DcMotorEx.class, "launch_motor");
        this.rotationMotor = hardwareMap.get(DcMotorEx.class, "rotation_motor");
        this.rampServo = hardwareMap.get(Servo.class, "ramp_servo");
    }

    public void setRPM(double rpm) {
        this.targetRPM = rpm;
        rpmMotor.setVelocity(rpm);
    }

    public void setRotationPower(double power) {
        rotationMotor.setPower(power);
    }

    public void setRampPosition(double position) {
        rampServo.setPosition(position);
    }

    public double getTargetRPM() {
        return targetRPM;
    }

    public void stop() {
        rpmMotor.setPower(0);
        rotationMotor.setPower(0);
    }
}
