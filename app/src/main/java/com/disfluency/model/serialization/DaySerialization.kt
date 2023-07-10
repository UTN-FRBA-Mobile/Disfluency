package com.disfluency.model.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import java.time.DayOfWeek

object DayDeserializer : JsonDeserializer<List<DayOfWeek>>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): List<DayOfWeek> {
        val node = p.readValueAsTree<JsonNode>()
        return node.map { d -> DayOfWeek.valueOf(d.textValue()) }
    }
}

object DaySerializer : JsonSerializer<List<DayOfWeek>>() {
    override fun serialize(
        value: List<DayOfWeek>,
        gen: JsonGenerator,
        serializers: SerializerProvider
    ) {
        with(gen) {
            writeArray(value.map { d -> d.toString() }.toTypedArray(), 0, value.size)
        }
    }
}