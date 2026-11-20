package org.firstinspires.ftc.teamcode.commands.intake;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class FlowerWheelCommand extends CommandBase {
    private final IntakeSubsystem intake;
    private final double power;

    public FlowerWheelCommand(IntakeSubsystem intake, double power) {
        this.intake = intake;
        this.power = power;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setFlowerPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        intake.setFlowerPower(0);
    }
}
