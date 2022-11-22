package com.example.eventme;

import static org.junit.Assert.assertEquals;

import com.example.eventme.ui.register.Register;

import org.junit.Test;

public class registerUnitTest {
    @Test
    public void emptyName(){
        int result=Register.checkReginfo("","test@test","00/00/0000","1234567890");//name is empty
        assertEquals(result,1);
    }
    @Test
    public void emptyEmail() {
        int result= Register.checkReginfo("test test","","00/00/0000","1234567890"); //email empty
        assertEquals(result,2);
    }
    @Test
    public void emptyDOB(){
        int result=Register.checkReginfo("test test","test@test","","1");//dob empty
        assertEquals(result,3);
    }
    @Test
    public void emptyPassword(){
        int result=Register.checkReginfo("test test","test@test","00/00/0000","");//password empty
        assertEquals(result,4);
    }

    @Test
    public void invalidPassword(){
        int result=Register.checkReginfo("test test","test@test","00/00/0000","1234");//password not long enough
        assertEquals(result,5);
    }

    @Test
    public void invalidName(){
        int result=Register.checkReginfo("testtest","test@test","00/00/0000","1234567890");//name doesnt have space
        assertEquals(result,6);
    }
    @Test
    public void invalidEmail() {
        int result= Register.checkReginfo("test test","testtest","00/00/0000","1234567890"); //email doesnt have @
        assertEquals(result,7);
    }


    @Test
    public void validInfo(){
        int result=Register.checkReginfo("test test","test@test","00/00/0000","1234567890");
        assertEquals(result,0);
    }
}
