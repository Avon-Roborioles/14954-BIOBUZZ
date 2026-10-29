package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class LobberSubsystem {

private DcMotorEx lobberMotor;
private double targetVelocity;
public LobberSubsystem(DcMotorEx lobberMotor, double targetVelocity) {
    this.lobberMotor = lobberMotor;
    this.targetVelocity = targetVelocity;
}

public void increaseVelocity() {
    targetVelocity += 25.00;
}

public void runMotor() {
    lobberMotor.setVelocity(targetVelocity);
}
}
