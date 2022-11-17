package com.example.eventme;

import static org.junit.Assert.assertEquals;

import com.example.eventme.ui.register.Register;

import org.junit.Test;

public class registerUnitTest {

    @Test
    public void invalidEmail() {
        int result= Register.checkReginfo("invalid","testtest","test test","1234567890"); //no @ sign
        assertEquals(result,1);
    }
    @Test
    public void invalidPassword(){
        int result=Register.checkReginfo("david@usc.edu","test","test test","1234567890");//password not long enough
        assertEquals(result,2);
    }
    @Test
    public void invalidName(){
        int result=Register.checkReginfo("david@usc.edu","testtest","nospace","1234567890");//no space in name
        assertEquals(result,3);
    }
    @Test
    public void invalidID(){
        int result=Register.checkReginfo("david@usc.edu","testtest","test test","1");//USC ID not long enough
        assertEquals(result,4);
    }
    @Test
    public void validInfo(){
        int result=Register.checkReginfo("david@usc.edu","testtest","test test","1234567890");
        assertEquals(result,0);
    }
}
