package org.usfirst.frc.team1360.auto.mode;

import java.util.ArrayList;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.util.AutonStop;

public class AutonBuilder {
    private ArrayList<AutonCommand> cmds;
    
    public AutonBuilder() {
        this.cmds = new ArrayList<>();
    }
    
    public void addCommand(AutonCommand cmd) {
        this.cmds.add(cmd);
    }
    
    public AutonCommand[] getAutonList() {
        // add a stop at the end of every auton mode
        this.cmds.add(new AutonStop());
        
        AutonCommand[] result = new AutonCommand[this.cmds.size()];
        result = this.cmds.toArray(result);
        
        return result;
    }
}
