// Klaudia C - 09.04.2016

import java.util.Scanner;

public class Source{
    public static Scanner scan = new Scanner(System.in);
    
    static int find(long arr[], long key){
        int l = 0, r = arr.length, c; // lewa i prawa strona przedzialu, element srodkowy
        int res = 0; // wynik
        
        while(l < r){ // poszukiwanie ostatniego wystapienia podanej liczby
             c = l + (r - l) / 2;
             if(arr[c] > key) r = c;
             else l = c + 1;
        }
        res = l;
        
        l = 0; // reset wartosci zmiennych
        r = arr.length;
        while(l < r){ // poszukiwanie pierwszego wystapienia podanej liczby
            c = l + (r - l) / 2;
            if(arr[c] >= key) r = c;
            else l = c + 1;
        }
        res -= l;

        return res;
    }
    
    public static void main(String[] args){
        int Z = scan.nextInt(); // liczba zestawow
        for(int z = 0; z < Z; z++){
            int n = scan.nextInt();
            long tab[] = new long[n]; // tworzenie i uzupelnienie tablicy
            for(int i = 0; i < n; i++)
                tab[i] = scan.nextLong();
            int S = scan.nextInt(); // liczba sprawdzen
            for(int s = 0; s < S; s++){
                long x = scan.nextLong(); // sprawdzana wartosc
                System.out.print(find(tab, x) +" ");
            }
            System.out.println();
        }
    }
}
