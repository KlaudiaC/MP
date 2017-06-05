// Klaudia C - 19.04.2016

import java.util.Scanner;

class Wagonik{
    String nazwa;
    Wagonik prev, next;
}

class Train{
    String nazwa;
    Train next;
    Wagonik first, last;
}

public class Source{
    public static Scanner scan = new Scanner(System.in);
    static Train Trains = null;

    public static Train findTrain(String nazwa){
        Train t = Trains;
        while(t != null && t.nazwa.compareTo(nazwa) != 0)
            t = t.next;
        return t;
    }

    public static Train findPrevTrain(String nazwa){
        Train t = Trains;
        Train tprev = null;
        while(t != null && t.nazwa.compareTo(nazwa) != 0){
            tprev = t;
            t = t.next;
        }
        return tprev;
    }

    public static void newTrain(String nazwaT, String nazwaW){
        if(findTrain(nazwaT) != null)
            System.out.println("Train " +nazwaT +" already exists");
        else{
            Wagonik w = new Wagonik();
            w.nazwa = nazwaW;
            Wagonik head = new Wagonik();
            head.next = head.prev = w;
            w.prev = w.next = head;

            Train t = new Train();
            t.nazwa = nazwaT;
            t.first = head;
            t.last = w;
            t.next = Trains;
            Trains = t;
        }
    }

    public static void insertFirst(String nazwaT, String nazwaW){
        Train t = findTrain(nazwaT);
        if(t == null)
            System.out.println("Train " +nazwaT +" does not exist");
        else{
            Wagonik w = new Wagonik();
            w.nazwa = nazwaW;
            w.prev = t.first;
            w.next = t.first.next;
            
            if(t.first.next.prev == t.first)
                t.first.next.prev = w;
            else
                t.first.next.next = w;
            t.first.next = w;
        }
    }

    public static void insertLast(String nazwaT, String nazwaW){
        Train t = findTrain(nazwaT);
        if(t == null)
            System.out.println("Train " +nazwaT +" does not exist");
        else{
            Wagonik w = new Wagonik();
            w.nazwa = nazwaW;
            w.prev = t.first.prev;
            w.next = t.first;
            
            if(t.last.prev == t.first)
                t.last.prev = w;
            else
                t.last.next = w;
            t.first.prev = t.last = w;
        }
    }

    public static void displayTrain(String nazwaT){
        Train t = findTrain(nazwaT);
        if(t == null)
            System.out.println("Train " +nazwaT +" does not exist");
        else{
            StringBuilder res = new StringBuilder();
            res.append(t.nazwa);
            res.append(':');

            Wagonik prev = t.first;
            Wagonik next = null;
            Wagonik cur = t.first.next;
            while(cur != t.first){
                res.append(' ');
                res.append(cur.nazwa);
                if(cur.next != prev)
                    next = cur.next;
                else
                    next = cur.prev;

                prev = cur;
                cur = next;
            }
            System.out.println(res.toString());
        }
    }

    public static void trainsList(){
        System.out.print("Trains:");
        for(Train t = Trains; t != null; t = t.next)
            System.out.print(" " + t.nazwa);
        System.out.println();
    }

    public static void reverse(String nazwaT){
        Train t = findTrain(nazwaT);
        if(t == null)
            System.out.println("Train " +nazwaT +" does not exist");
        else{
            Wagonik temp = t.first.next;
            t.first.next = t.last;
            t.first.prev = t.last = temp;
        }
    }
    
    public static void union(String nazwaT1, String nazwaT2){
        Train t1 = findTrain(nazwaT1);
        Train tprev = findPrevTrain(nazwaT2);
        Train t2;
        if(tprev == null)
            t2 = Trains;
        else
            t2 = tprev.next;
        if(t1 == null)
            System.out.println("Train " +nazwaT1 +" does not exist");
        else if(t2 == null)
            System.out.println("Train " +nazwaT2 +" does not exist");
        else{
            if(tprev == null)
                Trains = t2.next;
            else
                tprev.next = t2.next;
            
            if(t1.last.next == t1.first)
                t1.last.next = t2.first.next;
            else
                t1.last.prev = t2.first.next;
            
            if(t2.first.next.next == t2.first)
                t2.first.next.next = t1.last;
            else
                t2.first.next.prev = t1.last;
            
            if(t2.last.next == t2.first)
                t2.last.next = t1.first;
            else
                t2.last.prev = t1.first;
            t1.first.prev = t1.last = t2.last;
        }
    }

