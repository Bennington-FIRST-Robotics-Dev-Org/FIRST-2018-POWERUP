/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5906.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5906.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class CompressorCommand extends Command {
	private static boolean enabled;

	public CompressorCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.kExampleSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	public void initialize() {
		//boolean enabled = Robot.comp.enabled();
		//boolean pressureSwitch = Robot.comp.getPressureSwitchValue();
		//double current = Robot.comp.getCompressorCurrent();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		//System.out.println("Move like a butterfly, sting like a bee! (∩`ω´)⊃))");
		//if(Robot.comp.enabled() == true){
			//CompressorCommand.enabled = false;
		//} else {
			//CompressorCommand.enabled = true;
		//}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
