package org.firstinspires.ftc.teamcode.commands.launcher;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

public class RampExtensionCommand extends CommandBase {
    private final LauncherSubsystem launcher;
    private final double position;

    public RampExtensionCommand(LauncherSubsystem launcher, double position) {
        this.launcher = launcher;
        this.position = position;
        addRequirements(launcher);
    }

    @Override
    public void initialize() {
        launcher.setRampPosition(position);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
