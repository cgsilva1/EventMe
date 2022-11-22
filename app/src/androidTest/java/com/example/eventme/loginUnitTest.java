package com.example.eventme;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.eventme.ui.login.LoginActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

public class loginUnitTest {
    @Test
    public void invalidEmail(){
        int result= LoginActivity.checkLoginInfo("testtest.com","password");
        assertEquals(result,1);
    }
    @Test
    public void invalidPassword(){
        int result=LoginActivity.checkLoginInfo("test@test.com","pass");
        assertEquals(result,2);
    }

    @Test
    public void validInfo() {
        int result=LoginActivity.checkLoginInfo("test@test.com","password");
        assertEquals(result,0);
    }
}
