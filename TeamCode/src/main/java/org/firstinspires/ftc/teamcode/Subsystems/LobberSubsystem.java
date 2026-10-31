package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class LobberSubsystem extends SubsystemBase {

private DcMotorEx lobberMotor;
private double targetVelocity;
public LobberSubsystem(DcMotorEx lobberMotor, double targetVelocity) {
    this.lobberMotor = lobberMotor;
    this.targetVelocity = targetVelocity;
}
public void increaseVelocity() {
    targetVelocity += 25.00;
}
public void decreaseVelocity() { targetVelocity -= 25.00;}
public void runMotor() {
    lobberMotor.setVelocity(targetVelocity);
}

public double getVelo(){
    return targetVelocity;
}
}
