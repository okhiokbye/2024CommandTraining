package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterArm;

public class Intake extends Command {
    private final Shooter m_gun;
    private final Supplier<Boolean> beamBreak;
    private final int direc;
    public Intake(Shooter gun, Supplier<Boolean> beamBreak){
        this.m_gun = gun;
        this.direc= -1;
        this.beamBreak = beamBreak;
    }
    @Override
    public void initialize() {
        
    }
     @Override
     public void execute(){
       m_gun.runGreen(0.6,direc);
        

     }
    @Override
    public void end(boolean interrupted){
        m_gun.runBlueBlack(0.9, direc); 
        // WAIT 0.1 SEC THEN STOP
        m_gun.runGreen(0,direc);
    }

  // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return beamBreak.get();
  }
    
}
