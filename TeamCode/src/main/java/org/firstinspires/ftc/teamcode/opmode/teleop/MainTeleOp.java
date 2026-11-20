package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.drive.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.intake.FlowerWheelCommand;
import org.firstinspires.ftc.teamcode.commands.intake.GrabWheelCommand;
import org.firstinspires.ftc.teamcode.commands.launcher.LaunchRPMCommand;
import org.firstinspires.ftc.teamcode.commands.launcher.LauncherRotationCommand;
import org.firstinspires.ftc.teamcode.commands.launcher.RampExtensionCommand;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends CommandOpMode {

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private LauncherSubsystem launcherSubsystem;
    private ColorSensorSubsystem colorSensorSubsystem;

    private GamepadEx driverGamepad;
    private GamepadEx operatorGamepad;

    @Override
    public void initialize() {
        // Subsystems
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        launcherSubsystem = new LauncherSubsystem(hardwareMap);
        colorSensorSubsystem = new ColorSensorSubsystem(hardwareMap);

        // Gamepads
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);

        // Default Drive Command
        driveSubsystem.setDefaultCommand(new DefaultDriveCommand(
                driveSubsystem,
                () -> -driverGamepad.getLeftY(),
                () -> -driverGamepad.getLeftX(),
                () -> -driverGamepad.getRightX()
        ));

        // Intake Controls (Driver)
        new GamepadButton(driverGamepad, GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(new GrabWheelCommand(intakeSubsystem, 1.0));
        new GamepadButton(driverGamepad, GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(new GrabWheelCommand(intakeSubsystem, -1.0));

        // Launcher Controls (Operator)
        new GamepadButton(operatorGamepad, GamepadKeys.Button.A)
                .whenPressed(new LaunchRPMCommand(launcherSubsystem, 2500)); // Example RPM
        new GamepadButton(operatorGamepad, GamepadKeys.Button.B)
                .whenPressed(new LaunchRPMCommand(launcherSubsystem, 0));

        new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_UP)
                .whenPressed(new RampExtensionCommand(launcherSubsystem, 1.0));
        new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new RampExtensionCommand(launcherSubsystem, 0.0));

        new GamepadButton(operatorGamepad, GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(new LauncherRotationCommand(launcherSubsystem, 0.5));
        new GamepadButton(operatorGamepad, GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(new LauncherRotationCommand(launcherSubsystem, -0.5));

        // Flower Wheel (Operator)
        new GamepadButton(operatorGamepad, GamepadKeys.Button.X)
                .whileHeld(new FlowerWheelCommand(intakeSubsystem, 0.8));
    }
}
