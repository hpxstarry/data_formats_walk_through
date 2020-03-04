package com.hupx.dataformat.parquet;
// Generic Avro dependencies

// Hadoop stuff
// Generic Parquet dependencies
// Avro->Parquet dependencies

import com.hupx.dataformat.avro.Account;
import com.hupx.dataformat.avro.AvroExample;
import com.hupx.dataformat.avro.User;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ParquetExample {
    private User user1;
    private User user2;

    public ParquetExample() {
        this.user1 = User.newBuilder()
                .setFavoriteColor("RED")
                .setHeight(5)
                .setName("Jack")
                .setFavoriteNumber(1)
                .setAccount(Account.newBuilder().setId("1").setBalance(10.0).build())
                .build();

        this.user2 = User.newBuilder()
                .setFavoriteColor("Blue")
                .setHeight(6)
                .setName("King")
                .setFavoriteNumber(1)
                .setAccount(Account.newBuilder().setId("2").setBalance(11.0).build())
                .build();
    }

    public void serialize(String filePath) throws IOException {
        new File(filePath).delete();
        Path path = new Path(filePath);
        try (ParquetWriter<User> writer = AvroParquetWriter
                .<User>builder(path)
                .withSchema(user1.getSchema())
                .withConf(new Configuration())
                .withCompressionCodec(CompressionCodecName.SNAPPY)
                .build()) {
            writer.write(user1);
            writer.write(user2);
        }
    }

    public void deserialize(String filePath) throws IOException {
        Path path = new Path(filePath);
        try (ParquetReader<User> reader = AvroParquetReader
                .<User>builder(path)
                .withConf(new Configuration())
                .build()) {
            User user;
            while( (user = reader.read()) != null) {
                System.out.println(user);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/hupx/workspace_trial/dataformats/users.parquet";

        ParquetExample avroExample = new ParquetExample();
        avroExample.serialize(filePath);
        avroExample.deserialize(filePath);
    }
}