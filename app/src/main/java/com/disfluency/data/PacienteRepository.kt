package com.disfluency.data
import java.time.LocalDate
import com.disfluency.R
import com.disfluency.model.Paciente

object PacienteRepository {

    fun getPacienteById(id: Int): Paciente{
        return pacientes.first { paciente -> paciente.id == id }
    }

    val testPatient = Paciente("Agustin", "Cragno", LocalDate.of(1998, 7, 30), 40123864, "acragno@frba.utn.edu.ar", LocalDate.of(2023, 1, 9), R.drawable.agus)

    val pacientes = listOf(
        Paciente("Agustin", "Cragno", LocalDate.of(1998, 7, 30), 40123864, "acragno@frba.utn.edu.ar", LocalDate.of(2023, 1, 9), R.drawable.agus),
        Paciente("Jose", "Bruzzoni", LocalDate.of(1991, 2, 3), 43181238, "jbruzzoni@frba.utn.edu.ar", LocalDate.of(2018, 12, 9), R.drawable.jose),
        Paciente("Alexander", "Martinez", LocalDate.of(1995, 5, 4), 37186477, "amartinez@gmail.com", LocalDate.of(2023, 5, 15), R.drawable.matias)
    )

    val longListForTest = List(20){
        pacientes.random()
    }
}