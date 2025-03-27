package com.example.p12_joiefull.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.p12_joiefull.model.Picture
import com.example.p12_joiefull.model.Product

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {

        Column {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {

                AsyncImage(
                    model = product.picture.url,
                    contentDescription = product.picture.description,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .size(36.dp, 16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(
                            x = (-8).dp,
                            y = (-8).dp
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Like",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(18.dp)

                        )
                        Text(
                            text = product.likes.toString(),
                            fontSize = 12.sp, // Adjust size to fit
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,

                        )
                    }
                }
            }

            Text(
                text = product.name,
                modifier = Modifier.width(150.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,// Ensure width matches image
            )

            // Row to display price and original price
            Row(
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 4.dp),
            ) {
                Text(
                    text = "${product.price.toInt()}€",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    fontSize = 12.sp,

                    )
                Text(
                    text = "${product.originalPrice.toInt()}€",
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    textAlign = TextAlign.Right,
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    ProductItem(
        product = Product(
            id = 1,
            picture = Picture(
                url = "https://via.placeholder.com/150",
                description = "Image d'une robe élégante"
            ),
            name = "Robe élégante",
            category = "Vêtements",
            likes = 120,
            price = 49.99,
            originalPrice = 79.99
        ),
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)) // Couleur de fond légère pour la preview
    )
}
