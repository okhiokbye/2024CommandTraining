package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

public class ShooterArm extends PIDSubsystem{
    
    
   

    private final CANSparkMax a_motor1;
    private final CANSparkMax a_motor2;
    private RelativeEncoder m1_encoder;
   // private RelativeEncoder m2_encoder;
   
    private final RelativeEncoder a_encoder;
    private double output = 0;
    private double goal = 0;
    public double speed = 0.2;
    private final static PIDController m_armPIDController =
      new PIDController( //TOO STRONG
          0.40,
          0,
          0);
    public ShooterArm(PIDController controller) {
        super(m_armPIDController);
        a_motor1 =  new CANSparkMax(21, MotorType.kBrushless); //change ids later
        a_motor2 = new CANSparkMax(22, MotorType.kBrushless); 
        a_encoder = a_motor1.getEncoder();
        //a_encoder = new DutyCycleEncoder(5); // is absolute encoder yay no workie
        a_encoder.setPositionConversionFactor(1); //180*2*Math.PI
        zero();
    }
    public void zero(){
        a_encoder.setPosition(0);
      }

    @Override
    protected void useOutput(double output, double setpoint) {
        output = Math.min(speed, Math.max(-speed, output));
        a_motor1.set(output);
        a_motor2.set(-output); 
        
    }

    @Override
    protected double getMeasurement() {
        return a_encoder.getPosition();
    }
    
}
