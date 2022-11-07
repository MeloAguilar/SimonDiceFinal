package com.example.simondicefinal

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
// Si en el Graddle.app introducimos un nuevo buildFeatures,
// dentro de este, cambiaremos el estado de viewBinding a true
import com.example.simondicefinal.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    final var colorAzulEncendido: Int = R.drawable.btn_azul_encendido
    final var colorVerdeEncendido: Int = R.drawable.btn_verde_encendido
    final var colorAmarilloEncendido: Int = R.drawable.btn_amarillo_encendido
    final var colorRojoEncendido: Int = R.drawable.btn_rojo_encendido
    final var colorAzulApagado: Int = R.drawable.btn_azul_apagado
    final var colorVerdeApagado: Int = R.drawable.btn_verde_apagado
    final var colorRojoApagado: Int = R.drawable.btn_rojo_apagado
    final var colorAmarilloApagado: Int = R.drawable.btn_amarillo_apagado

    //Creamos el objeto que nos permite entrar en la vista con el nombre que se le indica en el import de viewbinding
    lateinit var viewBinding: ActivityMainBinding

    var fallo : Boolean = false
    var velocidad : Velocidades = Velocidades.LENTO
    var secuenciaUser : MutableList<Colores> = mutableListOf()
    var secuenciaCPU : MutableList<Colores> = mutableListOf()
    var ronda : Int = 0
    var score = 0
    var bestScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //recogemos el layoutInflater propio del layout y lo mostramos
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        viewBinding.btnAzul.setOnClickListener { onClickBotonesJuego(viewBinding.btnAzul) }
        viewBinding.btnAmarillo.setOnClickListener { onClickBotonesJuego(viewBinding.btnAmarillo) }
        viewBinding.btnRojo.setOnClickListener { onClickBotonesJuego(viewBinding.btnRojo) }
        viewBinding.btnVerde.setOnClickListener { onClickBotonesJuego(viewBinding.btnVerde) }

        viewBinding.btnLento.setOnClickListener{onClickBotonesInicio(Velocidades.LENTO)}
        viewBinding.btnRapido.setOnClickListener{onClickBotonesInicio(Velocidades.RAPIDO)}
        viewBinding.btnNormal.setOnClickListener{onClickBotonesInicio(Velocidades.MEDIO)}
        var rnd = Random.nextInt(Velocidades.values().size)
        viewBinding.btnAuto.setOnClickListener{onClickBotonesInicio(Velocidades.values()[rnd])}
        reset()

    }

    private fun reset(){
        viewBinding.btnAmarillo.isClickable = false
        viewBinding.btnAzul.isClickable = false
        viewBinding.btnVerde.isClickable = false
        viewBinding.btnRojo.isClickable = false
        viewBinding.btnAuto.isClickable = true

    }

