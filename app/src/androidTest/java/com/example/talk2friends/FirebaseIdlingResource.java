package com.example.talk2friends;

import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class FirebaseIdlingResource implements IdlingResource {

    private volatile ResourceCallback resourceCallback;
    private final AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return FirebaseIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    // Sets the idle state. Called when Firebase operation starts/ends
    public void setIdleState(boolean isIdleNow) {
        this.isIdleNow.set(isIdleNow);
        if (isIdleNow && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }
}