    public static void delFirst(String nazwaT1, String nazwaT2){
        Train tprev = findPrevTrain(nazwaT1);
        Train t1;
        
        if(tprev == null) t1 = Trains;
        else t1 = tprev.next;
        if(t1 == null)
            System.out.println("Train " +nazwaT1 +" does not exist");
        else{
            if(t1.first.next == t1.last){
                t1.nazwa = nazwaT2;
                if(tprev != null)
                    tprev.next = t1.next;
                else
                    Trains = t1.next;
                
                if(findTrain(nazwaT2) != null)
                    System.out.println("Train " +nazwaT2 +" already exists");
                else{
                    t1.next = Trains;
                    Trains = t1;
                }
            }
            else{
                Wagonik w1 = t1.first.next;
                Wagonik w2;
                if(w1.next != t1.first)
                    w2 = w1.next;
                else
                    w2 = w1.prev;
                if(w2.next == w1)
                    w2.next = t1.first;
                else
                    w2.prev = t1.first;
                t1.first.next = w2;
                newTrain(nazwaT2, w1.nazwa);
            }
        }
    }

    public static void delLast(String nazwaT1, String nazwaT2){
        Train tprev = findPrevTrain(nazwaT1);
        Train t1;
        if(tprev == null)
            t1 = Trains;
        else
            t1 = tprev.next;
        if(t1 == null)
            System.out.println("Train " +nazwaT1 +" does not exist");
        else{
            if(t1.first.next == t1.last){
                t1.nazwa = nazwaT2;
                if(tprev != null)
                    tprev.next = t1.next;
                else
                    Trains = t1.next;
                
                if(findTrain(nazwaT2) != null)
                    System.out.println("Train " +nazwaT2 +" already exists");
                else{
                    t1.next = Trains;
                    Trains = t1;
                }
            }
            else{
                Wagonik w1 = t1.last;
                Wagonik w2;
                if(w1.next != t1.first)
                    w2 = w1.next;
                else
                    w2 = w1.prev;
                
                if(w2.next == w1)
                    w2.next = t1.first;
                else
                    w2.prev = t1.first;
                t1.last = w2;
                
                t1.first.prev = w2;
                newTrain(nazwaT2, w1.nazwa);
            }
        }    
    }
    
    public static void main(String[] args){
        int Z = scan.nextInt();
        for(int z = 0; z < Z; z++){ // liczba zestawow
            Trains = null;
            int N = scan.nextInt(); // liczba polecen
            for(int n = 0; n < N; n++){
                String polecenie = scan.next();
                if(polecenie.compareTo("New") == 0){
                    String T1 = scan.next();
                    String W = scan.next();
                    newTrain(T1, W);
                }

                if(polecenie.compareTo("InsertFirst") == 0){
                    String T1 = scan.next();
                    String W = scan.next();
                    insertFirst(T1, W);
                }

                if(polecenie.compareTo("InsertLast") == 0){
                    String T1 = scan.next();
                    String W = scan.next();
                    insertLast(T1, W);
                }

                if(polecenie.compareTo("Display") == 0){
                    String T1 = scan.next();
                    displayTrain(T1);
                }

                if(polecenie.compareTo("Trains") == 0)
                    trainsList();

                if(polecenie.compareTo("Reverse") == 0){
                    String T1 = scan.next();
                    reverse(T1);
                }

                if(polecenie.compareTo("Union") == 0){
                    String T1 = scan.next();
                    String T2 = scan.next();
                    union(T1, T2);
                }

                if(polecenie.compareTo("DelFirst") == 0){
                    String T1 = scan.next();
                    String T2 = scan.next();
                    delFirst(T1, T2);
                }

                if(polecenie.compareTo("DelLast") == 0){
                    String T1 = scan.next();
                    String T2 = scan.next();
                    delLast(T1, T2);
                }
            }
        }
    }
}
