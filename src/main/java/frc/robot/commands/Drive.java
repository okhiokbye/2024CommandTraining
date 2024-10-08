// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/** An example command that uses an example subsystem. */
public class Drive extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  //private final DriveSubsystem m_subsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  private final DriveSubsystem swerveSubsystem;
  private final Supplier<Double> xSpdFunction, ySpdFunction, rotSpdFunction;

  private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

  public Drive(DriveSubsystem subsystem,
                      Supplier<Double> xSpdFunction,
                      Supplier<Double> ySpdFunction,
                      Supplier<Double> rotSpdFunction
                       ) {
    this.swerveSubsystem = subsystem;
    this.xSpdFunction = xSpdFunction;
    this.ySpdFunction = ySpdFunction;
    this.rotSpdFunction = rotSpdFunction;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


    // 1. Get real-time joystick inputs
    double xSpeed = MathUtil.applyDeadband(xSpdFunction.get(), 0.2);
    double ySpeed = MathUtil.applyDeadband(ySpdFunction.get(), 0.2);
    double turningSpeed = MathUtil.applyDeadband(rotSpdFunction.get(), 0.2);

        // 2. Apply deadband
    
        // 3. Make the driving smoother
    xSpeed = m_xspeedLimiter.calculate(xSpeed)*5; //times max constant ;
    ySpeed = m_yspeedLimiter.calculate(ySpeed)*5; // times max constant;
    turningSpeed = m_rotLimiter.calculate(turningSpeed)*Math.PI; 
            //* DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond;

    swerveSubsystem.drive(xSpeed,ySpeed,turningSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerveSubsystem.drive(0,0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
