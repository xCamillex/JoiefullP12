package com.example.p12_joiefull.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p12_joiefull.R


@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {

    val starSize = 40.dp
    val starSpacing = 15.dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) {
                painterResource(R.drawable.star)
            } else {
                painterResource(R.drawable.star_outline)
            }
            val iconTintColor =
                if (isSelected) colorResource(R.color.orange) else Color.Black

            Icon(
                painter = icon,
                contentDescription = "Attribuer la note $i étoile${if (i > 1) "s" else ""}",
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
                    .width(starSize)
                    .height(starSize)
                    .semantics {
                        contentDescription = "Attribuer la note $i étoile${if (i > 1) "s" else ""}"
                    }
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStarRatingBar() {
    StarRatingBar(
        rating = 0f,
        onRatingChanged = {}
    )
}