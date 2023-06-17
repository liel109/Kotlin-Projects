package com.example.myfavoritemovie.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.widget.ImageView
import com.example.myfavoritemovie.R

class ItemUtils {
    companion object {
        private const val ANIMATION_DURATION = 75L

        //Change a star from full star to empty star or the opposite with animation
        fun changeStar(star: ImageView, full: Boolean) {
            if (full) {
                star.setImageResource(R.drawable.ic_full_star)

                val scaleX =
                    ObjectAnimator.ofFloat(star, "scaleX", 1f, 1.2f).setDuration(ANIMATION_DURATION)
                val scaleY =
                    ObjectAnimator.ofFloat(star, "scaleY", 1f, 1.2f).setDuration(ANIMATION_DURATION)
                scaleX.repeatCount = 1
                scaleX.repeatMode = ObjectAnimator.REVERSE
                scaleY.repeatCount = 1
                scaleY.repeatMode = ObjectAnimator.REVERSE

                val animatorSet = AnimatorSet()
                animatorSet.playTogether(scaleX, scaleY)
                animatorSet.start()

            } else {
                star.setImageResource(R.drawable.ic_empty_star)
            }
        }

        //validate that the user entered all the needed input
        fun validateInput(
            numberOfStars: Int,
            movieTitle: String,
            movieDesc: String,
            movieLengthMinutes: Int,
            movieLengthHours: Int,
            imageURI : Uri?
        ): Boolean {
            return !(numberOfStars == 0 || movieTitle.trim().isEmpty()
                    || movieDesc.trim().isEmpty()
                    || (movieLengthMinutes == 0 && movieLengthHours == 0) || imageURI == null)
        }
    }
}