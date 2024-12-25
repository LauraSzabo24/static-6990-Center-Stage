This is the 2023-2024 season's code for team 6990 Static Void.

Credits: All code within this repository is from either the 2024-2025 FTC Roadrunner Quickstart, EasyOpenCV library, tensorflow library
or is the work of Laura Szabo #6990 Static Void with help from Qays Hawwar for some of the autonomous files also from #6990 Static Void

Guide/Breakdown: All important code files are found under TeamCode/src/main/java. Here there are 4 folders:

    -Auto: Each folder describes the type of autonomous the files inside are. These are based on the robot's starting position on the field. 
    There are a lot of different versions for each, but in the end there isn't that big of a difference between them technically. The Mailbox file
    is a file I use to transfer data from the autonomous portion to the teleOp files. 

    -TeleOp: There are a lot of different versions, but yet again, there isn't that big of a difference between them. The one that is the one we actually
    used the most and has the most of the features is RedTetrisShow. The other files can be ignored. ColorDetector is the pipeline I wrote in order
    to do the color detection for the pixel game elements. There is no seperate runner file for this since I was planning to add it within the RedTetrisShow
    file. 

    -Camera: These are the files for the camera detection. The AprilTagDetectionPipeline is a premade pipeline form EasyOpenCV, I did not write it. The various
    files starting with "PropDetector" are practically identical and the only changes are to account for differences in robot starting positon. Their purpose
    is to recognize where the "prop" game element is on the field. Finally, the two alignment files are just for calibrating the PropDetectors. They serve no
    other purpose on their own. Finally, the TetrisXAlignment is an unused file, it can be ignored. 

    -org/firstinspires/ftc/teamcode: These contain some of the files from the Roadrunner Quickstart. However, the majority are
    modified especially the ones found under the drive folder. Most of these modifications were done in order to implement automated
    teleOp/driver control period controls. 

    -Ancient - These are old files that I didn't want to delete. They can be ignored. 

Thank you for visiting this repository!! I hope the way I have the files isn't too confusing or messy. All of the important files should have comments to help sort out the chaos. Have a nice day!! 
