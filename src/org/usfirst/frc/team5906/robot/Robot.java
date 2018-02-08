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
import org.usfirst.frc.team5906.robot.commands.ExampleCommand;
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
	AHRS ahrs;
	RobotDrive myDrive;
	final int kFrontLeftChannel=3;
	final int kRearLeftChannel=4;
	final int kFrontRightChannel=2;
	final int kRearRightChannel=1;
	public static final ExampleSubsystem kExampleSubsystem
			= new ExampleSubsystem();
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public Robot(){
		OI.button1.whenPressed(new ExampleCommand(1));
		OI.button2.whenPressed(new ExampleCommand(2));
		myDrive=new RobotDrive(kFrontLeftChannel, kRearLeftChannel, kFrontRightChannel, kRearRightChannel);
		myDrive.setInvertedMotor(MotorType.kFrontLeft, true);
		myDrive.setInvertedMotor(MotorType.kRearLeft, true);
		myDrive.setExpiration(0.1);
		try {
			ahrs = new AHRS(SerialPort.Port.kUSB1);
			ahrs.enableLogging(true);
		} catch (RuntimeException ex ) {
			DriverStation.reportError("Error instantiating navX MVP: " + ex.getMessage(), true);
			
		}
		
	}
	
	
	
	
	@Override
	public void robotInit() {
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
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
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
			SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
			//System.out.println("X is: " + X + " Y is: " + Y + " Z is: " + Z);
			//System.out.println("X*c is: " + X*c + " Y*c is: " + Y*c + " Z*c is: " + Z*c);
			System.out.println(ExampleCommand.c);
			myDrive.mecanumDrive_Cartesian(X*ExampleCommand.c, Y*ExampleCommand.c, Z*ExampleCommand.c, 0);
			Timer.delay(0.005);
			Scheduler.getInstance().run();
		
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		SmartDashboard.putBoolean("IMU_Connected", ahrs.isConnected());
	}
}
