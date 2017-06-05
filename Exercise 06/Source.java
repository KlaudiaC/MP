// Klaudia C - 24.04.2016

import java.util.Scanner;

public class Source{
    public static Scanner scan = new Scanner(System.in);

    static long numInversion(long a[], int first, int last){
        if(last - first <= 1)
            return 0; // tablica ma nie wiecej niz jeden element
        int middle = (last - first)/2 + first;
        long res = numInversion(a, first, middle) + numInversion(a, middle, last);
        long temp[] = new long[last - first];
        int i = first;
        int j = middle;
        int k = 0;

        while(i < middle && j < last){
            if(a[i] <= a[j])
                temp[k++] = a[i++]; // przepisanie elementu z lewej polowy
            else{ // znaleziono zaburzenie w porzadku elementow
                temp[k++] = a[j++]; // przepisanie elementu z prawej polowy
                res += middle - i; // zwiekszenie liczby inwersji
            }
        }
        while(i < middle)
            temp[k++] = a[i++]; // przepisanie pozostalych elementow

        i = first; // reset wartosci indeksow
        j = 0;
        while(j < k)
            a[i++] = temp[j++]; // przepisanie uporzadkowanej tablicy

        return res;
    }

    public static void main(String[] args){
        int Z = scan.nextInt(); // liczba zestawow
        for(int z = 0; z < Z; z++){
            int R = scan.nextInt(); // liczba elementow
            long a[] = new long[R];

            for(int r = 0; r < R; r++)
                a[r] = scan.nextInt();
            System.out.println(numInversion(a, 0, R));
        }
    }
}
