package com.grigoriy.repository;

import java.util.concurrent.TimeUnit;

public interface RateLimiter {
    boolean allowRequest();
}
