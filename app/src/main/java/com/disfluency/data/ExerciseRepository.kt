package com.disfluency.data

import com.disfluency.model.Exercise
import com.disfluency.model.ExerciseAssignment
import com.disfluency.model.ExercisePractice
import com.disfluency.model.Patient
import java.io.File
import java.time.LocalDate

object ExerciseRepository {
    private var testUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"

    val exercises = listOf(
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

    val longListForTest = exercises

    val assignments = listOf(
        ExerciseAssignment(id = 1, exerciseId = 6, patientId = 40123864, data = LocalDate.of(2023, 6, 26)),
        ExerciseAssignment(id = 2, exerciseId = 4, patientId = 40123864, data = LocalDate.of(2023, 6, 25))
    )

    fun getExerciseById(id: Int): Exercise{
        return exercises.first { exercise -> exercise.id == id }
    }

    fun getAssignmentById(id: Int): ExerciseAssignment {
        return assignments.first { assignment -> assignment.id == id }
    }

    fun saveExercisePractice(assignmentId: Int, audio: File){
        val practice = ExercisePractice(
            id = assignmentId,
            audioUrl = "", //TODO: subir audio
            date = LocalDate.now()
        )
    }

    fun getCompletedExercisesCountByPatient(patient: Patient): Int {
        return 15; //TODO: implement
    }

    fun getPendingExercisesCountByPatient(patient: Patient): Int {
        return 2; //TODO: implement
    }


}