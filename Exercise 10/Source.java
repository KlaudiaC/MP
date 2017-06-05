// Klaudia C - 03.06.2016

import java.util.Scanner;

public class Source{
    public static Scanner scan = new Scanner(System.in);
    static int heap[]; // kopiec
    static int size; // rozmiar tablicy kopca
    static int ktoryCiag[]; // tablica numerow ciagow (wierszy ponizszej tablicy) [N]
    static int ciagi[][]; // tablica przechowujaca ciagi [N][]

    static void downHeap(int v){
        int temp = heap[v];
        int n = size;
        while(v < n/2){
            int left = 2 * v + 1; // lewy nastepnik
            if(left + 1 < n && ciagi[heap[left]][ktoryCiag[heap[left]]] > ciagi[heap[left + 1]][ktoryCiag[heap[left + 1]]])
                left++; //wybor wiekszego nastepnika
            if(ciagi[temp][ktoryCiag[temp]] <= ciagi[heap[left]][ktoryCiag[heap[left]]]) // sprawdzenie warunku kopca
                break; // warunkek kopca jest w porzadku
            heap[v] = heap[left]; // warunek kopca zostal zaburzony
            v = left; // trzeba przesunac biezacy element do gory
        }
        heap[v] = temp; // wstawienie v na wlasciwe miejce
    }

    public static void main(String[] args){
        int Z = scan.nextInt(); // liczba zestawow
        for(int z = 0; z < Z; z++){
            int N = scan.nextInt(); // liczba ciagow
            ciagi = new int[N][];
            heap = new int[N];
            size = N;
            ktoryCiag = new int[N];

            for(int n = 0; n < N; n++){ // budowanie ciagow
                int w = scan.nextInt(); // dlugosc ciagu
                ciagi[n] = new int[w]; // czyli n-tego wiersza tablicy ciagow
                heap[n] = n;
                ktoryCiag[n] = 0; // kazdy ciag otrzymuje na poczatek zerowa liczbe elementow
            }

            for(int n = 0; n < N; n++){
                int w = ciagi[n].length; // dlugosc aktualnie tworzonego ciagu
                for(int i = 0; i < w; i++)
                    ciagi[n][i] = scan.nextInt(); // elementy tego ciagu
            }

            for(int n = N/2 - 1; n >= 0; n--)
                downHeap(n); // przesiewanie

            StringBuilder res = new StringBuilder();
            while(size > 0){
                res.append(ciagi[heap[0]][ktoryCiag[heap[0]]]); // bierzemy pierwszy element
                res.append(' ');
                if(++ktoryCiag[heap[0]] == ciagi[heap[0]].length)
                    heap[0] = heap[--size]; // podmieniamy
                downHeap(0); // i przesiewamy
            }
            System.out.println(res.toString());
        }
    }
}