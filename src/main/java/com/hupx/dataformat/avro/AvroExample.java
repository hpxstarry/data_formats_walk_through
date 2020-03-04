package com.hupx.dataformat.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;


/**
 * This is an example for Avro example. It shows below ideas
 * 1) schema defintion & code generation.
 * 2) serialize/deserialize of multiple records
 */
public class AvroExample {
    private User user1;
    private User user2;

    public AvroExample() {
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
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
        try (DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter)) {
            dataFileWriter.create(user1.getSchema(), new File(filePath));
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
        }
    }

    public void deserialize(String filePath) throws IOException {
        DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
        DataFileReader<User> dataFileReader = new DataFileReader<User>(new File(filePath), userDatumReader);
        User user = null;
        while (dataFileReader.hasNext()) {
            // Reuse user object by passing it to next(). This saves us from
            // allocating and garbage collecting many objects for files with
            // many items.
            user = dataFileReader.next(user);
            System.out.println(user);
        }
    }

    public static void main(String[] args) throws IOException {
        String filePath = "./users.avro";
        System.out.println("Parquet will be written to " + new File(filePath).getAbsolutePath());

        AvroExample avroExample = new AvroExample();
        avroExample.serialize(filePath);
        avroExample.deserialize(filePath);
    }
}
