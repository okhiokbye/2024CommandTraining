// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkLowLevel.MotorType;

import javax.lang.model.util.ElementScanner14;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
 
    
    private final CANSparkMax a_motor1;
    private final CANSparkMax a_motor2;
    private RelativeEncoder m1_encoder;
   // private RelativeEncoder m2_encoder;
    private final int select; //shooting in ur mom ur shooting in ur daughter
    private final RelativeEncoder a_encoder;
    private double output = 0;
    private double goal = 0;
    public double speed = 0.2;
    private final PIDController m_turningPIDController =
      new PIDController( //TOO STRONG
          0.40,
          0,
          0);
  public ArmSubsystem(){
        a_motor1 =  new CANSparkMax(21, MotorType.kBrushless); //change ids later
        a_motor2 = new CANSparkMax(22, MotorType.kBrushless); 
        a_encoder = a_motor1.getEncoder();
        //a_encoder = new DutyCycleEncoder(5); // is absolute encoder yay
        a_encoder.setPositionConversionFactor(1); //180*2*Math.PI
        select = 0; //speaker = 0, amp = 1
        zero();
  }
  public void aim(int n) {
      aim(n, false);
  }
  public void zero(){
    a_encoder.setPosition(0);

  }
  public void aim(int n, boolean isAuto){
    SmartDashboard.putNumber("arm encoder position", a_encoder.getPosition());
    SmartDashboard.putNumber("arm encoder position1", a_encoder.getPosition());

    if(isAuto) {

        // while(analog.getValue() < 100) {
        //     double output = speed;
        //     a_motor1.set(output);
        //     a_motor2.set(-output);
        //     System.out.println(analog.getValue());

        // }

        n = 200;
    }

    if( n == 0 ){ //speaker position
    
        double pos1 = a_encoder.getPosition();
        double output = m_turningPIDController.calculate(pos1, -23/*angle for speaker */);
        output = Math.min(speed, Math.max(-speed, output));
        a_motor1.set(output);
        a_motor2.set(-output);
        SmartDashboard.putNumber("LA OUTPUT", output);
    }
    else if (n == 1){ //amp position
        double pos1 = a_encoder.getPosition();
        double output = m_turningPIDController.calculate(pos1, -78/*angle for amp */);
        output = Math.min(speed, Math.max(-speed, output));
        a_motor1.set(output);
        a_motor2.set(-output);
    }
    else if (n == 2){ //intake position
          double pos1 = a_encoder.getPosition();
        double output = m_turningPIDController.calculate(pos1, 0/*angle for intake */);
        output = Math.min(speed, Math.max(-speed, output));
        a_motor1.set(output);
        a_motor2.set(-output);
    }else if (n == 34){ //intake position
          double pos1 = a_encoder.getPosition();
        double output = m_turningPIDController.calculate(pos1, -50/*angle for intake */);
        output = Math.min(speed, Math.max(-speed, output));
        a_motor1.set(output);
        a_motor2.set(-output);
    }
    else if(n == 69){
                
        double pos1 = a_encoder.getPosition();
        double output = m_turningPIDController.calculate(pos1, pos1);
        output = Math.min(speed, Math.max(-speed, output));
        a_motor1.set(output);
        a_motor2.set(-output);
    }
    else if (n == 32){ //auto speaker(angle) position
        double pos1 = a_encoder.getPosition();
        double output = m_turningPIDController.calculate(pos1, -20/*angle for amp */);
        output = Math.min(speed, Math.max(-speed, output));
        a_motor1.set(output);
        a_motor2.set(-output);
    }
    
    else {
                
        double output = 0;
        output = Math.min(speed, Math.max(-speed, output));
        a_motor1.set(output);
        a_motor2.set(-output);
    }
    double pos1 = a_encoder.getPosition();
    System.out.println(pos1);
  }
    
    /**
     * An example method querying a boolean state of the subsystem (for example, a digital sensor).
     *
     * @return value of some boolean subsystem state, such as a digital sensor.
     */
  public void run(double output){
    //  double output = m_turningPIDController.calculate(a_encoder.getPosition(), 10/*angle for speaker */);
      output = Math.min(speed, Math.max(-speed, output));
      a_motor1.set(output);
      a_motor2.set(-output);
  }
  public void armDown(){
    // max speed is speed!!!!!!!! DO NOT GO OVER!!!
  //SmartDashboard.putNumber("arm encoder position", m1_encoder.getPosition());
  // //  System.out.println("arm down" + m1_encoder.getPosition());   
    //  System.out.println(System.currentTimeMillis());
  
    a_motor1.set(speed);
      a_motor2.set(-speed);
        // goal += (10/360.);
  }
  public void armUp(){
    //    martDashboard.putNumber("arm encoder position", m1_encoder.getPosition());

  
        a_motor1.set(-speed);
        a_motor2.set(speed);
        // goal -= (10/360.);

  //     System.out.println("absolute " + a_encoder.getAbsolutePosition());  
  //     System.out.println("relative " + a_encoder.getDistance());
    //    System.out.println(System.currentTimeMillis());
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run use for telemetry/PID
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}