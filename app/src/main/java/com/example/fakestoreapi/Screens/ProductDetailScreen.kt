package com.example.fakestoreapi.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fakestoreapi.Models.Product
import com.example.fakestoreapi.ui.theme.FakeStoreAPITheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.fakestoreapi.services.ProductsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Composable
fun ProductDetailScreen(id:Int)
{
    //VARIABLES
    val BASE_URL = "https://fakestoreapi.com/"
    var product by remember{
        mutableStateOf<Product?>(null)
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    //RetroFit
    LaunchedEffect(key1 = true)
    {
        try {
            //1. Instancia en retroFit
            val retrofitBuilder = Retrofit.Builder() //Patrón de diseño builder
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            //2. Ejecutar la petición HTTP
            val result = async(Dispatchers.IO) {
                val productService = retrofitBuilder.create(ProductsService::class.java)
                productService.getProductById(id)
            }

            //3. Manejar la respuesta
            Log.i("ProductsScreen", result.await().toString())
            product = result.await()
            isLoading = false
        }
        catch (e: Exception) {
            Log.e("ProductScreen", e.message.toString())
            isLoading = false

        }
    }

    if(product != null){

    //CONTENIDO DE PRODUCT_DETAIL
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center

        ) {
            //ImagenSuperior
            AsyncImage(
                model = product!!.image,
                contentDescription = product!!.title,
                //contentScale = ContentScale.Crop,
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = 55.dp, bottom = 20.dp)
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Fit
            )

            //Title and price
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                //Name and category
                Column(
                    modifier = Modifier
                        //.padding(start = 20.dp, end = 70.dp)
                        .weight(0.75f)
                ) {
                    Text(
                        text = product!!.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 24.sp
                    )
                    Text(
                        text = product!!.category,
                        fontSize = 14.sp,
                        color = Color.Gray
                        //fontWeight = FontWeight.Bold
                    )
                }

                //Price
                Box(
                    modifier = Modifier.weight(0.25f),
                    contentAlignment = Alignment.CenterEnd
                ) {

                    Box(
                        modifier = Modifier
                            .width(90.dp)
                            .height(45.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$"+ product!!.price.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            //Description
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            )
            {
                Text(
                    text = "Description",
                    //color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )

                Text(
                    text = product!!.description,
                    //color = Color.White,
                    //fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

    } else if( product == null && isLoading)
    {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else{
       Column()
       {
           Text("Error al cargar producto")
       }
    }

}


@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun ProductDetailScreenPreview(){
    FakeStoreAPITheme {
        ProductDetailScreen(
            id = 1
        )
    }
}