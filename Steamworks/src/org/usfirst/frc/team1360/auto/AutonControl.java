package org.usfirst.frc.team1360.auto;

import java.util.ArrayList;

import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.mode.DefaultMode;
import org.usfirst.frc.team1360.auto.step1.DriveToBaselineMiddle;
import org.usfirst.frc.team1360.auto.step1.DriveToBaselineOutside;
import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.auto.step1.DropOffGearLeft;
import org.usfirst.frc.team1360.auto.step1.DropOffGearMiddle;
import org.usfirst.frc.team1360.auto.step1.DropOffGearRight;
import org.usfirst.frc.team1360.auto.step1.balls.DropOffBallsBlue1;
import org.usfirst.frc.team1360.auto.step1.balls.DropOffBallsBlue2;
import org.usfirst.frc.team1360.auto.step1.balls.DropOffBallsBlue3;
import org.usfirst.frc.team1360.auto.step1.balls.DropOffBallsRed1;
import org.usfirst.frc.team1360.auto.step1.balls.DropOffBallsRed2;
import org.usfirst.frc.team1360.auto.step1.balls.DropOffBallsRed3;
import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.util.Debugger;
import org.usfirst.frc.team1360.server.components.AutonSelectorComponent;

public class AutonControl {
	private static AutonControl instance;
    
    public static final int NUM_ARRAY_MODE_STEPS = 3;
   
    
    private int autonDelay;
    private long autonStartTime;
    
    private boolean running;
    
    private ArrayList<ArrayList<AutonMode>> autonSteps = new ArrayList<>();
    
    private int currIndex;
    private AutonCommand[] commands;
    
    private AutonSelectorComponent selector;
    
    public static AutonControl getInstance() {
        if(instance == null) {
            instance = new AutonControl();
        }
        return instance;
    }

    private AutonControl() {
    	System.out.println("Auton control setup begin");
    	
        this.autonDelay = 0;
        this.currIndex = 0;
        
        for(int i = 0; i < NUM_ARRAY_MODE_STEPS; i++)
        	this.autonSteps.add(new ArrayList<AutonMode>());
        	
        // GOTCHA: remember to put all auton modes here
        
        // --- STEP 1 SUBMODES
        ArrayList<AutonMode> step1 = this.autonSteps.get(0);
        step1.add(new DefaultMode());
        step1.add(new DriveToBaselineMiddle());
        step1.add(new DriveToBaselineOutside());
        step1.add(new DropOffGearMiddle());
        step1.add(new DropOffGearRight());
        step1.add(new DropOffGearLeft());
        step1.add(new DropOffBallsBlue1());
        step1.add(new DropOffBallsBlue2());
        step1.add(new DropOffBallsBlue3());
        step1.add(new DropOffBallsRed1());
        step1.add(new DropOffBallsRed2());
        step1.add(new DropOffBallsRed3());
        
        
        // --- STEP 2 SUBMODES
        ArrayList<AutonMode> step2 = this.autonSteps.get(1);
        step2.add(new DefaultMode()); //0
       
       
        
        // --- STEP 3 SUBMODES
        ArrayList<AutonMode> step3 = this.autonSteps.get(2);
        step3.add(new DefaultMode()); //0
        
        
        //selector = new AutonSelectorComponent(autonSteps);
        //Robot.getInstance().getConnection().addComponent(selector, 0);

    	//System.out.println("Auton control setup end");
    }

    public void initialize() {
        Debugger.println("START AUTO");
        
        this.currIndex = 0;
        this.running = true;

        // initialize auton in runCycle
        AutonBuilder ab = new AutonBuilder();
        
        for (AutonMode mode : selector.getSelections())
        	mode.addToMode(ab);
        
        // get the full auton mode
        this.commands = ab.getAutonList();

        this.autonStartTime = System.currentTimeMillis();
        
        // clear out each components "run seat"
        AutonCommand.reset();
    }
    
    public void runCycle() {
        // haven't initialized list yet
        long timeElapsed = System.currentTimeMillis() - this.autonStartTime;
        if(timeElapsed > this.getAutonDelayLength() && this.running) {
            Debugger.println("Current index " + this.currIndex, "QTIP");
            
            
                // start waiting commands
                while(this.currIndex < this.commands.length &&
                        this.commands[this.currIndex].checkAndRun()) {
                    this.currIndex++;
               
            }
            // calculate call for all running commands
            AutonCommand.execute();
        } else {
            RobotOutput.getInstance().stopAll();
        }

    
    }
    
    public void stop() {
        this.running = false;
    }
    
    public long getAutonDelayLength() {
        return (long)(this.autonDelay * 500);
    }
}
