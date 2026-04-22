package com.example.lib

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    println("=== CORTINAS ===")
    //corrutinaLaunch()
    corrutinaAsync()
}


fun corrutinaAsync(){
    runBlocking {
        println("Haciendo petición /GET")
       val result =  async {
            println("Haciendo consulta a la API...")
            delay( 6000)
           println("retornando resultado...")
            //Manera uno
            //return@async "hola q hace"

            //manera dos
            """ {"id":1,"name":"Juan"}"""
        }
        println("El resultado de la petición es ${result.await()}")
    }
}

suspend fun  consultaAPI(){
    println("Consultando la API")
    delay(2000)
    println("La respuesta es positiva.")
}

 fun corrutinaLaunch(){
    println("=== CORTINAS ===")

    //1. CORTINA  - BLOQUEA EL HILO PRINCIPAL, SOLO PARA PRUEBAS, NO EN PRODUCCIÓN.
    runBlocking{
        println("Cargando interfaz gráfica...")
        //launch - ejecuta codigo asincrono.
        launch(Dispatchers.IO){
            consultaAPI()
        }
        println("La UI sigue cargando mientras termina la cortina")
    }
}