# <img src="http://1360.ca/wp-content/uploads/2017/02/cropped-1360-Logo-no-text-4.png" width="50"/> Steamworks 2017

## Prerequisites

1. WPILib - https://wpilib.screenstepslive.com/s/4485/m/13809/l/599681-installing-eclipse-c-java
1. navx - http://www.pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/

Note: If you followed installation instructions for navx correctly you should see *navx_frc.jar* file in *&lt;user home&gt;/wpilib/user/java/lib* directory

## Getting Started
```
git clone https://github.com/FRC1360/Steamworks2017.git
cd Steamworks2017/Steamworks
```

Next, create a project to open in IDE (Eclipse or IntelliJ)

### If using Eclipse
```
./gradlew eclipse
```

### If using IntelliJ
```
./gradlew idea
```

## GradleRIO tasks
* deploy - Deploy Java Code to the RoboRIO
* determine_rio_address - Determine the active address for the RoboRIO
* restart_rio_code - Restart User Code running on the RoboRIO

