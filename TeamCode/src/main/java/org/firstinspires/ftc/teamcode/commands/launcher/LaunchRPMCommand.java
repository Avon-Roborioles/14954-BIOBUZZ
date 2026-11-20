package org.firstinspires.ftc.teamcode.commands.launcher;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

public class LaunchRPMCommand extends CommandBase {
    private final LauncherSubsystem launcher;
    private final double rpm;

    public LaunchRPMCommand(LauncherSubsystem launcher, double rpm) {
        this.launcher = launcher;
        this.rpm = rpm;
        addRequirements(launcher);
    }

    @Override
    public void initialize() {
        launcher.setRPM(rpm);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
