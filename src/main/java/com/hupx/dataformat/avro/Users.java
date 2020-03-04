package com.hupx.dataformat.avro;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public final class Users {
    public static final User USER1 = User.newBuilder()
            .setFavoriteColor("RED")
            .setHeight(5)
            .setName("Jack")
            .setFavoriteNumber(1)
            .setAccount(Account.newBuilder().setId("1").setBalance(10.0).build())
            .setBlogs(Lists.newArrayList(Blog.newBuilder().setId("1").setContent("Hello world").build()))
            .build();

    public static final User USER2 = User.newBuilder()
            .setFavoriteColor("Blue")
            .setHeight(6)
            .setName("King")
            .setFavoriteNumber(1)
            .setAccount(Account.newBuilder().setId("2").setBalance(11.0).build())
            .setBlogs(Lists.newArrayList(Blog.newBuilder().setId("2").setContent("hi there").build(),
                    Blog.newBuilder().setId("5").setContent("good day").build()))
            .build();
}
