package Models;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.example.ergomanager.MainActivity;
import com.example.ergomanager.UserCRUD;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import Models.NordicHelper;

public class User implements Serializable {

    private String _id, _name, _email, _pin, _smoker, _genre;
    private Date _birthDate, _convertedDate;
    private int _age, _riskProfile, _active;
    private double _height, _weight, _imcResult;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    public static final int MAXNOME = 128;
    public static final double MAXHEIGHT = 2.80;
    public static final double MINHEIGHT = 0.80;
    public static final double MAXWEIGHT = 250;
    public static final double MINWEIGHT = 20;
    public static final int IMC_UNDER = 18;
    public static final int IMC_OK = 25;
    public static final int IMC_OVER1 = 30;
    public static final int IMC_OVER2 = 35;
    public String getID() {
        return _id;
    }

    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
        if (_name.length()>MAXNOME) _name =_name.substring(0, MAXNOME);
    }
    public String getEmail() {
        return _email;
    }
    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public void setEmail(String email) {
        _email =  email;
    }
    public String getPIN() {
        return _pin;
    }
    public void setPIN(String pin) {
        _pin = pin;
    }
    public String getBirthDate() {
        return sdf.format(_birthDate);
    }
    public void setBirthDate(String birthDate) {
        try {
            _convertedDate = sdf.parse(birthDate);
        }
        catch (ParseException e) {
            _convertedDate = new Date("1900/01/01");
        }
        _birthDate = _convertedDate;
    }
    public void setAge(String dob){
            int years = 0;
            int months = 0;
            int days = 0;
            Date date1= _convertedDate;

            //create calendar object for birth day
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTimeInMillis(date1.getTime());

            //create calendar object for current day
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);

            //Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            int currMonth = now.get(Calendar.MONTH) + 1;
            int birthMonth = birthDay.get(Calendar.MONTH) + 1;

            //Get difference between months
            months = currMonth - birthMonth;

            //if month difference is in negative then reduce years by one
            //and calculate the number of months.
            if (months < 0)
            {
                years--;
                months = 12 - birthMonth + currMonth;
                if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                    months--;
            } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            {
                years--;
                months = 11;
            }

            //Calculate the days
            if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
                days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
            else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            {
                int today = now.get(Calendar.DAY_OF_MONTH);
                now.add(Calendar.MONTH, -1);
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
            }
            else
            {
                days = 0;
                if (months == 12)
                {
                    years++;
                    months = 0;
                }
            }
            _age=years;

    }
    public int getAge(){
        return _age;
    }
    public String getGenre() {
        return _genre;
    }
    public void setGenre(String genre) {
        _genre = genre;
    }
    public double getHeight() {
        return _height;
    }
    public void setHeight(double height) {
        _height = height;
        if (_height < MINHEIGHT) _height = MINHEIGHT;
        if (_height > MAXHEIGHT) _height = MAXHEIGHT;
    }
    public double getWeight() {
        return _weight;
    }
    public void setWeight(double weight) {
        _weight = weight;
        if (_weight < MINWEIGHT)_weight = MINWEIGHT;
        if (_weight > MAXWEIGHT) _weight = MAXWEIGHT;
    }
    public void setIMC(double height, double weight){
        _imcResult =  weight / (height*height);
        DecimalFormat df = new DecimalFormat("#.##");
        _imcResult = Double.valueOf(df.format(_imcResult));
           }
    public double getIMC(){
        return _imcResult;
    }
    public String isSmoker() {
        return _smoker;
    }
    public void setSmoker(String smoker) {
        _smoker = smoker;
    }
    public int getActive() {
        return _active;
    }
    public void setActive(int active) {
        _active = active;
    }
    public int getHealthRisk(){
        _riskProfile=0;
        if (getAge()>55) _riskProfile++;
        if (getIMC()>30) _riskProfile++;
        if (getIMC()>35) _riskProfile++;
        if (isSmoker().equals("Fumador")) _riskProfile ++;
        if (getActive() < 3) _riskProfile ++;
        if (getActive()>5)_riskProfile --;
        if (_riskProfile<0)_riskProfile=0;
        return _riskProfile;
    }

    public User(){
        _id = UUID.randomUUID().toString();
        _name="";
        _email="";
        _pin="";
        _birthDate=null;
        _age=0;
        _genre="";
        _height=0.0;
        _weight=0.0;
        _imcResult=0.0;
        _active=0;
        _smoker="";
    }
    public User(String dbID){
        _id = dbID;
        _name="";
        _email="";
        _pin="";
        _birthDate=null;
        _age=0;
        _genre="";
        _height=0.0;
        _weight=0.0;
        _imcResult=0.0;
        _active=0;
        _smoker="";
    }

    @Override
    public String toString(){
        return getID()+ "\n " + getName()+ "\n " + getEmail()+ "\n " + getBirthDate()+"\n " +getAge() +"\n " + getGenre()+"\n " +
                getHeight()+"\n " +getWeight()+ "\n " + getIMC()+ "\n "+ getHealthRisk() + "\n " + isSmoker();
}

}
