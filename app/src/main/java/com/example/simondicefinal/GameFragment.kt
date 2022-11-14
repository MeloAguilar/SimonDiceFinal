package com.example.simondicefinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.simondicefinal.databinding.ActivityMainBinding
import com.example.simondicefinal.databinding.FragmentGameBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class GameFragment : Fragment() {

    final var colorAzulEncendido: Int = R.drawable.btn_azul_encendido
    final var colorVerdeEncendido: Int = R.drawable.btn_verde_encendido
    final var colorAmarilloEncendido: Int = R.drawable.btn_amarillo_encendido
    final var colorRojoEncendido: Int = R.drawable.btn_rojo_encendido
    final var colorAzulApagado: Int = R.drawable.btn_azul_apagado
    final var colorVerdeApagado: Int = R.drawable.btn_verde_apagado
    final var colorRojoApagado: Int = R.drawable.btn_rojo_apagado
    final var colorAmarilloApagado: Int = R.drawable.btn_amarillo_apagado

    //Creamos el objeto que nos permite entrar en la vista con el nombre que se le indica en el import de viewbinding

    lateinit var binding: FragmentGameBinding

    var fallo : Boolean = false
    var velocidad : Velocidades = Velocidades.LENTO
    var secuenciaUser : MutableList<Colores> = mutableListOf()
    var secuenciaCPU : MutableList<Colores> = mutableListOf()
    var ronda : Int = 0
    var score = 0
    var bestScore = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var viewroot = inflater.inflate(R.layout.fragment_game, container, false)
        return viewroot
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //recogemos el layoutInflater propio del layout y lo mostramos
        this.binding = FragmentGameBinding.inflate(layoutInflater)



        this.binding.btnAzul.setOnClickListener { onClickBotonesJuego(this.binding.btnAzul) }
        this.binding.btnAmarillo.setOnClickListener { onClickBotonesJuego(this.binding.btnAmarillo) }
        this.binding.btnRojo.setOnClickListener { onClickBotonesJuego(this.binding.btnRojo) }
        this.binding.btnVerde.setOnClickListener { onClickBotonesJuego(this.binding.btnVerde) }

        this.binding.btnLento.setOnClickListener{onClickBotonesInicio(Velocidades.LENTO)}
        this.binding.btnRapido.setOnClickListener{onClickBotonesInicio(Velocidades.RAPIDO)}
        this.binding.btnNormal.setOnClickListener{onClickBotonesInicio(Velocidades.MEDIO)}
        var rnd = Random.nextInt(Velocidades.values().size)
        this.binding.btnAuto.setOnClickListener{onClickBotonesInicio(Velocidades.values()[rnd])}
        reset()

    }

    private fun reset(){
        this.binding.btnAmarillo.isClickable = false
        this.binding.btnAzul.isClickable = false
        this.binding.btnVerde.isClickable = false
        this.binding.btnRojo.isClickable = false
        this.binding.btnAuto.isClickable = true

    }

    private fun cleanScore(){
        secuenciaCPU.clear()
        secuenciaUser.clear()
        score = 0
        this.binding.txtScore.text = "Score: $score"
    }

    private fun start(){
        this.binding.btnAmarillo.isClickable = true
        this.binding.btnAzul.isClickable = true
        this.binding.btnVerde.isClickable = true
        this.binding.btnRojo.isClickable = true
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
            var dialogo = android.app.AlertDialog.Builder(super.getContext())
            dialogo.setMessage("Perdiste We")
            dialogo.setTitle("Simon Says")
            dialogo.setPositiveButton("Ok") { _, _ ->
                cleanScore()
                fallo = false
            }

            dialogo.setCancelable(false)
        }else{
            ronda++
            score++
            if(score > bestScore)
                bestScore = score

            this.binding.txtScore.text = "Score:  $score"
            this.binding.txtBestScore.text = "Best Score: $bestScore"
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
                this.binding.btnAzul
            }
            Colores.AMARILLO ->{
                this.binding.btnAmarillo
            }
            Colores.ROJO -> {
                this.binding.btnRojo
            }
            Colores.VERDE -> {
                this.binding.btnVerde
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
            this.binding.btnVerde.id -> {
                Colores.VERDE
            }
            this.binding.btnAzul.id ->{
                Colores.AZUL
            }
            this.binding.btnRojo.id -> {
                Colores.ROJO
            }
            this.binding.btnAmarillo.id -> {
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