package org.firstinspires.ftc.teamcode.commands.launcher;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

public class LauncherRotationCommand extends CommandBase {
    private final LauncherSubsystem launcher;
    private final double power;

    public LauncherRotationCommand(LauncherSubsystem launcher, double power) {
        this.launcher = launcher;
        this.power = power;
        addRequirements(launcher);
    }

    @Override
    public void execute() {
        launcher.setRotationPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        launcher.setRotationPower(0);
    }
}
