// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private final TalonFX motor;
  private final DigitalInput lim;
  private boolean capture;
  private final VelocityVoltage m_velocity = new VelocityVoltage(0);
  private final PositionVoltage m_voltagePosition = new PositionVoltage(0, 0, false, 0, 1, false, false, false);

  public Intake() {
    motor = new TalonFX(IntakeConstants.kID);
    lim = new DigitalInput(IntakeConstants.kDIO);
    TalonFXConfiguration config = new TalonFXConfiguration();
    config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
    config.Slot0.kP = IntakeConstants.vkP;
    config.Slot1.kP = IntakeConstants.pkP;
    motor.getConfigurator().apply(config);
  }

  public Command in() {
    return run(
      () -> {
        if(!capture) {
          motor.setControl(m_velocity.withVelocity(IntakeConstants.kInVel));
        }
        else {
          motor.set(0);
        }
      }
    );
  }

  public void hold(double setpoint) {
    motor.setControl(m_voltagePosition.withPosition(setpoint));
  }

  public boolean isCapture() {
    return !lim.get();
  }

  public double getPos() {
    return motor.getPosition().getValueAsDouble();
  }

  @Override
  public void periodic() {
    capture = isCapture();
  }
}
