package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import java.util.function.Supplier;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private final CANSparkMax m_intake1;
    private final CANSparkMax m_intake2; 
    private final CANSparkMax m_arm1;
    private final CANSparkMax m_arm2; // change can ids later
    
    private final int goal; // 0 for amp, 1 for speaker

    private boolean isHoldingDownShooter = false;
    private boolean hasIntakeFinished = false;
    private int direc = 1;
    private Supplier<Boolean> beamBreak;
    public Shooter(Supplier<Boolean> beamBreak){
        m_intake1 =  new CANSparkMax(16, MotorType.kBrushless); //black/blue, shooter
        m_intake2 = new CANSparkMax(2, MotorType.kBrushless); //green wheels, intake
        m_arm1= null;
        m_arm2 = null;
        this.beamBreak =beamBreak;

      
        goal =1; //inital goal speaker
    }
    // @Override
    // public void periodic(){
    //     SmartDashboard.putBoolean("BEAMBREAKER", )
    // }
    public void runBlueBlack(double output, double direc){
        m_intake1.set(output*direc);
    }
    public void runGreen(double output, double direc){
        m_intake2.set(output*direc);
    }
    @Override
    public void periodic(){
        System.out.println("BEAMBREAK???" + beamBreak.get());
        SmartDashboard.putBoolean("BEAM??", beamBreak.get());
    }
  
    public void setDirec(int direc){
        this.direc= direc;
    }
    public Command intakeCmd(){ // set green rollers, then when the beam break sees hoop stop it (no intake correction)
        return new StartEndCommand( // this whole thign is probably just a functional command but lol
            ()->m_intake2.set(0.9),
            ()->m_intake2.set(0)
        ).until(()->!beamBreak.get());
        //what the fuck did i    cook below lmao
        //return this.runOnce(()->m_intake2.set(0.9)).andThen(WaitUntil(beamBreak.get()).andThen(this.runOnce(()->m_intake2.set(0))));
    }
    public Command intakeCorrectCmd(){ //correcting for intake here
        return new StartEndCommand(
            ()->m_intake1.set(0.9),
            ()->m_intake1.set(0)).withTimeout(0.1);
    }
    public Command spinCmd(double bSpeed,double gSpeed, int direc){
        return Commands.ParallelDeadlineGroup(()->m_intake1.set(bSpeed*direc), ()->m_intake2.set(gSpeed*direc));
    }


}
