package org.firstinspires.ftc.teamcode.commands.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import java.util.function.DoubleSupplier;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

public class DefaultDriveCommand extends CommandBase {
    private final DriveSubsystem drive;
    private final DoubleSupplier forward, strafe, rotate;

    public DefaultDriveCommand(DriveSubsystem drive, DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier rotate) {
        this.drive = drive;
        this.forward = forward;
        this.strafe = strafe;
        this.rotate = rotate;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.drive(forward.getAsDouble(), strafe.getAsDouble(), rotate.getAsDouble(), true);
    }
}
