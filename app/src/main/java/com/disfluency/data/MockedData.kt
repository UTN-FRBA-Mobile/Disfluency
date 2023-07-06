package com.disfluency.data

import com.disfluency.R
import com.disfluency.model.*
import java.time.LocalDate

object MockedData {

    const val testUrl = "https://pf5302.s3.us-east-2.amazonaws.com/audios/iniciosuave.mp3"

    val exercises: MutableList<Exercise> = arrayListOf(
        Exercise(id = 1, therapistId = 1, title = "Presentarse", instruction = "Di tu nombre y apellido", sampleAudioURL = testUrl, number = 1),
        Exercise(id = 2, therapistId = 1, title = "Repetir", instruction = "Repite la siguiente frase", phrase = "Tres tigres comen trigo Tres tigres comen trigoTres tigres comen trigo Tres tigres comen trigo Tres tigres comen trigo Tres tigres comen trigo Tres tigres comen trigo v Tres tigres comen trigo Tres tigres comen trigo", sampleAudioURL = testUrl, number = 2),
        Exercise(id = 3, therapistId = 1, title = "Repetir 2", instruction = "Repita la siguiente frase", phrase = "Pablito clavó un clavito", sampleAudioURL = testUrl, number = 3),

        Exercise(
            id = 4,
            therapistId = 1,
            title = "Fonacion Continuada",
            instruction = "Mantener la fonacion a lo largo de la palabra y entre palabra y palabra. Mantener la vibracion de las cuerdas vocales a lo largo de la palabra y entre palabras sin frenar, y sostener durante toda una frase",
            phrase = "La portabilidad es la capacidad del producto o componente de ser transferido de forma efectiva y eficiente de un entorno hardware, software, operacional o de utilización a otro.",
            sampleAudioURL = testUrl,
            number = 4
        ),
        Exercise(
            id = 5,
            therapistId = 1,
            title = "Inicio Suave",
            instruction = "Es un suave comienzo en la vibración de las cuerdas vocales en el inicio de cualquier palabra que comience con vocal. Dejo salir un poco de aire a través de las cuerdas vocales antes de comenzar la fonación; quizás un poco aireado al principio pero mejorará con la práctica.",
            phrase = "La fiabilidad es la capacidad de un sistema o componente para desempeñar las funciones especificadas, cuando se usa bajo unas condiciones y periodo de tiempo determinados.",
            sampleAudioURL = testUrl,
            number = 5
        ),
        Exercise(
            id = 6,
            therapistId = 1,
            title = "Toques ligeros",
            instruction = "Producir las consonantes con movimientos y toques suaves entre las zonas de contacto. Empiezo todas las palabras que comienzan con consonante con una suave producción; toco y me voy; evito la presión en el resto de las consonantes de la palabra.",
            phrase = "La mantenibilidad es la característica que representa la capacidad del producto software para ser modificado efectiva y eficientemente, debido a necesidades evolutivas, correctivas o perfectivas.",
            sampleAudioURL = testUrl,
            number = 6
        ),
        Exercise(
            id = 7,
            therapistId = 1,
            title = "Velocidad cómoda y continua",
            instruction = "Controlar la velocidad de manera que me sea cómodo, tratar de mantenerla ajustándola a mi comodidad. Hablá a una velocidad cómoda y constante a lo largo de las palabras; y de las frases; bajá un poco la velocidad cuando notás un poco de tensión en tu máquina de hablar.",
            phrase = "La usabilidad es la capacidad del producto software para ser entendido, aprendido, usado y resultar atractivo para el usuario, cuando se usa bajo determinadas condiciones.",
            sampleAudioURL = testUrl,
            number = 7
        )
    )

    val practices = arrayListOf(
        ExercisePractice("1", LocalDate.of(2023, 7, 3), testUrl),
        ExercisePractice("2", LocalDate.of(2023, 4, 26), testUrl),
        ExercisePractice("3", LocalDate.of(2023, 4, 28), testUrl)
    )

    val assignments = arrayListOf(
        ExerciseAssignment("1", exercises[4], LocalDate.of(2021, 8, 5), arrayListOf(practices[0], practices[1])),
        ExerciseAssignment("2", exercises[3], LocalDate.of(2023, 1, 9), arrayListOf(practices[2])),
        ExerciseAssignment("3", exercises[5], LocalDate.of(2018, 12, 9), emptyList())
    )

    val patients: MutableList<Patient> = arrayListOf(
        Patient("Agustin", "Cragno", LocalDate.of(1998, 7, 30), 40123864, "acragno@frba.utn.edu.ar", LocalDate.of(2023, 1, 9), R.drawable.avatar_26, "Lunes y Miercoles", "18:00", assignments),
        Patient("Jose", "Bruzzoni", LocalDate.of(1991, 2, 3), 43181238, "jbruzzoni@frba.utn.edu.ar", LocalDate.of(2018, 12, 9), R.drawable.avatar_12, "Martes y Jueves", "19:00", assignments),
        Patient("Alexander", "Martinez", LocalDate.of(1995, 5, 4), 37186477, "amartinez@gmail.com", LocalDate.of(2023, 5, 15), R.drawable.avatar_17, "Miercoles", "19:00", assignments)
    )

    val therapists: MutableList<Phono> = arrayListOf(
        Phono("1", "Lionel", "Scaloni", R.drawable.avatar_12, patients, exercises),
        Phono("2", "Jorge", "Sampaoli", R.drawable.avatar_26, patients, exercises)
    )

    val users: List<User> = patients.map { User(it.name, "123", it) } + therapists.map { User(it.name, "123", it) }
}