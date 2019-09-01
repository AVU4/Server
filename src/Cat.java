

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public  class Cat extends Animal implements Comparable<Cat>, Serializable {
    private double Attack = 10;
    private String name;
    private Date date = new Date();
    private int age;
    private Position position;
    Stomach stomach;

    public Cat(String name, int age, int volume, int x, int y){
        this.age = age;
        this.name = name;
        stomach = new Stomach(volume);
        position = new Position(x,y);
    }

    public Cat(String name, int age, int volume, int x, int y, Date date){
        this(name, age, volume, x, y);
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public Date getDate() {
        return date;
    }

    public int getVolume(){
        return stomach.volume;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }else if (this.getClass() != obj.getClass()){
            return false;
        }else if (obj == null){
            return false;
        }else{
            Cat otherObj = (Cat) obj;
            if ((otherObj.getAge() == this.getAge()) && (otherObj.getName().equals(this.getName()))){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public int compareTo(Cat o) {
        if (this.getAge() > o.getAge()){
            return -1;
        }else if (this.getAge() == o.getAge()){
            return 0;
        }else {
            return 1;
        }
    }

    public void Simple_Attack(Human obj) {
        stomach.volume -= 30;
        stomach.testHunger();
        if (stomach.getHunger() == true) {
            if (obj != Human.Tank) {
                if (obj.getStatus() == 1) {
                    System.out.println("ШШШ-ШШШ");
                    double R = obj.getHp() - this.Attack * obj.getDefence();
                    obj.setHp(R);
                    if (obj.getHp() <= 0.0D) {
                        obj.setStatus(0);
                        System.out.println(obj.getName() + " покинул эту квартиру!");
                    }
                }
            } else {
                System.out.println("Мур-Мур");
            }
        }else{
            System.out.println("Мяу, еееесть");
            stomach.Eat();
        }
    }

    public class Stomach implements Serializable{

        private int volume;
        private boolean hunger;

        public Stomach( int volume){
            this.volume = volume;
            this.hunger = true;
        }

        public void testHunger(){
            if (this.volume < 50){
                this.hunger = false;
            }else{
                this.hunger = true;
            }
        }

        public void Eat(){
            this.hunger = true;
            this.volume = 100;
        }

        public Boolean getHunger(){
            return hunger;
        }

    }

    public String getName() {
        return name;
    }

    void A(){
        System.out.println("A");
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getAge() + " " + this.getVolume() + " " + this.date + "(" + this.position.getX()+ ";"  + this.position.getY()+ ")";
    }
}

