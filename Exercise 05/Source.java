// Klaudia C - 22.04.2016

import java.util.Scanner;

public class Source{
    public static Scanner scan = new Scanner(System.in);
    static String res; // wynik

    public static boolean knapsack(int weight, int[] things, int n){ // waga plecaka, tablica rzeczy, ilosc rzeczy
        if(n == things.length)
            return false; // przejrzano wszystkie rzeczy
        if(things[n] < weight){ // aktualnie sprawdzana rzecz wazy mniej od wagi docelowej
            if(knapsack(weight - things[n], things, n + 1)){ // zmniejszamy wage o te rzecz w kolejnym wywolaniu
                res = things[n] +" " +res; // element pasuje
                return true;
            }
            else
                return knapsack(weight, things, n + 1); // trzeba zaczac poszukiwania od kolejnego elementu
        }
        else if(things[n] > weight) // aktualny element jest za duzy, wiec przechodzimy do kolejnej rzeczy
          return knapsack(weight, things, n + 1);
        else{ // element wazy w sam raz
            res = things[n] +" " +res;
            return true;
        }
    }

    public static void main(String[] args){
        int Z = scan.nextInt(); // ilosc zestawow danych
        for(int z = 0; z < Z; z++){
            res = ""; // wyczyszczenie wyniku
            int v = scan.nextInt(); // pojemnosc plecaka
            int N = scan.nextInt(); // liczba elementow, ktore mozna umiescic w plecaku
            
            int a[] = new int [N];
            for(int n = 0; n < N; n++)
                a[n] = scan.nextInt();
            if(knapsack(v, a, 0))
                System.out.println(v +" = " +res);
            else
                System.out.println("BRAK");
        }
    }
}
