package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

public class ColorSensorSubsystem extends SubsystemBase {
    private final NormalizedColorSensor colorSensor;

    public ColorSensorSubsystem(HardwareMap hardwareMap) {
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color_sensor");
    }

    public NormalizedColorSensor getColorSensor() {
        return colorSensor;
    }
}
