package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.AnalogGyro;
 import com.ctre.phoenix.sensors.Pigeon2;
 import com.ctre.phoenix.sensors.Pigeon2Configuration;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;



public class DriveSubsystem extends SubsystemBase {
  public static final double kMaxSpeed = 5.0; // 3 meters per second
  public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second

  private final Translation2d m_frontLeftLocation = new Translation2d(0.2096, 0.2096);
  private final Translation2d m_frontRightLocation = new Translation2d(0.2096, -0.2096);
  private final Translation2d m_backLeftLocation = new Translation2d(-0.2096, 0.2096);
  private final Translation2d m_backRightLocation = new Translation2d(-0.2096, -0.2096);

  
  private final AHRS m_gyro = new AHRS(SerialPort.Port.kMXP);

  // B
  private final SwerveModule m_frontLeft = new SwerveModule(15, 4, 1, 0 );
  // A
  private final SwerveModule m_frontRight = new SwerveModule(3,8, 9, 8);
  // D
  private final SwerveModule m_backLeft = new SwerveModule(6, 9, 3, 2);
  // C
  private final SwerveModule m_backRight = new SwerveModule(12, 11, 7, 6);

  // private final SwerveModule m_frontLeft = new SwerveModule(1, 2, 2, 3);
  // private final SwerveModule m_frontRight = new SwerveModule(3, 4, 6, 7);
  // private final SwerveModule m_backLeft = new SwerveModule(5, 6, 10, 11);
  // private final SwerveModule m_backRight = new SwerveModule(7, 8, 14, 15);


  private final SwerveDriveKinematics m_kinematics =
      new SwerveDriveKinematics(
          m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);
          
          // public Drivetrain() {
  //   Pigeon2Configuration config = new Pigeon2Configuration();
  //   config.MountPoseYaw =0;
  //   config. MountPosePitch =0;
  //   config.MountPoseRoll =0;
  //   m_gyro.configAllSettings(config);
  // }

  /**
   * Method to drive the robot using joystick info.
   *
   * @param xSpeed Speed of the robot in the x direction (forward).
   * @param ySpeed Speed of the robot in the y direction (sideways).
   * @param rot Angular rate of the robot.
   * @param fieldRelative Whether the provided x and y speeds are relative to the field.
   */
    public void drive(double xSpeed, double ySpeed, double rot) {
    var swerveModuleStates =   
    //  m_kinematics.toSwerveModuleStates(
    //   ChassisSpeeds.discretize(

    //           ChassisSpeeds.fromFieldRelativeSpeeds(
    //               xSpeed, ySpeed, rot, new Rotation2d(m_gyro.getYaw()*(Math.PI/180))), 0.02
    //                 )
    //               );
    m_kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(ySpeed, xSpeed, -rot , new Rotation2d(m_gyro.getYaw())));
    //SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, kMaxSpeed);
    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_frontRight.setDesiredState(swerveModuleStates[1]);
    m_backLeft.setDesiredState(swerveModuleStates[2]);
    m_backRight.setDesiredState(swerveModuleStates[3]);
    System.out.println(m_backLeft.getWhere() + "THIS");
    System.out.println(m_backRight.getWhere() + "THISBITVH");
  }

  public void resetModule() {
    m_frontLeft.zero();
    m_frontRight.zero();
    m_backLeft.zero();
    m_backRight.zero();
    m_gyro.reset();
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run use for telemetry
    System.out.println("CURRENT YAW:" + m_gyro.getYaw());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

}
