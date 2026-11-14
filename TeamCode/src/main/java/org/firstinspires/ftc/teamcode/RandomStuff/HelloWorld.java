package org.firstinspires.ftc.teamcode.RandomStuff;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class HelloWorld extends OpMode {

    @Override
    public void init() {
        telemetry.addData("Hello", "Kevin");
    }

    @Override
    public void loop() {

    }

}
