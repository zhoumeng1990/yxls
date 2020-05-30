package com.coder.framework

import com.squareup.moshi.*
import org.json.JSONObject.NULL

/**
 * Created by Zero on 2020/05/30.
 *
 */
class NullStringAdapter : JsonAdapter<String>() {
    @FromJson
    override fun fromJson(reader: JsonReader?): String {
        if (reader == null) {
            return ""
        }

        return if (reader.peek() == NULL) "" else reader.nextString()
    }

    @ToJson
    override fun toJson(writer: JsonWriter?, value: String?) {
        writer?.value(value)
    }
}