package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class DriveSubsystem extends SubsystemBase {
    private final Follower follower;

    public DriveSubsystem(HardwareMap hardwareMap) {
        this.follower = Constants.create(hardwareMap);
    }

    public void drive(double forward, double strafe, double rotate, boolean fieldCentric) {
        follower.setMovementVectors(forward, strafe, rotate, fieldCentric);
    }

    public Follower getFollower() {
        return follower;
    }

    @Override
    public void periodic() {
        follower.update();
    }
}
