import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Player {
    String name; //ชื่อของผู้เล่น
    long budget; //จำนวนเงินที่มี
    Address CityCrew = null; //สร้าง Address CityCrew
    Address CityCenter = null; // สร้าง Address CityCenter
    HashMap<String, Integer> PersonalValue = null; //เอาไว้เก็บตัวแปรของผู็เล่นนั้นๆ
    int player_turn = 0; //จำนวนเทิร์นที่ผู้เล่นเล่นไปแล้ว
    ArrayList<Address> RegionPossessing = new ArrayList<>(); //เก็บตำแหน่งRegionที่ครอบครองไว้

    Player(String name,long budget,long x,long y){
        this.name = name;
        this.budget = budget;
        CityCrew = new Address((int) x,(int)y);
        CityCenter = new Address((int)x,(int)y);
        PersonalValue = new HashMap<String,Integer>();
    }

    void addValue(String s,int i){
        if(PersonalValue.containsKey(s)){
            PersonalValue.replace(s,i);
        }else{
            PersonalValue.put(s,i);
        }
    }
    /*รีเทิร์นค่าMapออกมา*/
    HashMap<String,Integer> giveValue(){

        return new HashMap<>(PersonalValue);
    }

}
