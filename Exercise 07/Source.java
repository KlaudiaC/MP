// Klaudia C - 30.04.2016

import java.util.Scanner;

public class Source{
    public static Scanner scan = new Scanner(System.in);

    public static int partition(int tab[], int first, int last){
        int left = first - 1;
        int right = last;
        int rand =  first + (int)(Math.random() * ((last - first) + 1));
        int p = tab[rand]; // wybrany element
        tab[rand] = tab[last];
        tab[last] = p;
        
        while(true){ // partition Hoare'a
            while(p > tab[++left]); // szukanie elementu wiekszego od p
            while(p < tab[--right] && right > first); // szukanie elementu mniejszego od p
            if(right > left){
                int temp = tab[left];
                tab[left] = tab[right];
                tab[right] = temp;
            }
            else
                break; // koniec dzielenia
        }
        int temp = tab[left];
        tab[left] = tab[last];
        tab[last] = temp;
        
        return left; // wyznaczony pivot
    }
    
    public static void quickSort(int tab[], int first, int last){
        int pom = 0;
        while(first < last || pom > 0){
            if(last > first){
                int p = partition(tab, first, last);  // trzeba ustalic podzial
                tab[last] = -tab[last];
                last = p - 1;
                pom++;
            }
            else{
                last = first = last + 2;
                while(last < tab.length){
                    if(tab[last] >= 0)
                        last++;
                    else{
                        tab[last] = -tab[last];
                        break;
                    }
                }
                pom--;
            }
        }
    }

    public static void main(String[] args){
        int Z = scan.nextInt(); // liczba zestawow
        for(int z = 0; z < Z; z++){
            int N = scan.nextInt(); // liczba elementow
            int a[] = new int[N];
            for(int n = 0; n < N; n++) a[n] = scan.nextInt();
            quickSort(a, 0, N - 1);
            StringBuilder res = new StringBuilder();
            for(int n = 0; n < N; n++){
                if(a[n] < 0)
                    res.append(-a[n]);
                else
                    res.append(a[n]);
                res.append(" ");
            }
            System.out.println(res.toString());
        }
    }
}
