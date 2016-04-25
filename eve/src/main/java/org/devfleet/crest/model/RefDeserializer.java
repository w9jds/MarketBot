package org.devfleet.crest.model;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

class RefDeserializer extends StdDeserializer<String> {

    public RefDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final JsonNode node = (JsonNode)p.getCodec().readTree(p).get("href");
        if (null == node) {
            return null;
        }
        return node.asText();
    }
}
