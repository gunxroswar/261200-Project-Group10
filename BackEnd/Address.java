public class Address {
    protected int posX;
    protected int posY;
    Address(int x,int y){
        posX = x;
        posY = y;
    }

    int PositionX(){
        return posX;
    }

    int PositionY(){
        return posY;
    }
}
