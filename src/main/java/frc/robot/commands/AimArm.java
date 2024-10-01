package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterArm;


public class AimArm extends Command {
    private final ShooterArm m_arm;
    private final double setpoint;
    private final int direc;
    public AimArm(ShooterArm arm, double setpoint, int direc){
        this.m_arm = arm;
        this.setpoint = setpoint;
        this.direc=direc;
    }
    @Override
    public void initialize() {
        m_arm.setSetpoint(setpoint);
        m_arm.enable();
        m_arm.setGoal(direc);
  }

  // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_arm.getController().atSetpoint();
  }

}
