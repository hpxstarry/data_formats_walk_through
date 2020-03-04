package com.hupx.dataformat.avro;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hupx.dataformat.pojo.Account;
import com.hupx.dataformat.pojo.Blog;
import com.hupx.dataformat.pojo.User;

import java.util.List;

public final class Users {
    public static final User USER1 = User.newBuilder()
            .setFavoriteColor("RED")
            .setHeight(5)
            .setName("Jack")
            .setFavoriteNumber(1)
            .setAccount(Account.newBuilder().setId("1").setBalance(10.0).build())
            .setBlogs(Lists.newArrayList(Blog.newBuilder().setBlogId("1").setContent("Hello world").build()))
            .build();

    public static final User USER2 = User.newBuilder()
            .setFavoriteColor("Blue")
            .setHeight(6)
            .setName("King")
            .setFavoriteNumber(1)
            .setAccount(Account.newBuilder().setId("2").setBalance(11.0).build())
            .setBlogs(Lists.newArrayList(Blog.newBuilder().setBlogId("2").setContent("hi there").build(),
                    Blog.newBuilder().setBlogId("5").setContent("good day").build()))
            .build();

    public static final List<Blog> HUNDRED_BLOGS;
    static {
        List<Blog> blogs = Lists.newArrayListWithExpectedSize(100);
        for (int i = 0;i < 100;++i) {
            blogs.add(Blog.newBuilder().setBlogId(""+i).setContent("").build());
        }
        HUNDRED_BLOGS = ImmutableList.copyOf(blogs);
    }

    public static final User USER_WITH_100_BLOGS = User.newBuilder()
            .setFavoriteColor("Blue")
            .setHeight(6)
            .setName("King")
            .setFavoriteNumber(1)
            .setAccount(Account.newBuilder().setId("3").setBalance(11.0).build())
            .setBlogs(HUNDRED_BLOGS)
            .build();
}
