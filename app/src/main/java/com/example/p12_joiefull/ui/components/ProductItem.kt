package com.example.p12_joiefull.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.p12_joiefull.R
import com.example.p12_joiefull.domain.model.Picture
import com.example.p12_joiefull.domain.model.Product

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
) {
    var isFavorite by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isInPreview = LocalInspectionMode.current

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
                        .size(42.dp, 16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(
                            x = (-8).dp,
                            y = (-8).dp
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                isFavorite = !isFavorite
                                val message = if (isFavorite) {
                                    "Article ajouté aux favoris"
                                } else {
                                    "Article retiré des favoris"
                                }
                                if (!isInPreview) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFavorite) "Retirer des favoris" else "Ajouter aux favoris",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(18.dp)
                        )
                        Text(
                            text = product.likes.toString(),
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .offset(y = (-4).dp, x = (2).dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 4.dp),
            ) {
                Text(
                    text = product.name,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
                Icon(
                    painter = painterResource(R.drawable.star),
                    tint = Color(0xFFFFC700),
                    contentDescription = "Note de l'article",
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = product.rating.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
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
            rating = 4.6f,
            price = 49.99,
            originalPrice = 79.99
        ),
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)) // Couleur de fond légère pour la preview
    )
}