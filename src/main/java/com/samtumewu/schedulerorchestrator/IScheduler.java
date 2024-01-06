package com.samtumewu.schedulerorchestrator;

public interface IScheduler {
    void start();
    boolean stop();
    void refresh();
}
