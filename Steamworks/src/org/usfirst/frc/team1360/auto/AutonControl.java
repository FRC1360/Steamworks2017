package org.usfirst.frc.team1360.auto;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.mode.DefaultMode;
import org.usfirst.frc.team1360.auto.step1.DriveLikeYouStoleItAndHitTheHopper;
import org.usfirst.frc.team1360.auto.step1.DrivePIDTest;
import org.usfirst.frc.team1360.auto.step1.DriveToBaseline;
import org.usfirst.frc.team1360.auto.step1.gear.DriveToGearLeft;
import org.usfirst.frc.team1360.auto.step1.gear.DriveToGearMiddle;
import org.usfirst.frc.team1360.auto.step1.gear.DriveToGearRight;
import org.usfirst.frc.team1360.auto.step2.red.GearMiddleToBallRed;
import org.usfirst.frc.team1360.auto.step2.NeutralZoneAfterSideGear;
import org.usfirst.frc.team1360.auto.step2.ToBoilerFromBlue;
import org.usfirst.frc.team1360.auto.step2.ToBoilerFromRed;
import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.util.Debugger;
import org.usfirst.frc.team1360.server.components.AutonSelectorComponent;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonControl {
private static AutonControl instance;
    
    public static final int NUM_ARRAY_MODE_STEPS = 3;
   
    
    private int autonDelay;
    private long autonStartTime;
    
    private boolean running;
    
    private int curAutonStepToSet = 0;
    private int[] autonSubmodeSelections = new int[NUM_ARRAY_MODE_STEPS];
    private ArrayList<ArrayList<AutonMode>> autonSteps = new ArrayList<>();
    
    private int currIndex;
    private AutonCommand[] commands;
        
    private String autoSelectError = "NO ERROR";
    
    public static AutonControl getInstance() {
        if(instance == null) {
            instance = new AutonControl();
        }
        return instance;
    }

    private AutonControl() {
        this.autonDelay = 0;
        this.currIndex = 0;
        
        for(int i = 0; i < NUM_ARRAY_MODE_STEPS; i++) {
        	this.autonSteps.add(new ArrayList<AutonMode>());
        	this.autonSubmodeSelections[i] = 0; // default to default auto modes 
        }
        	
        // GOTCHA: remember to put all auton modes here
        
        // --- STEP 1 SUBMODES
        ArrayList<AutonMode> step1 = this.autonSteps.get(0);
        step1.add(new DefaultMode()); //0
        //step1.add(new DrivePIDTest());
        step1.add(new DriveToGearRight()); 
        step1.add(new DriveToGearLeft());
        //step1.add(new DriveToBaseline());
        step1.add(new DriveToGearMiddle()); 
        
        
        // --- STEP 2 SUBMODES
        ArrayList<AutonMode> step2 = this.autonSteps.get(1);
        step2.add(new DefaultMode()); //0
        step2.add(new NeutralZoneAfterSideGear());
        step2.add(new ToBoilerFromBlue());
        step2.add(new ToBoilerFromRed());
        //step2.add(new GearMiddleToBallRed());
       
       
        
        // --- STEP 3 SUBMODES
        ArrayList<AutonMode> step3 = this.autonSteps.get(2);
        step3.add(new DefaultMode()); //0
        
        
        
       
    }

    public void initialize() {
        Debugger.println("START AUTO");
        
        this.currIndex = 0;
        this.running = true;

        // initialize auton in runCycle
        AutonBuilder ab = new AutonBuilder();

        // add auton commands from all the different steps
        for(int i = 0; i < this.autonSteps.size(); i++) {
        	this.autonSteps.get(i).get(this.autonSubmodeSelections[i]).addToMode(ab);
        }
        
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

    public void updateModes() {
        HumanInput driverIn = HumanInput.getInstance();
        
        if(driverIn.getAutonStepIncrease()) {
        	this.curAutonStepToSet++;
        	this.curAutonStepToSet = Math.min(this.curAutonStepToSet, this.autonSteps.size() - 1);
        }
        
        if(driverIn.getAutonStepDecrease()) {
        	this.curAutonStepToSet--;
        	this.curAutonStepToSet = Math.max(this.curAutonStepToSet, 0);
        }
        
       	boolean updatingAutoMode = false;

        try {
        
        	
        if(driverIn.getAutonSetModeButton()) {
            updatingAutoMode = true;
        	
        	double val = driverIn.getAutonSelectStick();
            val = (val + 1) / 2.0;  // make it positive and between 0 - 1.0
            
            
            
            
            // figure out which auton mode is being selected
            int autonMode = (int)(val *  this.autonSteps.get(this.curAutonStepToSet).size());
            
            
            
            // make sure we didn't go off the end of the list
            autonMode = Math.min(autonMode, this.autonSteps.get(this.curAutonStepToSet).size() - 1);          
            if(autonMode < 0 ){
            	autonMode = 0;
            }
            
            this.autonSubmodeSelections[this.curAutonStepToSet] = autonMode;
            

           
            
            /*
            if(val < 0) { this.autonMode = 0; }
            else { this.autonMode = 1; }
         */   
        } else if(driverIn.getAutonSetDelayButton()) {
            this.autonDelay = (int)((driverIn.getAutonSelectStick() + 1) * 5.0);
            if(this.autonDelay < 0 ) {
            	this.autonDelay =0;
            }
        }
        
        } catch(Exception e) {
        	//this.autonMode = 0;
        	// TODO: some kind of error catching
        	
        	
        	StringWriter sw = new StringWriter();
        	e.printStackTrace(new PrintWriter(sw));
        	
        	
        	this.autoSelectError = sw.toString();
        
        }
        
        // display steps of auto
        for(int i = 0; i < autonSteps.size(); i++) {
	        // name of the current auton mode
	        String name = this.autonSteps.get(i).get(this.autonSubmodeSelections[i]).getClass().getName();
	
	        // make sure there is a '.'
	        if(name.lastIndexOf('.') >= 0) {
	            // get just the last bit of the name
	            name = name.substring(name.lastIndexOf('.'));
	        }
	        
	        String outputString = "" + autonSubmodeSelections[i] + name + "";
	        
	        SmartDashboard.putString("Auton Step " + (i+1) + ": ", outputString);
	       
	        if(updatingAutoMode) {
            	//System.out.print(this.autonSubmodeSelections[i] + "-");
	        	System.out.println("Step " + (i + 1) + ": " + outputString);
	        }
	        	
	        	// System.out.println();
	        
	        //SmartDashboard.putString("Auton Error: ", this.autoSelectError);
        }
        
        if(updatingAutoMode) {
        	System.out.println("----------------------------------");
        }
        
        // step we are currently modifying
        SmartDashboard.putNumber("SETTING AUTON STEP: ", this.curAutonStepToSet+1);
        
        // delay 
        String delayAmt = "";
        if(this.autonDelay < 10) {
            // pad in a blank space for single digit delay
            delayAmt = " " + this.autonDelay;
        } else {
            delayAmt = "" + this.autonDelay;
        }
        SmartDashboard.putNumber("Auton Delay: ", this.autonDelay);


    }

}
