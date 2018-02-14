/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5906.robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5906.robot.commands.Sensitivity1;
import org.usfirst.frc.team5906.robot.commands.Sensitivity2;
import org.usfirst.frc.team5906.robot.commands.*;
import org.usfirst.frc.team5906.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this bclass or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@SuppressWarnings("deprecation")
public class Robot extends TimedRobot {
	public static AHRS ahrs;
	public static RobotDrive UpDrive;
	public static RobotDrive myDrive;
	final int kFrontLeftChannel=2;
	final int kRearLeftChannel=3;
	final int kFrontRightChannel=1;
	final int kRearRightChannel=0;
	final int UpChannel=4;
	final int UpChannel2=5;
	final int UpChannel3=6;
	final int UpChannel4=7;
	public static Compressor comp = new Compressor(0);
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static OI m_oi;
	public static int autox;
	public static int autoy;
	public static String GoToDrive = GoToDrive();
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 * ;D;
	 */
	public Robot(){
		autox = 0;
		autoy = 0;
		System.out.println("I'm waking up, give me 5 more minutes!");
		OI.button1.whenPressed(new Sensitivity1());
		OI.button2.whenPressed(new Sensitivity2());
		OI.button3.whenPressed(new UpDrive());
		OI.button4.whenPressed(new NoDrive());
		OI.button5.whenPressed(new DownDrive());
		OI.button6.whenPressed(new CompressorCommand());
		myDrive=new RobotDrive(kFrontLeftChannel, kRearLeftChannel, kFrontRightChannel, kRearRightChannel);
		UpDrive=new RobotDrive(UpChannel,UpChannel2,UpChannel3,UpChannel4);
		myDrive.setInvertedMotor(MotorType.kFrontLeft, true);
		myDrive.setInvertedMotor(MotorType.kRearLeft, true);
		myDrive.setExpiration(0.1);
		comp.setClosedLoopControl(true);
		comp.setClosedLoopControl(false);
		try {
			ahrs = new AHRS(SerialPort.Port.kUSB1);
			ahrs.enableLogging(true);
		} catch (RuntimeException ex ) {
			System.out.println("No Gyro! (╯°□°）╯︵ ┻━┻");
			DriverStation.reportError("Error instantiating navX MVP: " + ex.getMessage(), true);
			
		}
		
	}
	
	
	
	
	private static String GoToDrive() {
		//This should make the robot go to or a little bit over the x and y
		System.out.println("Ok! Calculating path! /(>‸<)\\");
		while (Robot.ahrs.getDisplacementX() <= Robot.autox){
			Robot.myDrive.mecanumDrive_Cartesian(1,0,0,0);
		}
		if(Robot.ahrs.getDisplacementX() >= Robot.autox){
		System.out.println("Ok! half way there! (･ω･)");
			Robot.myDrive.mecanumDrive_Cartesian(0,0,0,0);
		}
		System.out.println("Lets keep moving! └(>ω<。)┐-=≡");
		while (Robot.ahrs.getDisplacementY() <= Robot.autoy){
			Robot.myDrive.mecanumDrive_Cartesian(0,1,0,0);
		}
		if(Robot.ahrs.getDisplacementY() >= Robot.autoy){
			Robot.myDrive.mecanumDrive_Cartesian(0,0,0,0);
		}
		System.out.println("I did it! (๑˃̵ᴗ˂̵)و");
		return "Done";
	}




	@Override
	public void robotInit() {
		System.out.println("I'm up! \\≧Д≦/");
		m_oi = new OI();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		System.out.println("Naptime I guess... (*-ω-)...zzzZZZ");
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		System.out.println("Leave it to me! （⌒▽⌒ゞ)");
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		GoToDrive();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		System.out.println("Ok, your in command! ^o^");
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		myDrive.setSafetyEnabled(true);
		while(isOperatorControl() && isEnabled()){
			double X = OI.mecstick.getX();
			double Y = OI.mecstick.getY();
			double Z = OI.mecstick.getZ();
			//Send all of the ahrs data to the smartdashboard, to do something useful with it
			SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
            SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
            SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
            SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
            SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
            SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
            SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
            SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
            SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());
            SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
            SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
            SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
            SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());
			SmartDashboard.putNumber("Sensitivity", Sensitivity1.c);
			myDrive.mecanumDrive_Cartesian(X*Sensitivity1.c, Y*Sensitivity1.c, Z*Sensitivity1.c, 0);
			Timer.delay(0.005);
			Scheduler.getInstance().run();
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		System.out.println("Ok, but I'm not very good at tests! (>﹏>')");
	}
}
