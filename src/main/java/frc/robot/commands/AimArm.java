package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class AimArm extends Command {
    private final Arm m_arm;
    private final double setpoint;
    public AimArm(Arm arm, double setpoint){
        this.m_arm = arm;
        this.setpoint = setpoint;
    }
    @Override
    public void initialize() {
        m_arm.setSetpoint(m_setpoint);
        m_arm.enable();
  }

  // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_arm.getController().atSetpoint();
  }

}
