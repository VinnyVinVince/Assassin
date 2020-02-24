import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        
        list.add("Sam");
        list.add("Name");
        list.add("Vinny");
        list.add("Drew Queue");
        
        System.out.println(list);
        
        AssassinManager manager = new AssassinManager(list);
        manager.printKillRing();
        manager.printGraveyard();
        
        System.out.println("i am legend");

    }

}