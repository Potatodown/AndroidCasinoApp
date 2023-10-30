package ca.on.conestogac.gambleapp;

import android.app.Application;

public class PayRollApplication extends Application {

    public int slots(int s1, int s2, int s3, int betMultiplier) {
        int win = 0;
        boolean found = false;
        for(int x = 0; x<= 5; x++){
            if (s1 == s2 && s2 == s3 && s1 == x) {
                switch (x){
                    case 4:
                        win = 1000 * betMultiplier;
                        break;
                    case 1:
                        win = 750 * betMultiplier;
                        break;
                    case 0:
                        win = 250 * betMultiplier;
                        break;
                    default:
                        win = 60 * betMultiplier;
                        break;
                }
                found = true;
            }
            else if (s1 == s2 && s1 == x){
                win = twoSlot(x, betMultiplier);
                found = true;
            }
            else if (s2 == s3 && s2 ==x){
                win = twoSlot(x, betMultiplier);
                found = true;
            }
            else if(s1 == s3 && s1 == x) {
                win = twoSlot(x, betMultiplier);
                found = true;
            }
            else{
                if(!found) {
                    win = -1;
                }
            }
        }

        return win;
    }

    public int twoSlot(int x, int betMultiplier){
        int win = 0;
        switch (x){
            case 4:
                win = 300 * betMultiplier;
                break;
            case 1:
                win = 250 * betMultiplier;
                break;
            case 0:
                win = 100 * betMultiplier;
                break;
            default:
                win = 30 * betMultiplier;
                break;
        }
        return win;
    }
}
