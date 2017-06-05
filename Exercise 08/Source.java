// Klaudia C - 11.05.2016

import java.util.Scanner;

public class Source{
    public static Scanner scan = new Scanner(System.in);

    public static void insertionSort(int tab[], int first, int last){
        for(int i = first; i <= last; i++){
            int x = tab[i];
            int j = i - 1;
            while(j >= 0 && tab[j] > x){
                tab[j + 1] = tab[j];
                j--;
            }
            tab[j + 1] = x;
        }
    }

    public static int kthElement(int[] tab, int first, int last, int k){
        int size = last - first + 1; // rozmiar tablicy

        if(size < 50){ // tablica jest na tyle mala, ze mozna ja po prostu posortowac i wziac k-ty element
            insertionSort(tab, first, last);
            return tab[k - 1];
        }
        
        int ilePrzedz = size/5; // liczba przedzialow piecioelementowych
        int mediany[] = new int[ilePrzedz + 1]; // tablica median podzbiorow

        for(int i = 0; i < ilePrzedz; i++){ // podzial tablicy na 5-elementowe podzbiory
            insertionSort(tab, first + 5 * i, first + (5 * (i + 1) - 1)); // sortowanie podzbioru
            mediany[i] = tab[first + 5 * i + 2]; // zapisanie mediany podzbioru w tablicy median
        }
        if(size%5 != 0){ // pozostal jeszcze jeden podzbior, majacy mniej niz 5 elementow
            insertionSort(tab, first + 5 * ilePrzedz, last); // trzeba powtorzyc powyzsze operacje
            mediany[ilePrzedz] = tab[first + 5 * ilePrzedz + (last - (first + 5 * ilePrzedz) + 1) / 2];
            ilePrzedz++; // i zwiekszyc liczbe przedzialow
        }

        int mediana = kthElement(mediany, 0, ilePrzedz - 1, ilePrzedz/2); // wybranie mediany median
        int S1[] = new int[last - first];  // podzbior z elementami mniejszymi od mediany median
        int S2[] = new int[last - first]; // elementy rowne
        int S3[] = new int[last - first]; // elementy wieksze
        int s1, s2, s3; // ilosc elementow w poszczegolnych podzbiorach
        s1 = s2 = s3 = 0;

        for(int i = 0; i <= last; i++){ // rozlokowanie elementow w odpowiednich zbiorach
            if(tab[i] < mediana)
                S1[s1++] = tab[i]; // elementy mniejsze od mediany median
            else if(tab[i] == mediana)
                S2[s2++] = tab[i]; // elementy rowne
            else
                S3[s3++] = tab[i]; // elementy wieksze
        }

        if(k <= s1)
            return kthElement(S1, 0, s1 - 1, k); // element k znajduje sie w pierwszym zbiorze
        else if(k <= s1 + s2)
            return mediana; // element k zostal znaleziony
        else
            return kthElement(S3, 0, s3 - 1, k - s1 - s2); // element k znajduje sie w ostatnim zbiorze
    }

    public static void main(String[] args){
        int Z = scan.nextInt();
        for(int z = 0; z < Z; z++){
            int R = scan.nextInt();
            int a[] = new int[R];

            for(int r = 0; r < R; r++)
                a[r] = scan.nextInt();
            int I = scan.nextInt();
            for(int i = 0; i < I; i++){
                int k = scan.nextInt(); // szukany element
                System.out.println(k + " " +kthElement(a, 0, R - 1, k));
            }
        }
    }
}
