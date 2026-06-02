package com.example.atmsimulator.util;

import com.example.atmsimulator.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SessionData {
    public String currentCNIC;
    public static String formNumber;
    public  UserEntity tempUser;
    public  boolean isEditing = false;
    public boolean cnicAvalibleWhileReg=false;
}
