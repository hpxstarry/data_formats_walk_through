package com.hupx.dataformat.avro;

import com.google.common.collect.Lists;
import com.hupx.dataformat.pojo.User;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * This is an example for Avro example. It shows below ideas
 * 1) schema defintion & code generation.
 * 2) serialize/deserialize of multiple records
 */
public class AvroExample {
    public AvroExample() {
    }

    public void serialize(String filePath) throws IOException {
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
        try (DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter)) {
            dataFileWriter.create(Users.USER1.getSchema(), new File(filePath));
            dataFileWriter.append(Users.USER1);
            dataFileWriter.append(Users.USER2);
        }
    }


    public byte[] estimateSerializeSize(List<User> users) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
        try (DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter)) {
            dataFileWriter.create(Users.USER1.getSchema(), byteArrayOutputStream);
            for (User user : users) {
                dataFileWriter.append(user);
            }
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
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
        System.out.println("Avro will be written to " + new File(filePath).getAbsolutePath());

        AvroExample avroExample = new AvroExample();
        avroExample.serialize(filePath);
        avroExample.deserialize(filePath);

        List<Integer> userNums = Lists.newArrayList(1, 2, 5, 10, 20);
        for (int userNum : userNums) {
            List<User> users = Collections.nCopies(userNum, Users.USER_WITH_100_BLOGS);

            byte[] avroData = avroExample.estimateSerializeSize(users);
            System.out.println("Number of Users = " + userNum);
            System.out.println("Avro size       = " + avroData.length
                    + " bits");

            String json = users.toString();
            System.out.println("JSON size       = " + json.length()
                    + " bits");
            //System.out.println(new String(json));
        }
    }
}
