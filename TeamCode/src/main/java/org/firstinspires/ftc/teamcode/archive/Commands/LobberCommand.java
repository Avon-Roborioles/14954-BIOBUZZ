package org.firstinspires.ftc.teamcode.archive.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

public class LobberCommand extends CommandBase {
    private LauncherSubsystem subsystem;

    public LobberCommand(LauncherSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }
    @Override
    public void execute() {
        subsystem.runMotor();
    }
    @Override
    public void end(boolean interrupted) {

    }
}
