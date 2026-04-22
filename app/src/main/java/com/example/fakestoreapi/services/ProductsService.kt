package com.example.fakestoreapi.services

import com.example.fakestoreapi.Models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {

    @GET("products")
    suspend fun getAllProducts() : List<Product>

    //Path( /1 ), queryString ( ?name=Juan ) y Body ( {"name":"Juan"} )
    @GET("products/{asasas}")
    suspend fun getProductById(@Path("asasas") id: Int) : Product
}