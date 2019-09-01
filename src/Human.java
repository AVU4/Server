
public enum Human  {
    Killer, Support, Tank;

    private String name;
    private int status;
    private int hilling;
    private double Hp;
    private double Max_Hp;
    private double Attack;
    private double Defence;
    private int Work;
    private Human.Weapon weapon = null;



    public static class Weapon{

        private double damage;
        private String name;
        private String sound;
        private Boolean status = false;

        public Weapon(String name, double damage, String sound){
            this.name = name;
            this.damage = damage;
            this.sound = sound;
        }

        @Override
        public boolean equals(Object otherobject){
            if (this == otherobject){return true;} // проверка, одинаковые ли ссылки
            else if (otherobject == null){return  false;}
            else if (this.getClass() != otherobject.getClass()){return false;}
            else{
                Weapon otherobj = (Weapon) otherobject;
                if ((otherobj.damage == this.damage) && (otherobj.name.equals(this.name))){return true;}
                else {return false;}
            }
        }

        @Override
        public String toString(){
            return this.getClass()+ " " + name + " " + Double.toString(damage);
        }

        @Override
        public int hashCode(){
            return name.hashCode() * (int)this.damage;
        }

        public void setStatus(Boolean status){
            this.status = status;
        }

        public Boolean getStatus(){
            return status;
        }

        public String Sound() {
            return sound;
        }
    }



    public String getName() {
        return name;
    }
    public double getAttack(){return Attack;}
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status)  {
        this.status = status;

    }
    public void setWork(int x) {
        this.Work = 1;
    }
    public double getMax_Hp(){return  this.Max_Hp;}
    public int getWork() {
        return this.Work;
    }
    public double getDefence() {
        return this.Defence;
    }
    public double getHp() {
        return this.Hp;
    }
    public void setHp(double Hp){
        this.Hp = Hp;

    }
    public void AddHp(double Hp){this.Hp += Hp; this.Max_Hp += Hp;}
    public  void AddAttack(double Attack) {
        this.Attack += Attack;}
    public void bufAttack () {
        class Conditions {
            String condition;

            public String getCondition(Human obj) {
                return condition;
            }

            public void setCondition(Human obj) {
                if (obj.getHp() >= obj.getMax_Hp() * 0.9) {
                    this.condition = "Dominator";
                } else if (obj.getMax_Hp() >= obj.getMax_Hp() * 0.5) {
                    this.condition = "Normal";
                } else {
                    this.condition = "Kill me, please";
                }
            }
        }



        Conditions conditions = new Conditions();
        conditions.setCondition(this);

        if (conditions.getCondition(this) == "Dominator") {
            this.Attack += Attack*0.2;
            System.out.println("Я тебя уничтожу");

        }else if (conditions.getCondition(this) == "Normal"){
            System.out.println("Надо бороться");
        }else {
            this.Attack -= Attack*0.1;
        }
    }
    public void SubDefence(double Defence){this.Defence += Defence;}


    public void setStats(String name, Human obj){

        this.name = name;

        this.Work = 1;
        if (obj == Killer){
            this.status = 1;
            this.Attack = 50.0D;
            this.hilling = 3;
            this.Defence = 0.5D;
            this.Hp = 80.0D;
            this.Max_Hp = 80.0D;
        }else if (obj == Support){
            this.status = 1;
            this.Attack = 20.0D;
            this.hilling = 5;
            this.Defence = 0.8D;
            this.Hp = 80.0D;
            this.Max_Hp = 80.0D;
        }else if (obj == Tank){
            this.status = 1;
            this.Attack = 40.0D;
            this.hilling = 5;
            this.Defence = 0.2D;
            this.Hp = 150.0D;
            this.Max_Hp = 150.0D;
        }
    }

    public  void Attack(Human obj, Human[] pull_targets) {

        int target = (int) (Math.random() * (pull_targets.length));
        while (obj.equals(pull_targets[target])) {
            target = (int) (Math.random() * (pull_targets.length));
        }
        Human targ = pull_targets[target];
        if (obj.weapon == null) {
            obj.Simple_Attack(targ);
            obj.bufAttack();
        }else{
            obj.Simple_Attack_by_weapon(targ);
        }
    }

    public void Simple_Attack_by_weapon(Human obj){
        if (obj.getStatus() == 1 && this.getWork() == 1) {
            System.out.println(this.weapon.Sound());
            double R = obj.getHp() - this.weapon.damage*obj.getDefence();
            obj.setHp(R);
            if (obj.getHp() <= 0.0D) {

                obj.setStatus(0);
                System.out.println(obj.getName() + " покинул эту квартиру!");

            }
        }
    }


    public void Simple_Attack(Human obj) {
        if (obj.getStatus() == 1 && this.getWork() == 1) {
            System.out.println("Пау-Пау");
            double R = obj.getHp() - this.Attack * obj.getDefence();
            obj.setHp(R);
            if (obj.getHp() <= 0.0D) {
                obj.setStatus(0);
                System.out.println(obj.getName() + " покинул эту квартиру!");


            }
        }

    }

    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }



    public Weapon getWeapon(){return this.weapon;}

}
