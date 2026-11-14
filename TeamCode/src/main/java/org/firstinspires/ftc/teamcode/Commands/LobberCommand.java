package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.LobberSubsystem;

public class LobberCommand extends CommandBase {
    private LobberSubsystem subsystem;

    public LobberCommand(LobberSubsystem subsystem) {
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
