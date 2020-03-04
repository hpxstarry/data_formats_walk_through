package com.hupx.dataformat.avro;

import org.apache.commons.io.FileUtils;
import org.kitesdk.data.spi.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * failed to infer {"85": 3}
 *
 * */

public class SchemaInference {
    public static void main(String[] args) throws IOException {
        URL url = SchemaInference.class.getResource("/JSON/planningdetails.es.normal.json");
        System.out.println(url.getFile());
        String jsonContent = FileUtils.readFileToString(new File(url.getFile()));
        System.out.println(jsonContent);
        String schema = JsonUtil.inferSchema(JsonUtil.parse(jsonContent), "mySchema").toString();
        System.out.println(schema);
    }
}
