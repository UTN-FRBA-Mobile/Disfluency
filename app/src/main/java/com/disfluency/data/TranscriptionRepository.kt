package com.disfluency.data

import com.disfluency.model.analysis.AnalysedWord
import com.disfluency.model.analysis.Analysis
import com.disfluency.model.analysis.CompositeDisfluency
import com.disfluency.model.analysis.DisfluencyType.*
import com.disfluency.model.analysis.SingleDisfluency

object TranscriptionRepository {

    val mockedTranscriptions = listOf(
        Analysis(
            listOf(
                AnalysedWord("La",1220,1280),
                AnalysedWord("fiabilidad",1400,2140, SingleDisfluency(V)),
                AnalysedWord("es",2420,2500),
                AnalysedWord("la",2600,2660),
                AnalysedWord("capacidad",2760,3420),
                AnalysedWord("de",3580,3660),
                AnalysedWord("un",3740,3840, SingleDisfluency(RF)),
                AnalysedWord("sistema",3960,4460),
                AnalysedWord("o",4900,4920),
                AnalysedWord("componente",5020,5680, CompositeDisfluency(listOf(M, I))),
                AnalysedWord("para",6240,6440),
                AnalysedWord("desempeñar",6600,7340),
                AnalysedWord("las",7460,7580),
                AnalysedWord("funciones",7640,8200),
                AnalysedWord("especificadas,",9040,10020),
                AnalysedWord("cuando",10920,11140, SingleDisfluency(I)),
                AnalysedWord("se",11220,11280, SingleDisfluency(V)),
                AnalysedWord("usa",11420,11640),
                AnalysedWord("bajo",11980,12220),
                AnalysedWord("unas",12340,12520, CompositeDisfluency(listOf(V, RF))),
                AnalysedWord("condiciones",12640,13300, SingleDisfluency(V)),
                AnalysedWord("y",13580,13640),
                AnalysedWord("periodo",13740,14160),
                AnalysedWord("de",14240,14280, SingleDisfluency(RP)),
                AnalysedWord("tiempo",14380,14700),
                AnalysedWord("determinados.",14900,16660)


//                ,
//                AnalysedWord("fiabilidad",1400,2140, SingleDisfluency(V)),
//                AnalysedWord("es",2420,2500),
//                AnalysedWord("la",2600,2660),
//                AnalysedWord("capacidad",2760,3420),
//                AnalysedWord("de",3580,3660),
//                AnalysedWord("un",3740,3840, SingleDisfluency(RF)),
//                AnalysedWord("sistema",3960,4460),
//                AnalysedWord("o",4900,4920),
//                AnalysedWord("componente",5020,5680, CompositeDisfluency(listOf(M, I))),
//                AnalysedWord("para",6240,6440),
//                AnalysedWord("desempeñar",6600,7340),
//                AnalysedWord("las",7460,7580),
//                AnalysedWord("funciones",7640,8200),
//                AnalysedWord("especificadas,",9040,10020),
//                AnalysedWord("cuando",10920,11140, SingleDisfluency(I)),
//                AnalysedWord("se",11220,11280, SingleDisfluency(V)),
//                AnalysedWord("usa",11420,11640),
//                AnalysedWord("bajo",11980,12220),
//                AnalysedWord("unas",12340,12520, CompositeDisfluency(listOf(V, RF))),
//                AnalysedWord("condiciones",12640,13300, SingleDisfluency(V)),
//                AnalysedWord("y",13580,13640),
//                AnalysedWord("periodo",13740,14160),
//                AnalysedWord("de",14240,14280, SingleDisfluency(RP)),
//                AnalysedWord("tiempo",14380,14700),
//                AnalysedWord("determinados.",14900,16660)
            )
        )
    )

    fun getAnalysisByPracticeId(practiceId: String): Analysis {
        return mockedTranscriptions[0]
    }
}