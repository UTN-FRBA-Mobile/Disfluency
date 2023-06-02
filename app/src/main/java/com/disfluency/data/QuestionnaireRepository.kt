package com.disfluency.data

import com.disfluency.model.Patient

object QuestionnaireRepository {

    fun getCompletedQuestionnairesCountByPatient(patient: Patient): Int {
        return 3; //TODO: implement
    }

    fun getPendingQuestionnairesCountByPatient(patient: Patient): Int {
        return 0; //TODO: implement
    }
}