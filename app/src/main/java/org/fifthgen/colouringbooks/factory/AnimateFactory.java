package org.fifthgen.colouringbooks.factory;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.fifthgen.colouringbooks.R;

/**
 * Created by GameGFX Studio on 2015/8/11.
 */
@SuppressWarnings("unused")
public class AnimateFactory {
    private static AnimateFactory animateFactory;

    private AnimateFactory() {
    }

    public static AnimateFactory getInstance() {
        if (animateFactory == null) {
            animateFactory = new AnimateFactory();
        }
        return animateFactory;
    }

    public Animation rotationAnimation(Context context, float fromdgrees, float todgrees) {
        return rotationAnimation(context, fromdgrees, todgrees, 0, 0);
    }

    public Animation rotationAnimation(Context context, float fromdgrees, float todgrees, int x, int y) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromdgrees, todgrees, x, y);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        return rotateAnimation;
    }

    public void SlideOutUpAnimation(View... view) {
        for (final View v : view) {
            if (v.getVisibility() == View.VISIBLE) {
                AnimatorListener listener = new AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        v.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                };

                YoYo.with(Techniques.FadeOut).withListener(listener).duration(300).playOn(v);
            }
        }
    }

    public void BounceInDownAnimation(View... view) {
        for (View v : view) {
            if (v.getVisibility() != View.VISIBLE) {
                v.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.BounceInDown).duration(300).playOn(v);
            }
        }
    }

    public Animation rotateAnimationForever(float fromdgrees, float todgrees, int x, int y) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromdgrees, todgrees, x, y);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return rotateAnimation;
    }

    public Animation popupAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.pop_show_overshoot);
    }
}
