package com.hupx.dataformat.parquet;

import com.hupx.dataformat.avro.User;
import com.hupx.dataformat.avro.Users;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.util.HadoopInputFile;

import java.io.File;
import java.io.IOException;

public final class ParquetExample {

    public ParquetExample() {
    }

    public void serialize(String filePath) throws IOException {
        new File(filePath).delete();
        Path path = new Path(filePath);
        try (ParquetWriter<User> writer = AvroParquetWriter
                .<User>builder(path)
                .withSchema(Users.USER1.getSchema())
                .withConf(new Configuration())
                .withCompressionCodec(CompressionCodecName.SNAPPY)
                .build()) {
            writer.write(Users.USER1);
            writer.write(Users.USER2);
        }
    }

    public void deserialize(String filePath) throws IOException {
        Path path = new Path(filePath);
        try (ParquetReader<User> reader = AvroParquetReader
                .<User>builder(HadoopInputFile.fromPath(path, new Configuration()))
                .withConf(new Configuration())
                .build()) {
            User user;
            while ((user = reader.read()) != null) {
                System.out.println(user);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String filePath = "./users.parquet";
        System.out.println("Parquet will be written to " + new File(filePath).getAbsolutePath());

        ParquetExample avroExample = new ParquetExample();
        avroExample.serialize(filePath);
        avroExample.deserialize(filePath);
    }
}