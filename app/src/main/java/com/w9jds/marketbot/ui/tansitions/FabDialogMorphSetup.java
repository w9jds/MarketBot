package com.w9jds.marketbot.ui.tansitions;

/**
 * Helper class for setting up Fab <-> Dialog shared element transitions.
 */
public class FabDialogMorphSetup {
//
//    public static final String EXTRA_SHARED_ELEMENT_START_COLOR =
//            "EXTRA_SHARED_ELEMENT_START_COLOR";
//    public static final String EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS =
//            "EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS";
//
//    private FabDialogMorphSetup() { }
//
//    /**
//     * Configure the shared element transitions for morphin from a fab <-> dialog. We need to do
//     * this in code rather than declaratively as we need to supply the color to transition from/to
//     * and the dialog corner radius which is dynamically supplied depending upon where this screen
//     * is launched from.
//     */
//    public static void setupSharedEelementTransitions(@NonNull Activity activity,
//                                                      @Nullable View target,
//                                                      int dialogCornerRadius) {
//        if (!activity.getIntent().hasExtra(EXTRA_SHARED_ELEMENT_START_COLOR)) return;
//
//        int startCornerRadius = activity.getIntent().getIntExtra
//                (EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS, -1);
//
//        ArcMotion arcMotion = new ArcMotion();
//        arcMotion.setMinimumHorizontalAngle(50f);
//        arcMotion.setMinimumVerticalAngle(50f);
//        int color = activity.getIntent().
//                getIntExtra(EXTRA_SHARED_ELEMENT_START_COLOR, Color.TRANSPARENT);
//        Interpolator easeInOut =
//                AnimUtils.getFastOutSlowInInterpolator(activity);
//        MorphFabToDialog sharedEnter = new MorphFabToDialog(color, dialogCornerRadius, startCornerRadius);
//        sharedEnter.setPathMotion(arcMotion);
//        sharedEnter.setInterpolator(easeInOut);
//        MorphDialogToFab sharedReturn = new MorphDialogToFab(color, startCornerRadius);
//        sharedReturn.setPathMotion(arcMotion);
//        sharedReturn.setInterpolator(easeInOut);
//        if (target != null) {
//            sharedEnter.addTarget(target);
//            sharedReturn.addTarget(target);
//        }
//        activity.getWindow().setSharedElementEnterTransition(sharedEnter);
//        activity.getWindow().setSharedElementReturnTransition(sharedReturn);
//    }

}