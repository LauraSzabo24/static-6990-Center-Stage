package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepField {
    static int position;
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        position = 1;
        RoadRunnerBotEntity robot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-36/*12*/, /*58*/-60, Math.toRadians(90)))
                                //far blue through side
                                /*.forward(24)
                                .turn(Math.toRadians(-90))
                                .forward(24)
                                .turn(Math.toRadians(-90))
                                .forward(24)
                                .turn(Math.toRadians(-90))
                                .forward(24)
                                .forward(24)
                                .forward(24)
                                .forward(24)
                                .turn(Math.toRadians(-90))
                                .forward(24)
                                .turn(Math.toRadians(90))
                                .build()*/

                                //far blue through middle
                                /*.forward(24)
                                .turn(Math.toRadians(-90))
                                .forward(24)
                                .turn(Math.toRadians(90))
                                .forward(24)
                                .turn(Math.toRadians(90))
                                .forward(24)
                                .forward(24)
                                .forward(24)
                                .forward(24)
                                .turn(Math.toRadians(90))
                                .forward(24)
                                .turn(Math.toRadians(-90))
                                .build()*/

                                //close blue
                                /*.forward(24)
                                .turn(Math.toRadians(90))
                                .forward(24)
                                .build()*/



                                //far red through side
                                /*.forward(24)
                                .turn(Math.toRadians(90))
                                .forward(24)
                                .turn(Math.toRadians(90))
                                .forward(24)
                                .turn(Math.toRadians(90))
                                .forward(24)
                                .forward(24)
                                .forward(24)
                                .forward(24)
                                .turn(Math.toRadians(90))
                                .forward(24)
                                .turn(Math.toRadians(-90))
                                .build()*/

                                //far red through middle
                                /*.forward(24)
                                .turn(Math.toRadians(90))
                                .forward(24)
                                .turn(Math.toRadians(-90))
                                .forward(24)
                                .turn(Math.toRadians(-90))
                                .forward(24)
                                .forward(24)
                                .forward(24)
                                .forward(24)
                                .turn(Math.toRadians(-90))
                                .forward(24)
                                .turn(Math.toRadians(90))
                                .build()*/

                                //close red
                                .forward(24)
                                .turn(Math.toRadians(-90))
                                .forward(24)
                                .build()


                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(robot)
                .start();
    }
}

//this is just the defualt meep meep testing code, change later
//also change the powerplay field to the center stage one once its out