// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.ForkConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Fork extends SubsystemBase {
  private final CANSparkMax motor;
  private final RelativeEncoder encoder;
  private final SparkPIDController pid;
  private final DigitalInput lim;
  private boolean homed;
  private boolean overrun;
  public Fork() {
    motor = new CANSparkMax(ForkConstants.kID, MotorType.kBrushless);
    lim = new DigitalInput(ForkConstants.kDIO);
    encoder = motor.getEncoder();
    pid = motor.getPIDController();
    pid.setFeedbackDevice(encoder);
    pid.setP(ForkConstants.kP);
    pid.setI(ForkConstants.kI);
    pid.setD(ForkConstants.kD);
    pid.setFF(ForkConstants.kFF);
  }

  private void runToPos(double pos) {
    if(encoder.getPosition() > ForkConstants.kUpperLim || encoder.getPosition() < ForkConstants.kLowerLim) {
      motor.set(0);
    }
    pid.setReference(pos, ControlType.kPosition);
  }

  public Command toPos(double pos) {
    if(!homed || getPos() == -1) {
      return runOnce(()-> motor.set(0));
    }
    else {
      return runOnce(()-> runToPos(pos));
    }
  }

  public Command nudge(boolean out) {
    if(out) {
      return toPos(getPos() + ForkConstants.kNudgeCount);
    }
    else {
      return toPos(getPos() - ForkConstants.kNudgeCount);
    }
  }

  private boolean isAtLimit() {
    return !lim.get();
  }

  public boolean isHomed() {
    return homed;
  }

  public double getPos() {
    if(!homed) {
      return -1;
    }
    else {
      return encoder.getPosition();
    }
  }

  @Override
  public void periodic() {
    if(isAtLimit()) {
      encoder.setPosition(0);
      homed = true;
    }
    if(encoder.getPosition() > ForkConstants.kUpperLim || encoder.getPosition() < ForkConstants.kLowerLim) {
      overrun = true;
    }
    else {
      overrun = false;
    }
  }
}
