package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveModuleSubsystem{
  private static final double kTurningEncoderResolution = 420;//410; //189*2; //383.6; // 424; // 424 pulses for one revolution

  private final TalonSRX m_driveMotor;
  private final TalonSRX m_turningMotor;

  private final Encoder m_turningEncoder;
  private final int turningMotorID;

  // Gains are for example purposes only - must be determined for your own robot!
  private final PIDController m_turningPIDController =
      new PIDController(
          3,
          0,
          0);


          // new PIDController(
          // 3,
          // 0,
          // 0);
  /**
   * Constructs a SwerveModule with a drive motor, turning motor, drive encoder and turning encoder.
   *
   * @param driveMotorChannel PWM output for the drive motor.
   * @param turningMotorChannel PWM output for the turning motor.
   * @param turningEncoderChannelA DIO input for the turning encoder channel A
   * @param turningEncoderChannelB DIO input for the turning encoder channel Bx
   */
    public SwerveModuleSubsystem(
    int driveMotorChannel,
    int turningMotorChannel,
    int turningEncoderChannelA,
    int turningEncoderChannelB) 
    {
    turningMotorID = turningMotorChannel;
    m_driveMotor = new TalonSRX(driveMotorChannel);
    m_turningMotor = new TalonSRX(turningMotorChannel);
    m_driveMotor.configPeakCurrentLimit(10);
    m_turningMotor.configPeakCurrentLimit(10);

    m_turningEncoder = new Encoder(turningEncoderChannelA, turningEncoderChannelB);

    m_turningEncoder.reset(); 
    // Set the distance (in this case, angle) in radians per pulse for the turning encoder.
    // This is the the angle through an entire rotation (2 * pi) divided by the
    // encoder resolution.
    m_turningEncoder.setDistancePerPulse(2 * Math.PI/kTurningEncoderResolution);

    // Limit the PID Controller's input range between -pi and pi and set the input
    // to be continuous.
    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }
    public void zero(){
    m_turningEncoder.reset();
    //m_turningMotor.configFactoryDefault();
    
      m_turningMotor.configPeakCurrentLimit(Constants.kPeakCurrentAmps, Constants.kTimeoutMs);
       m_turningMotor.configPeakCurrentDuration(Constants.kPeakTimeMs, Constants.kTimeoutMs);
     m_turningMotor.configContinuousCurrentLimit(Constants.kContinCurrentAmps, Constants.kTimeoutMs);
      m_turningMotor.enableCurrentLimit(true); // Honor initial setting
  
    //   /* setup a basic closed loop */
      //m_turningMotor.setNeutralMode(NeutralMode.Brake); // Netural Mode override 

    //***unfortunately we do not have the encoder wired directly to motor controller so we can't use this */
    //  m_turningMotor.configSelectedFeedbackSensor(  m_turningEncoder, // Sensor Type 
    //                                          Constants.PID_PRIMARY,      // PID Index
    //                                           Constants.kTimeoutMs);      // Config Timeout
  
    //  /* Ensure Sensor is in phase, else closed loop will not work. (since we arent running PID on motor controller we 
    //                                                                  technically dont need this)
    //     * Positive Sensor should match Motor Positive output (Green LED)
    //         */
      //m_turningMotor.setSensorPhase(true);  
  }
  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    SwerveModuleState state =
        SwerveModuleState.optimize(desiredState, new Rotation2d(m_turningEncoder.getDistance()));

    // Calculate the drive output from the drive PID controller.
    final double driveOutput = state.speedMetersPerSecond;

    // Calculate the turning motor output from the turning PID controller.
    final double turnOutput =
        m_turningPIDController.calculate(m_turningEncoder.getDistance(), state.angle.getRadians());
    SmartDashboard.putNumber("Wheel Angle" + turningMotorID, m_turningEncoder.getDistance());
    m_driveMotor.set(ControlMode.PercentOutput, Math.min(1,Math.max(-1, driveOutput/2)));
    m_turningMotor.set(ControlMode.PercentOutput, Math.min(1,Math.max(-1, turnOutput)));
  }

  public double getWhere(){
     return m_turningEncoder.getDistance();
  }
}