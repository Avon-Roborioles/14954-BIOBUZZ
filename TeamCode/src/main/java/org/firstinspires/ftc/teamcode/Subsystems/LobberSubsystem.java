package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class LobberSubsystem extends SubsystemBase {

private DcMotorEx lobberMotor;
private DcMotorEx lobberMotor1;
private double targetVelocity;
private Servo servo;
private double servoPos;
public LobberSubsystem(DcMotorEx lobberMotor, DcMotorEx lobberMotor1, double targetVelocity, Servo servo, double servoPos) {
    this.lobberMotor = lobberMotor;
    this.lobberMotor1 = lobberMotor1;
    this.targetVelocity = targetVelocity;
    this.servo = servo;
    this.servoPos = servoPos;
}
public void increaseVelocity() {
    targetVelocity += 25.00;
}
public void decreaseVelocity() { targetVelocity -= 25.00;}
public void runMotor() {
    lobberMotor.setVelocity(targetVelocity);
    lobberMotor1.setVelocity(targetVelocity);
}
public void increasePos() { servoPos += 0.1;}
public void decreasePos() { servoPos -= 0.1;}
public void setServoPos() {servo.setPosition(servoPos);}
public double getVelo(){
    return targetVelocity;
}
public double getPos() {
    return servoPos;
}
}
