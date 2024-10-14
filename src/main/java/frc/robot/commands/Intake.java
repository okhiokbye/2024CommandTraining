package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        //teehee
        System.out.println("I AM INITIALZIING");
    }
     @Override
     public void execute(){
       m_gun.runGreen(0.1,direc);

       
       SmartDashboard.putBoolean("BEMABERKA", beamBreak.get());
       System.out.println("I AM EXECUTING");
      if(isFinished()){
        m_gun.runBlueBlack(0.9, -direc);
        try {
          Thread.sleep(100);
      } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
      m_gun.runBlueBlack(0, -direc);
      
        
      }
     }
    @Override
    public void end(boolean interrupted){
        m_gun.runGreen(0.0, direc); 
        m_gun.runBlueBlack(0, direc);
        
    }

  // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
      System.out.println("I AM CHECKING IF I CAN END");
      double done = 0.0;
      SmartDashboard.putNumber("HOW MANY TIMES LOL THE DAMN THING", done );
      done++;
      if(!beamBreak.get()){
        System.out.println("I SHOULD END BRO WHY AM I NMOT ENDING");
      }
        return (!(beamBreak.get()));
  }
    
}
