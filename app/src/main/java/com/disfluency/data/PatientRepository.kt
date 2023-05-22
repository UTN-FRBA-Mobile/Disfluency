package com.disfluency.data
import com.disfluency.model.Patient
import java.time.LocalDate
import com.disfluency.R

object PatientRepository {
    val testPatient = Patient("Agustin", "Cragno", LocalDate.of(1998, 7, 30), 40123864, "acragno@frba.utn.edu.ar", LocalDate.of(2023, 1, 9))

    val testList = listOf(
        Patient("Agustin", "Cragno", LocalDate.of(1998, 7, 30), 40123864, "acragno@frba.utn.edu.ar", LocalDate.of(2023, 1, 9), R.drawable.agus),
        Patient("Jose", "Bruzzoni", LocalDate.of(1991, 2, 3), 43181238, "jbruzzoni@frba.utn.edu.ar", LocalDate.of(2018, 12, 9), R.drawable.jose),
        Patient("Alexander", "Martinez", LocalDate.of(1995, 5, 4), 37186477, "amartinez@gmail.com", LocalDate.of(2023, 5, 15), R.drawable.matias)
    )
}