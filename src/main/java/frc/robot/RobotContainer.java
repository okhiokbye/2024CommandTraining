// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.AimArm;
//import frc.robot.Constants.OperatorConstants;
//import frc.robot.commands.Autos;
import frc.robot.commands.Drive;
import frc.robot.commands.Intake;
import frc.robot.commands.Shoot;
//import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterArm;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
//import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

// Things to do: telemetry,intake stop unjank, shoot varying speed??

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_swerve = new DriveSubsystem();
  private final static PIDController m_armPIDController =
      new PIDController( //TOO STRONG
          0.40,
          0,
          0);
  private final Shooter m_gun = new Shooter();
  private final ShooterArm m_arm = new ShooterArm(m_armPIDController, m_gun);
  private final DigitalInput laserSticky = new DigitalInput(4);
  // Replace with CommandPS4Controller or CommandJoystick if needed
 private final CommandJoystick m_driverJoystick = new CommandJoystick(0);
  private final CommandJoystick m_aimJoystick = new CommandJoystick(1);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
   
    m_swerve.setDefaultCommand(new Drive(
                m_swerve,
                () -> m_driverJoystick.getRawAxis(0),
                () -> m_driverJoystick.getRawAxis(1),
                () -> m_driverJoystick.getRawAxis(2)
                ));
    

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
      m_aimJoystick.button(5).onTrue(new AimArm(m_arm, -23.0, -1));
      m_aimJoystick.button(3).onTrue(new AimArm(m_arm, -78.0, 1));
      m_aimJoystick.button(6).onTrue(new AimArm(m_arm, 0,1));
      m_aimJoystick.button(1).onTrue(new Shoot(m_gun, 1.0, -1));
      m_aimJoystick.button(2).whileTrue(new Intake(m_gun, ()->laserSticky.get()));

      
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  //     
}
