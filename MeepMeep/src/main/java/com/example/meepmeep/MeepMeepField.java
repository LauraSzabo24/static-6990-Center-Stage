package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepField {
    static int position;
    static Pose2d myPose;
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        myPose = new Pose2d(-36, -36, Math.toRadians(0));
        RoadRunnerBotEntity robot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-36/*12*/, /*58*/-60, Math.toRadians(/*-90*/90)))
                                //far blue through middle
                                /*.lineToLinearHeading(new Pose2d(-36, 34, Math.toRadians(180)))
                                .forward(24)
                                .splineToLinearHeading(new Pose2d(-36,10,Math.toRadians(0)),  Math.toRadians(0))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,34,Math.toRadians(0)),  Math.toRadians(90))
                                .splineToLinearHeading(new Pose2d(12,10,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,34,Math.toRadians(180)),  Math.toRadians(0))
                                .build()*/

                                //far blue through side
                                /*.lineToLinearHeading(new Pose2d(-36, 34, Math.toRadians(180)))
                                .forward(24)
                                .splineToLinearHeading(new Pose2d(-36,10,Math.toRadians(0)),  Math.toRadians(0))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,34,Math.toRadians(0)),  Math.toRadians(90))
                                .splineToLinearHeading(new Pose2d(12,10,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,34,Math.toRadians(180)),  Math.toRadians(0))
                                .build()*/


                                //close blue down middle
                                /*.lineToLinearHeading(new Pose2d(12, 34, Math.toRadians(0)))
                                .forward(24)
                                .splineToLinearHeading(new Pose2d(12,10,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(72)
                                .lineToLinearHeading(new Pose2d(12, 10, Math.toRadians(0)))
                                .splineToLinearHeading(new Pose2d(36, 34,Math.toRadians(0)),  Math.toRadians(180))
                                .splineToLinearHeading(new Pose2d(12,10,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(72)
                                .lineToLinearHeading(new Pose2d(12, 10, Math.toRadians(0)))
                                .splineToLinearHeading(new Pose2d(36,34,Math.toRadians(0)),  Math.toRadians(180))
                                .build()*/

                                //close blue down side
                                /*.lineToLinearHeading(new Pose2d(12, 34, Math.toRadians(0)))
                                .forward(24)
                                .splineToLinearHeading(new Pose2d(12,58,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,34,Math.toRadians(180)),  Math.toRadians(180))

                                .splineToLinearHeading(new Pose2d(-36,58,Math.toRadians(0)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,34,Math.toRadians(0)),  Math.toRadians(180))

                                .splineToLinearHeading(new Pose2d(12,58,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,34,Math.toRadians(180)),  Math.toRadians(180))

                                .splineToLinearHeading(new Pose2d(-36,58,Math.toRadians(0)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,34,Math.toRadians(0)),  Math.toRadians(180))
                                .build()*/




                                //far red through middle
                                .lineToLinearHeading(new Pose2d(-36, -36, Math.toRadians(180)))
                                .forward(24)

                                .splineToLinearHeading(new Pose2d(-36,-12,Math.toRadians(0)),  Math.toRadians(0))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,-36,Math.toRadians(0)),  Math.toRadians(-90))
                                .splineToLinearHeading(new Pose2d(12,-12,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,-36,Math.toRadians(180)),  Math.toRadians(0))

                                .splineToLinearHeading(new Pose2d(-36,-12,Math.toRadians(0)),  Math.toRadians(0))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,-36,Math.toRadians(0)),  Math.toRadians(-90))
                                .splineToLinearHeading(new Pose2d(12,-12,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,-36,Math.toRadians(180)),  Math.toRadians(0))
                                .build()

                                //far red through side
                                /*.lineToLinearHeading(new Pose2d(-36, -36, Math.toRadians(180)))
                                .forward(24)

                                .splineToLinearHeading(new Pose2d(-36,-12,Math.toRadians(0)),  Math.toRadians(0))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,-36,Math.toRadians(0)),  Math.toRadians(-90))
                                .splineToLinearHeading(new Pose2d(12,-12,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,-36,Math.toRadians(180)),  Math.toRadians(0))

                                .splineToLinearHeading(new Pose2d(-36,-12,Math.toRadians(0)),  Math.toRadians(0))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,-36,Math.toRadians(0)),  Math.toRadians(-90))
                                .splineToLinearHeading(new Pose2d(12,-12,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,-36,Math.toRadians(180)),  Math.toRadians(0))
                                .build()*/

                                //close red down middle
                                /*.lineToLinearHeading(new Pose2d(12, -36, Math.toRadians(0)))
                                .forward(24)
                                .splineToLinearHeading(new Pose2d(12,-12,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(72)
                                .lineToLinearHeading(new Pose2d(12, -12, Math.toRadians(0)))
                                .splineToLinearHeading(new Pose2d(36,-36,Math.toRadians(0)),  Math.toRadians(180))
                                .splineToLinearHeading(new Pose2d(12,-12,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(72)
                                .lineToLinearHeading(new Pose2d(12, -12, Math.toRadians(0)))
                                .splineToLinearHeading(new Pose2d(36,-36,Math.toRadians(0)),  Math.toRadians(180))
                                .build()*/

                                //close red down side
                                /*.lineToLinearHeading(new Pose2d(12, -36, Math.toRadians(0)))
                                .forward(24)
                                .splineToLinearHeading(new Pose2d(12,-60,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,-36,Math.toRadians(180)),  Math.toRadians(180))

                                .splineToLinearHeading(new Pose2d(-36,-60,Math.toRadians(0)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,-36,Math.toRadians(0)),  Math.toRadians(180))

                                .splineToLinearHeading(new Pose2d(12,-60,Math.toRadians(180)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(-60,-36,Math.toRadians(180)),  Math.toRadians(180))

                                .splineToLinearHeading(new Pose2d(-36,-60,Math.toRadians(0)),  Math.toRadians(180))
                                .forward(48)
                                .splineToLinearHeading(new Pose2d(36,-36,Math.toRadians(0)),  Math.toRadians(180))
                                .build()*/


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