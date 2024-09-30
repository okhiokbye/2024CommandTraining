package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterArm;

public class Shoot extends Command {
    private final Shooter m_gun;
    private final double speed;
    private final int direc;
    private boolean done;
    private final double time;
    public Shoot(Shooter gun, double speed, int direc){
        this.m_gun = gun;
        this.speed = speed;
        this.direc = direc;
        this.done = false;
        this.time = Timer.getFPGATimestamp();
    }
    @Override
    public void initialize() {
        m_gun.runBlueBlack(0, direc);
    
        m_gun.runGreen(0,direc);
    }
     @Override
     public void execute(){
       
    
        if(time <= time+0.8){
            m_gun.runBlueBlack(speed, direc);
        }
        else if(time <= time+1.3){
            m_gun.runGreen(speed, direc);
        }
        else if(time> time+1.3){
            done = true;
        }

     }
    @Override
    public void end(boolean interrupted){
        m_gun.runBlueBlack(0, direc);
        m_gun.runGreen(0,direc);
    }

  // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return done;
  }
    
}
