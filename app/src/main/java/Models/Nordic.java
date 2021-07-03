package Models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Nordic implements Serializable {

    public static final int MAXRISK = 10;
    public static final int LEVEL1 = 6;
    public static final int LEVEL2 = 9;
    private String _id, _fkIdUser;
    private Date _fillDate;
    private int _neck, _shoulder, _upBack,
            _elbow, _wrstHand, _lowBack,
            _hip, _knee, _ankFeet;
    private int _riskNordic;
    User user = new User(_fkIdUser);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public String getId(){ return _id;}
    public String getFkIdUser(){return _fkIdUser;}
    public void setFkIdUser(String fkIdUser){
        _fkIdUser=fkIdUser;
    }
    public String getFillDate() {
        return sdf.format(_fillDate);
    }
    public void setFillDate(String date) {
        Date _dt;
        try {
            _dt = sdf.parse(date);
        }
        catch (ParseException e) {
            _dt = new Date("1900/01/01");
        }
        _fillDate = _dt;
    }
    public int getNeck() {
        return _neck;
    }
    public void setNeck(int neck) {
        _neck = neck;
    }
    public int getShoulder() {
        return _shoulder;
    }
    public void setShoulder(int shoulder) {
        _shoulder = shoulder;
    }
    public int getUpBack() {
        return _upBack;
    }
    public void setUpBack(int upBack) {
        _upBack = upBack;
    }
    public int getElbow() {
        return _elbow;
    }
    public void setElbow(int elbow) {
        _elbow = elbow;
    }
    public int getWrstHand() {
        return _wrstHand;
    }
    public void setWrstHand(int wrstHand) {
        _wrstHand = wrstHand;
    }
    public int getLowBack() {
        return _lowBack;
    }
    public void setLowBack(int lowBack) {
        _lowBack = lowBack;
    }
    public int getHip() {
        return _hip;
    }
    public void setHip(int hip) {
        _hip = hip;
    }
    public int getKnee() {
        return _knee;
    }
    public void setKnee(int knee) {
        _knee = knee;
    }
    public int getAnkFeet() {
        return _ankFeet;
    }
    public void setAnkFeet(int ankFeet) {
        _ankFeet = ankFeet;
    }
    public void setErgoRisk(){
        _riskNordic=0;
        _riskNordic=(getNeck()+getShoulder()+getUpBack()+
                getElbow()+getWrstHand()+getLowBack()+
                getHip()+getKnee()+getAnkFeet())/9;
        if (getNeck()>LEVEL1)_riskNordic++;
        if (getNeck()>=LEVEL2)_riskNordic++;
        if (getShoulder()>LEVEL1) _riskNordic++;
        if (getShoulder()>=LEVEL2)_riskNordic ++;
        if (getUpBack()>LEVEL1)_riskNordic++;
        if (getUpBack()>=LEVEL2)_riskNordic++;
        if (getElbow()>LEVEL1 )_riskNordic++;
        if (getElbow()>=LEVEL2)_riskNordic++;
        if (getWrstHand()>LEVEL1) _riskNordic++;
        if (getWrstHand()>=LEVEL2) _riskNordic++;
        if (getLowBack()>LEVEL1 ) _riskNordic++;
        if (getLowBack()>=LEVEL2) _riskNordic++;
        if (getHip()>LEVEL1 ) _riskNordic++;
        if (getHip()>=LEVEL2) _riskNordic++;
        if (getKnee()>LEVEL1 ) _riskNordic++;
        if (getKnee()>=LEVEL2) _riskNordic++;
        if (getAnkFeet()>LEVEL1 ) _riskNordic++;
        if (getAnkFeet()>=LEVEL2) _riskNordic++;
        if (user.getHealthRisk()>1) _riskNordic++;
        if (_riskNordic>MAXRISK) _riskNordic = MAXRISK;
    }

    public int getErgoRisk() {
        return _riskNordic;
    }

    public Nordic(){
        _id = UUID.randomUUID().toString();
        setFkIdUser(user.getID());
        setFillDate("");
        setNeck(0);
        setShoulder(0);
        setUpBack(0);
        setElbow(0);
        setWrstHand(0);
        setLowBack(0);
        setHip(0);
        setKnee(0);
        setAnkFeet(0);
        setErgoRisk();
    }

    public Nordic(String dbID){
        _id = dbID;
        setFkIdUser(user.getID());
        setFillDate("");
        setNeck(0);
        setShoulder(0);
        setUpBack(0);
        setElbow(0);
        setWrstHand(0);
        setLowBack(0);
        setHip(0);
        setKnee(0);
        setAnkFeet(0);
        setErgoRisk();
    }


    @Override
    public String toString(){
        return  "\n"+ getFillDate() +"\n"+ "Risco Ergonómico: " + getErgoRisk();
    }
    
}
