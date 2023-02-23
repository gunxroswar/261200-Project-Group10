public class Region {

    private int posX; //ตำแหน่งแกนx
    private int posY; //ตำแหน่งแหนy
    private long deposit;//จำนวนเงินฝากในรีเจี้ยนนี้
    private long max_dep;//คเพดานเงินสูงสุดที่จำกัดไว้
    private long interest_pct;//เปอร์เซ็นต์ปันผล
    private String Owner = null;//ชื่อที่เอาไว้เช็คว่าเป็นใครเป็นเจ้าจอง

    private boolean CityCenter = false; //เช็คว่าเป็นCityCenterหรือไม่

    //คอนทรัคชั่นฟังก์ชั่น
    public Region(long MaxDeposit,long interest_pct,int posX ,int posY){
        this.deposit = 0;
        this.max_dep = MaxDeposit;
        this.interest_pct = interest_pct;
        this.posX = posX;
        this.posY = posY;
    }

    /* เป็นฟังก์ชั่นที่เอาไว้คำนวนค่า rจากสูตร b * log10 d * ln t. หรือเอัตตราาดอกเบี้นี่จะได้หลังจบเทิร์นแล้วเอาไปคำนวณหาจำนวนเงินที่ะจได้หลังจบเทิร์น
    * @param : turn รับค่าturn มาคำนวณ
    * @return : รีเทิร์นค่าที่ได้จากการคำนวณโดยใช้สูตร
    * */
    public long Int(int turn){
        return (long) (this.interest_pct*(Math.log10(this.deposit))*Math.log(turn));
    }

    /*รีเทิร์นค่าของตัวแปล CityCenter
     */
    public boolean isCityCenter(){
        return CityCenter;
    }
    //เอาไว้เช็คว่าสูญเสียการครอบครองรีเจี้ยนนี้หรือไม่
    //Uncomplete
    private boolean PossessionLost(){
        return true;
    }
    /*เอาไว้คำนวณความเสียหายแล้วคำนวณว่าเราเสียการครอบครองไปแล้วหรือยัง
    * @param : รับค่าความเสียหาย
    * @return : รีเทิร์นว่ายิงแตกหรือไม่
    * @effects : จะไปเรียกฟังชั่นPossessionLost
     */
    protected boolean beShot(int damage){
        this.deposit -= damage;
        if(Math.floor(deposit) > 0){
            return false;
        }else{
            PossessionLost();
            return true;
        }
    }
    //เปลี่ยนสถานะของการเป็นCityCEnter
    public void changeCityCenter(boolean change){
        this.CityCenter = change;
    }
    //เปลี่ยนผู้ครอบครองRegion
    public void changeOwner(String Owner){
        this.Owner = Owner;
    }

    /*ตรวจสอบความเป็นเจ้าของในรีเจี้ยนนั้นๆ
    *@param : ตัวแปรname ที่จะเอามาเช็คว่าใช่ของเราหรือไม่
    *@return : คืนค่าBooleanออกไป
     */
    public boolean isOwner(String name){
        return Owner.equals(name);
    }
    /*เพิ่มเงินในคลังของRegionนั้นๆ
    * @param : จำนวนเงินที่จะนำมาลงทุน
    * @return : -
    * @effects : ค่าdepositของregionนั้นๆถูกเปลี่ยน และถ้าจำนวนเงินเกินแคปที่กำหนดไว้ ก็จะเปลี่ยนให้deposit = max_dep
     */
    protected void invest(int capital){
        this.deposit += capital;
        if(this.deposit >= max_dep) this.deposit = max_dep;
    }
    /* คืนค่าเงินในคลังออกมา และ ถ้าไม่ได้เป็นเจ้าของจะรีเทิร์นค่าติดลบออกมา
    * @param : ชื่อเพื่อนำมาเช็คว่าเราเป็นเจ้าของเงินก้อนนั้นหรือไมา
    * @return : รีเทิร์นเงินในคลังออกมา จะเป็นบวกถ้าเราเป็นเจ้าของ เป็นลบถ้าเราไม่ได้เป็นเจ้าของ
     */
    public int giveDeposit(String name){
        if(isOwner(name)) return (int) Math.floor(deposit);
        else return ((int) Math.floor(deposit))*-1;
    }

    /* ฟังก์ชั่นที่จะอัพเดทเงินในคลังของเราหลังจบเทิร์น
    * @param : จำนวนเทิร์นเพื่อนำมาเข้าฟังก์ชั่น Int(turn)
    * @return : -
    * @effects : ค่าเงินในคลังถูกเปลี่ยนไป
     */
    protected void updateDeposit(int turn){
        long r = Int(turn);
        this.deposit += this.deposit*r/100;
    }

    /* ฟังก์ชันที่จะเก็บเงินในคลังออกมาโดยถ้าเงินที่ถอนไม่มากกว่าเงินในคลังก็จะถอนได้ และ ถ้าถอนจนหมดก็จะสูญเสียความเป็นเจ้าของในRegionนั้นๆ
    * @param : จำนวนเงินที่จะถอน
    * @return : ถอนได้หรือไม่
    * @effects : เงินในคลังลดลง และ ถ้าเงินใสนคลังหมดก็จะสูญเสียความเป็นเจ้าของในRegionนั้นไป
     */
    protected boolean collect(int takenmoney){
        if(takenmoney <= deposit){
            this.deposit -= takenmoney;
            if(Math.floor(deposit) <= 0){
                PossessionLost();
            }
            return true;
        }else return false;
    }

}
