

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class CatVector {
    Vector<Cat> Home = new Vector<>();
    Date dataInizialization;
    CatComparator comparator = new CatComparator();
    static String  Path ;

    public CatVector(){
        dataInizialization = new Date();
    }

    /**
     * This method adds an object of class Cat to the collection of type Vector. After added the collection is sorted.
     * @param obj
     *
     */
    public void add(Cat obj) {
        if (Home.add(obj)){
            System.out.println("Элемент успешно добавлен");
        }else{
            System.out.println("Элемент уже есть в коллекции");
        }
    }

    /**
     * This method displays all elements of the collection in a string view.
     */

    public void show(){
        for (Cat elem : Home){
            System.out.println(elem.toString());
        }
    }

    /**
     * This method removes an item from the collection if the item is there, or reports that the item is not in the collection.
     * @param obj
     */

    public void remove(Cat obj){
        if (Home.remove(obj)){
            System.out.println("Элемент успешно удалён");
        }else {
            System.out.println("Данного элемента нет в коллекции");
        }


    }

    /**
     * This method provides information about the collection.
     */

    public void info(){
        System.out.println("Тип коллекции - Vector");
        System.out.println("Дата инициализации - " + dataInizialization.toString());
        System.out.println("Количество элементов - " + Home.size());
    }

    /**
     * Using this method, the user provides the program with the address of the file that will be read and to which new data will be written.
     * @param Path
     * @throws FileNotFoundException
     */

//    public static void Import(String Path) throws FileNotFoundException  {
//        try{
//            File file = new File(Path);
//            Scanner scanner = new Scanner(file);
//            while (scanner.hasNext()) {
//                String obj = scanner.nextLine();
//                String[] massive = obj.split(";");
//                if (!massive[0].equals("Name") && (!massive[1].equals("Age") && (!massive[2].equals("Volume")))) {
//                    Home.add(new Cat(massive[0], Integer.parseInt(massive[1]), Integer.parseInt(massive[2])));
//                }
//            }
//
//            if (CatVector.Home.isEmpty() == false) {
//                System.out.println("Запись в коллекцию прошла успешно");
//            } else {
//                System.out.println("Данные из файла в коллекцию не записаны");
//
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Проверьте правильно ли записаны данные в файле");
//            System.out.println("Пример правильной записи, Barsik;5;1");
//            System.exit(1);
//        }
//
//    }

    /**
     * This method removes all items in the collection that are older (by age) than the object passed to the method.
     * @param obj
     */

    public void remove_greater(Cat obj){
        Cat[] indexs = new Cat[Home.size()];
        Home.copyInto(indexs);
        int i = 0;
        for (Cat elem : indexs){

            if (elem.equals(obj) == false){
                if (elem.getAge() > obj.getAge()){
                    Home.remove(elem);
                }
            }
        }
        System.out.println("Удаление прошло успешно");

    }

    /**
     * This method saves collection in file.
     */
    public  void save(){
        try {
            FileWriter writ = new FileWriter(System.getenv("Path"));
            writ.write("Name;Age;Volume");
            for (Cat elem : Home) {
                writ.write("\n");
                writ.write(elem.getName() + ";" + elem.getAge() + ";" + elem.getVolume());
            }
            writ.close();
            System.out.println("Коллекция сохранена");

        }catch(IOException e){
            System.out.println("Проверьте файл, возможно ограничен доступ");
        }

    }

    public static String getPath() {
        return Path;
    }

    /**
     * This command provides information about the commands.
     */

    public static void help(){
        System.out.println("Команда add позволяет пользователю добавить элемент в коллекцию.");
        System.out.println("Формат ввода : add{ name : String, age : int, Stomach{ volume : int}}");
        System.out.println("Команда remove позволяет пользователю удалить элемент из коллекции.");
        System.out.println("Формат ввода : remove{ name : String, age : int, Stomach{ volume : int}}");
        System.out.println("Команда remove_greater позволяет пользователю удалить элементы, которые старше данного, из коллекции.");
        System.out.println("Формат ввода : remove_greater{ name : String, age : int, Stomach{ volume : int}}");
        System.out.println("Команда info предоставляет пользователю информацию о коллекции.");
        System.out.println("Формат ввода : info");
        System.out.println("Команда show предоставляет пользователю информацию о содержимом коллекции в строковом представлении.");
        System.out.println("Формат ввода : show");
        System.out.println("Команда save сохраняет содержимое коллекции в файл.");
        System.out.println("Формат ввода : save");
        System.out.println("Команда import передаёт программе адрес файла");
        System.out.println("Формат ввода : import{String}");


    }



}


