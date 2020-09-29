package com.ersiver.gymific.util

import android.widget.ImageButton
import com.ersiver.gymific.R

//Helper to make the button disabled and semi-transparent
fun ImageButton.disabledAndFade(){
    this.apply {
        isEnabled = false
        alpha = 0.5F
    }
}

//Helper to make the button enabled and opaque.
fun ImageButton.enabledAndOpaque(){
    this.apply {
        isEnabled = true
        alpha = 1F
    }
}

//Helper to match the image id and the passed workout's code.
fun String.asDrawableId() : Int {
    return when(this){
        "cardio" -> R.drawable.cat_cardio
        "legs" -> R.drawable.cat_legs
        "arms" -> R.drawable.cat_arms
        "arms_a" -> R.drawable.arms_a
        "arms_b" -> R.drawable.arms_b
        "arms_c" -> R.drawable.arms_c
        "arms_d" -> R.drawable.arms_d
        "arms_e" -> R.drawable.arms_e
        "arms_f" -> R.drawable.arms_f
        "arms_g" -> R.drawable.arms_g
        "arms_h" -> R.drawable.arms_h
        "arms_i" -> R.drawable.arms_i
        "arms_j" -> R.drawable.arms_j
        "legs_a" -> R.drawable.legs_a
        "legs_b" -> R.drawable.legs_b
        "legs_c" -> R.drawable.legs_c
        "legs_d" -> R.drawable.legs_d
        "legs_e" -> R.drawable.legs_e
        "legs_f" -> R.drawable.legs_f
        "legs_g" -> R.drawable.legs_g
        "legs_h" -> R.drawable.legs_h
        "legs_i" -> R.drawable.legs_i
        "legs_j" -> R.drawable.legs_j
        "cardio_a" -> R.drawable.cardio_a
        "cardio_c" -> R.drawable.cardio_c
        "cardio_d" -> R.drawable.cardio_d
        else -> R.drawable.arms_a
    }
}