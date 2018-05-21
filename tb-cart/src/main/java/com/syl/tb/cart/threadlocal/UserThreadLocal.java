package com.syl.tb.cart.threadlocal;


import com.syl.tb.cart.pojo.User;

public class UserThreadLocal {
    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    private UserThreadLocal(){}

    public static void set(User user){
        LOCAL.set(user);
    }
    public static User get(){
        return LOCAL.get();
    }
}
