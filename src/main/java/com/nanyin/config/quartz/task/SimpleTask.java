package com.nanyin.config.quartz.task;

/**
 * Created by NanYin on 2019/9/19.
 */
public class SimpleTask implements Task {
    @Override
    public void execute(String... args) {
        System.out.println("simple task,..");
    }
}
