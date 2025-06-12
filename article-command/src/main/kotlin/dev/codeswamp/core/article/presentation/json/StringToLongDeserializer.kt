package dev.codeswamp.core.article.presentation.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class StringToLongDeserializer: JsonDeserializer<Long>() {
    override fun deserialize(
        p: JsonParser,
        ctxt: DeserializationContext?
    ): Long {
        return p.text.toLong()
    }
}