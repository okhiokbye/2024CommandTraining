package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;




/*
 *  DEPRECATED OLD CODE FROM OLD ROBOT JANK 10000     
 * 
 * 
 *  DEPRECATED OLD CODE FROM OLD ROBOT JANK 10000     
 * 
 *  DEPRECATED OLD CODE FROM OLD ROBOT JANK 10000     
 * 
 * 
 * 
 * 
 * 
 * 
 *  DEPRECATED OLD CODE FROM OLD ROBOT JANK 10000     
 */
















public class ShooterSubsystem extends SubsystemBase{
    private final CANSparkMax m_intake1 ;
    private final CANSparkMax m_intake2 ; 
    private final CANSparkMax m_arm1;
    private final CANSparkMax m_arm2; // change can ids later
    private final DigitalInput laserStick;
    private final int goal; // 0 for amp, 1 for speaker

    private boolean isHoldingDownShooter = false;
    private boolean hasIntakeFinished = false;
    private int direc = 1;

    public ShooterSubsystem(){
        m_intake1 =  new CANSparkMax(16, MotorType.kBrushless); //black/blue, shooter
        m_intake2 = new CANSparkMax(2, MotorType.kBrushless); //green wheels, intake
        m_arm1= null;
        m_arm2 = null;
        laserStick = new DigitalInput(4);
        goal =1; //inital goal speaker
    }

    public void printLaserStick(){
        System.out.println(laserStick.get());
    }
   
    public void aim(int n){
        
        if(goal ==0 ){
            direc = 1;
        }
        else if (goal == 1){
            direc = -1;
        }
        else{
            direc  = 1;
        }
    }
    public void intake() {
       isHoldingDownShooter = true; // shooter button pressed
                  m_intake2.set(.80);

                  //if(1==1) return;
        
        
        if(hasIntakeFinished && isHoldingDownShooter) 
            return;                    

        if(laserStick.get()){
             // run neo500s at 60% here, in correct direction
                   m_intake2.set(.9);

                  
        }
        else{
            m_intake2.set(-.9); // change to PID control later to fix scuffed
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            m_intake2.set(0);

            hasIntakeFinished = true;
            // intakeCorrect();

        }
        //otherwise do nothing
    }
    public void shoot(){

        direc = -1;

       
        if(goal == 0){
            //arm motors move up a little until correct encoder position (encoder not working?)
            m_intake1.set(-direc*.5);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m_intake2.set(-direc * 0.3); 

    
        }else{
            //arm motors move up until right encoder position (encoder not working?)
            m_intake1.set(-direc*1);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m_intake2.set(-direc * 0.95); 
            //shoot at whatever power we need to make it in amp, flip direction
        }
    }
    
    public void reverse() {
        m_intake2.set(-1);
        try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        m_intake2.set(0);
    }
    public void stop(){
        isHoldingDownShooter = false;
        hasIntakeFinished = false;
        m_intake2.set(0);
        m_intake1.set(0);
        
    }
    //public void stopShoot(){}
    public void deciderUp(){
        m_intake2.set(0.1);
    }
    public void deciderDown(){
        m_intake2.set(-0.1); 
    }
    public void shooterUp(){
        m_intake1.set(0.1); 
    }
    public void shooterDown(){
        m_intake1.set(-0.1); 
    }   
    public void intakeCorrect(){
        m_intake2.set(-0.5);
        try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        m_intake2.set(0);
    }

}