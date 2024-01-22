package com.fylora.pomodorotimer.core

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.fylora.pomodorotimer.R

object Fonts {
    val fontFamily = FontFamily(
        Font(
            R.font.montserrat_black,
            FontWeight.Black
        ),
        Font(
            R.font.montserrat_blackitalic,
            FontWeight.Black, style = FontStyle.Italic
        ),
        Font(
            R.font.montserrat_bold,
            FontWeight.Bold
        ),
        Font(
            R.font.montserrat_bolditalic,
            FontWeight.Bold, style = FontStyle.Italic
        ),
        Font(
            R.font.montserrat_extrabold,
            FontWeight.Bold
        ),
        Font(
            R.font.montserrat_extrabolditalic,
            FontWeight.Bold, style = FontStyle.Italic
        ),
        Font(
            R.font.montserrat_extralight,
            FontWeight.ExtraLight
        ),
        Font(
            R.font.montserrat_extralightitalic,
            FontWeight.ExtraLight, style = FontStyle.Italic
        ),
        Font(
            R.font.montserrat_italic,
            FontWeight.Normal, style = FontStyle.Italic
        ),
        Font(
            R.font.montserrat_light,
            FontWeight.Light
        ),
        Font(
            R.font.montserrat_lightitalic,
            FontWeight.Light, style = FontStyle.Italic
        ),
        Font(
            R.font.montserrat_medium,
            FontWeight.Medium
        ),
        Font(
            R.font.montserrat_mediumitalic,
            FontWeight.Medium, style = FontStyle.Italic
        ),
        Font(
            R.font.montserrat_regular,
            FontWeight.Normal
        ),
        Font(
            R.font.montserrat_semibold,
            FontWeight.SemiBold
        ),
        Font(
            R.font.montserrat_semibolditalic,
            FontWeight.SemiBold, style = FontStyle.Italic
        ),
        Font(
            R.font.montserrat_thin,
            FontWeight.Thin
        ),
        Font(
            R.font.montserrat_thinitalic,
            FontWeight.Thin, style = FontStyle.Italic
        )
    )
}

val fontFamily = Fonts.fontFamily