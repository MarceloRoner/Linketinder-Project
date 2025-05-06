package controller.rest

import com.google.gson.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.lang.reflect.Type

class LocalDateAdapter
        implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE

    @Override
    JsonElement serialize(LocalDate src,
                          Type t, JsonSerializationContext ctx) {
        new JsonPrimitive(src.format(FMT))
    }

    @Override
    LocalDate deserialize(JsonElement json,
                          Type t, JsonDeserializationContext ctx)
            throws JsonParseException {
        LocalDate.parse(json.asString, FMT)
    }
}
