// Klaudia C - 18.05.2016

import java.util.Scanner;

public class Source{
    public static Scanner scan = new Scanner(System.in);
    public static int post[]; // tablica brakujacego porzadku
    public static int postIdx;
    public static int depth[]; // tablica glebokosci;
    public static int depthCount[]; // licznik elementow na kazdym poziomie

    public static void postorder(int pre[], int in[], int firstPre, int firstIn, int size, int d){
                                // tablice pozostalych porzadkow, ich indeksy poczatkowe, rozmiar, glebokosc
        if(size == 0)
            return;
        int root = pre[firstPre]; // korzen poddrzewa
        int i = firstIn; // poczatkowy indeks w inorder
        int j = firstIn + size - 1; // koncowy indeks w inorder
        while(in[i] != root && in[j] != root){ // szukanie korzenia w inorder
            i++;
            j--;
        }
        if(in[i] == root)
            j = i; // j - indeks korzenia
        depth[j] = d; // zapisanie glebokosci
        depthCount[d + 1]++; // powiekszenie wartosci odpowiedniej glebokosci
        postorder(pre, in, firstPre + 1, firstIn, j - firstIn, d + 1); // wywolanie dla lewego poddrzewa
        postorder(pre, in, firstPre + 1 + j - firstIn, j + 1, size - 1 - (j - firstIn), d + 1); // wywolanie dla prawego poddrzewa
        post[postIdx++] = root;
    }

    public static void main(String[] args){
        int Z = scan.nextInt(); // liczba zestawow
        for(int z = 1; z <= Z; z++){
            int N = scan.nextInt(); // wielkosc zestawu
            int pre[] = new int[N]; // tablice poszczegolnych porzadkow
            int in[] = new int[N];
            post = new int[N];
            postIdx = 0; // poczatkowy indeks tablicy postorder
            depth = new int[N]; // tablica glebokosci[0..N-1]
            depthCount = new int[N + 1]; // tablica licznikow glebokosci [1..N]
            for(int i = 0; i < N; i++){
                depth[i] = 0; // zerowanie tablicy glebokosci
                depthCount[i] = 0; // i jej licznikow
            }
            
            String order = scan.next();
            for(int i = 0; i < N; i++)
                pre[i] = scan.nextInt(); // wczytywanie pre- lub postorder
            scan.next(); // pominiecie slowa "INORDER"
            for(int i = 0; i < N; i++)
                in[i] = scan.nextInt(); // wczytywanie inorder

            if(order.compareTo("PREORDER") != 0){ // wczytano porzadek postorder
                int i = 0; // pomocnicze indeksu poczatku i konca tablicy drzewa
                int j = N - 1;
                while(i < j){
                    int temp = pre[i]; // tworzymy lustrzane odbicie postorder
                    pre[i] = pre[j]; // ktore jest jednoczesnie porzadkiem preorder odbitego drzewa
                    pre[j] = temp;
                    temp = in[i]; // odbijamy tez inorder
                    in[i] = in[j]; // aby obie tablice reprezentowaly to samo drzewo
                    in[j] = temp;
                    i++; // zblizenie do siebie indeksy poczatku i konca
                    j--;
                }
            }
            postorder(pre, in, 0, 0, N, 0); // poszukiwanie brakujacego porzadku

            if(order.compareTo("PREORDER") != 0){ // dokonano wczesniej odbicia drzewa
                int i = 0;
                int j = N - 1;
                while (i < j){
                    int temp = post[i]; // powrotne odwrocenie postorder
                    post[i] = post[j];
                    post[j] = temp;
                    temp = in[i]; // powrotne odwrocenie inorder
                    in[i] = in[j];
                    in[j] = temp;
                    temp = depth[i]; // odwrocenie tablicy glebokosci
                    depth[i] = depth[j];
                    depth[j] = temp;
                    i++;
                    j--;
                }
            }
            
            System.out.println("ZESTAW: " +z);
            if(order.compareTo("POSTORDER") == 0)
                System.out.println("PREORDER:");
            else
                System.out.println("POSTORDER:");
            for(int i = 0; i < N; i++)
                System.out.print(post[i] +" "); // wypisanie brakujacego porzadku
            System.out.println();
            System.out.println("LEVELORDER:");
            for(int i = 0; i < N - 1; i++)
                depthCount[i + 1] += depthCount[i]; // obliczanie sum czesciowych (poczatkowe pozycje wartosci)
            for(int i = 0; i < N; i++)
                post[depthCount[depth[i]]++] = in[i]; // porzadkowanie do wyniku
            for(int i = 0; i < N; i++)
                System.out.print(post[i] +" "); // wypisanie elementow wedlug poziomow
            System.out.println();
        }
    }
}
