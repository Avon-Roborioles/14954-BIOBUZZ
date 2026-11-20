package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends SubsystemBase {
    private final DcMotor grabWheel;
    private final DcMotor flowerWheel;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        grabWheel = hardwareMap.get(DcMotor.class, "intake_motor");
        flowerWheel = hardwareMap.get(DcMotor.class, "flower_motor");
    }

    public void setGrabPower(double power) {
        grabWheel.setPower(power);
    }

    public void setFlowerPower(double power) {
        flowerWheel.setPower(power);
    }

    public void stop() {
        grabWheel.setPower(0);
        flowerWheel.setPower(0);
    }
}
