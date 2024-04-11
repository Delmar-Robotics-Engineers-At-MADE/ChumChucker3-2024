// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.util.PIDConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final double LEFT_Y_DEADBAND = 0;
    public static final double LEFT_X_DEADBAND = 0;
  }

  public static final class AutonConstants{
    public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
    public static final PIDConstants ANGLE_PID   = new PIDConstants(0.4, 0, 0.01);
  }

  public static class IntakeConstants {

    public static final double vkP = 2;
    public static final double pkP = 0.1;
    public static final double kInVel = 0;
    public static int kID = 5;
    public static int kDIO = 0;  
  }

  public static class  ForkConstants {

    public static final int kID = 0;
    public static final int kDIO = 1;
  
    public static final double kP = 5.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
    public static final double kFF = 0.0;
    public static final double kUpperLim = 0;
    public static final double kLowerLim = 0;
    public static final double kNudgeCount = 0;
  }
}
