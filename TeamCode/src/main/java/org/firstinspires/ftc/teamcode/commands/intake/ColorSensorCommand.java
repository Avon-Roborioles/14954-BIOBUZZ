package org.firstinspires.ftc.teamcode.commands.intake;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensorSubsystem;

public class ColorSensorCommand extends CommandBase {
    private final ColorSensorSubsystem colorSensor;

    public ColorSensorCommand(ColorSensorSubsystem colorSensor) {
        this.colorSensor = colorSensor;
        addRequirements(colorSensor);
    }

    @Override
    public void execute() {
        // Example: Add logic to process color data or send to telemetry
    }
}
