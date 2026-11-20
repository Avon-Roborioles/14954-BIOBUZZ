package org.firstinspires.ftc.teamcode.commands.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.pathgen.PathChain;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

public class PedroPassingCommand extends CommandBase {
    private final DriveSubsystem drive;
    private final PathChain path;
    private final boolean holdEnd;

    public PedroPassingCommand(DriveSubsystem drive, PathChain path, boolean holdEnd) {
        this.drive = drive;
        this.path = path;
        this.holdEnd = holdEnd;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.getFollower().followPath(path, holdEnd);
    }

    @Override
    public boolean isFinished() {
        return !drive.getFollower().isBusy();
    }
}
