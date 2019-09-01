
public abstract   class Animal {
    private double Attack = 10;
    private double coficient = 0.2;


    public  void Attack(Animal obj, Human[] pull_targets) {

        int target = (int) (Math.random() * (pull_targets.length));
        Human targ = pull_targets[target];
        if (obj instanceof Cat) {
            Cat obj_1 = (Cat) obj;
            obj_1.Simple_Attack(targ);
        }
    }


    public void Simple_Attack(Human obj){
        if (obj.getStatus() == 1) {
            System.out.println("Афф-Афф");
            double R = obj.getHp() - this.Attack * obj.getDefence();
            obj.setHp(R);
            if (obj.getHp() <= 0.0D) {

                obj.setStatus(0);

                System.out.println(obj.getName() + " покинул эту квартиру!");
            }
        }
    }

    public  void Stopper(Human obj){
        int x = (int)(0.0D+Math.random()*11.0D);
        if (obj == Human.Killer){
            if (x<(this.coficient + 0.2)){
                obj.setWork(0);
                System.out.println("Feed me!");
            }
        }else if (obj == Human.Support){
            if (x<(this.coficient + 0.1)){
                obj.setWork(0);
                System.out.println("Feed me");
            }
        }else if (obj == Human.Tank) {
            if (x < (this.coficient + 0.3)) {
                obj.setWork(0);
                System.out.println("Feed me!");
            }
        }
    }


}