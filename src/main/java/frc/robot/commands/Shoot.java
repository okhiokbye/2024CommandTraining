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
    private double time;
    public Shoot(Shooter gun, double speed, int direc){
        this.m_gun = gun;
        this.speed = 1;
        this.direc = direc;
        this.done = false;
        this.time = Timer.getFPGATimestamp();
    }
    @Override
    public void initialize() {
        // m_gun.runBlueBlack(0, direc);
        // time = Timer.getFPGATimestamp();
        // m_gun.runGreen(0,direc);
    }
     @Override
     public void execute(){
       
        // System.out.println("EXECUTING");
        // if(Timer.getFPGATimestamp() <= time+1){
        //     m_gun.runBlueBlack(speed, direc);
        //     System.out.println("ACCELERATING");
        // }
        // else if(Timer.getFPGATimestamp()<= time+1.5){
        //     m_gun.runGreen(speed, direc);
        //     m_gun.runBlueBlack(speed, direc);
        //     System.out.println("LAUNCHING");
        // }
        // else if(Timer.getFPGATimestamp()> time+1.5){
        //     done = true;
        // }

     }
    @Override
    public void end(boolean interrupted){
        // m_gun.runBlueBlack(0, direc);
        // m_gun.runGreen(0,direc);
        // System.out.println("STOP STOP STOP");
    }

  // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        System.out.println("AM CHECKING IF STOPPING TIME");
        return done;
  }
    
}