private fun cleanScore(){
    secuenciaCPU.clear()
    secuenciaUser.clear()
    score = 0
    viewBinding.txtScore.text = "Score: $score"
}

    private fun start(){
        viewBinding.btnAmarillo.isClickable = true
        viewBinding.btnAzul.isClickable = true
        viewBinding.btnVerde.isClickable = true
        viewBinding.btnRojo.isClickable = true
    }


    /**
     * Método que se llama cada vez que se pulsan los botones de inicio de juego
     */
    private fun onClickBotonesInicio(_velocidad : Velocidades){
        cleanScore()
        this.velocidad = _velocidad
        generarClickCpu()
        GlobalScope.launch {
            delay(500)
            mostrarSecuenciaCPU()
            start()
        }

    }

    /**
     * Método que se encarga de iluminar los botones de juego con la secuencia que se encuentra
     * el la MutableList secuenciaCPU
     */
    private fun mostrarSecuenciaCPU(){
        reset()
        GlobalScope.launch {
        for(item in secuenciaCPU){

                delay(600)
                iluminarBoton(recogerBotonAPartirDeColor(item))
                delay(velocidad.milis)
            }

        }
    }

    /**
     * Método que genera un color de los 4 posibles mediante un numero aleatorio
     * y lo añade a la mutableList secuenciaCPU
     */
    private fun generarClickCpu(){
        var rnd = Random.nextInt(Colores.values().size)
        var color = Colores.values()[rnd]
        secuenciaCPU.add(color)
    }


    /**
     * Método que se encarga de, en cada ronda, comprobar si el jugador se ha equivocado o no
     */
    private fun siguienteRonda(){

        if(fallo){
            var dialogo = android.app.AlertDialog.Builder(this)
            dialogo.setMessage("Perdiste We")
            dialogo.setTitle("Simon Says")
            dialogo.setPositiveButton("Ok") { _, _ ->
                cleanScore()
                fallo = false
            }
            dialogo.show()
            dialogo.setCancelable(false)
        }else{
            ronda++
            score++
            if(score > bestScore)
                bestScore = score

            viewBinding.txtScore.text = "Score:  $score"
            viewBinding.txtBestScore.text = "Best Score: $bestScore"
            generarClickCpu()
            mostrarSecuenciaCPU()
            start()
        }
    }


    /**
     * Método que se lanzará cuando se pulse uno de los cuatro botones de colores pertenecientes al juego
     * del layout activity_main
     *
     */
    private fun onClickBotonesJuego(boton : ImageButton){
            iluminarBoton(boton)
            var color = recogerColorAPartirDeBoton(boton.id)

            secuenciaUser.add(color)
        comprobarSecuencia(color, secuenciaUser.size)

    }

    fun comprobarSecuencia(color : Colores, posicion : Int){
        if (color != secuenciaCPU[posicion]) {

        }
    }


    /**
     * Método que se encarga de devolver un objeto ImageButton, que corresponderá
     * a uno de los cuatro botones de colores que se utilizan para interactuar
     * con el juego, a partir de un objeto tipo Colores
     * introducido como parámetro
     */
    private fun recogerBotonAPartirDeColor(color : Colores): ImageButton {
        val boton : ImageButton = when(color){
            Colores.AZUL -> {
                viewBinding.btnAzul
            }
            Colores.AMARILLO ->{
                viewBinding.btnAmarillo
            }
            Colores.ROJO -> {
                viewBinding.btnRojo
            }
            Colores.VERDE -> {
                viewBinding.btnVerde
            }
        }
        return boton
    }


    /**
     * Método que se encarga de devolver un objeto Colores
     * a partir de un entero correspondiente al id de un objeto ImageButton.
     *
     * <pre>
     *     btn debe ser el id de un objeto ImageButton que se encuentre en activity.main.xml
     * </pre>
     * <post>
     *     Siempre devolverá un objeto de tipo Colores correspondiente a uno de los cuatro botones
     *      de diferentes colores del layout activity_main
     * </post>
     */
    fun recogerColorAPartirDeBoton(btn : Int) : Colores{
        var color : Colores = Colores.ROJO

            color = when(btn){
            viewBinding.btnVerde.id -> {
                Colores.VERDE
            }
            viewBinding.btnAzul.id ->{
                 Colores.AZUL
            }
            viewBinding.btnRojo.id -> {
                Colores.ROJO
            }
            viewBinding.btnAmarillo.id -> {
                Colores.AMARILLO
            }
                else -> {color}
            }
        return color
    }

    /**
     * Método que se encarga de iluminar un boton al presionarlo el
     * usuario o el cpu
     */
    fun iluminarBoton(boton: ImageButton) {
        var colorEncendido: Int = 0
        var colorApagado: Int = 0
        when (boton.id) {
            R.id.btnAzul -> {
                colorEncendido = colorAzulEncendido
                colorApagado = colorAzulApagado
            }
            R.id.btnAmarillo -> {
                colorEncendido = colorAmarilloEncendido
                colorApagado = colorAmarilloApagado
            }
            R.id.btnRojo -> {
                colorEncendido = colorRojoEncendido
                colorApagado = colorRojoApagado
            }
            R.id.btnVerde -> {
                colorEncendido = colorVerdeEncendido
                colorApagado = colorVerdeApagado
            }

        }
        boton.setImageResource(colorEncendido)
        GlobalScope.launch {
            delay(400)
            boton.setImageResource(colorApagado)

        }
    }
}