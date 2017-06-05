// Klaudia C - 13.03.2016

import java.util.Scanner;

public class Source{
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args){
        int Z = scan.nextInt(); // liczba zestawow
        for(int z = 0; z < Z; z++){
            int W = scan.nextInt(); // liczba wierszy
            int K = scan.nextInt(); // liczba kolumn
            int tab[][] = new int[W][K];

            for(int w = 0; w < W; w++) // uzupelnianie wierszy
                for(int k = 0; k < K; k++) tab[w][k] = scan.nextInt();
            
            int maxS = tab[0][0]; // maksymalna suma
            int sizeS = 1; // dlugosc maksymalnej podtablicy
            int wBeg = 0, wEnd = 0; // dane wiersza wynikowego: [i..j]
            int kBeg = 0, kEnd = 0; // dane kolumny wynikowej: [k..l]

            for(int i = 0; i < W; i++){
                int maxTab[] = new int[K]; // maksymalna podtablica
                
                for(int j = i; j < W; j++){
                    int k = 0; // indeks pierwszej kolumny wyniku
                    int curMax = 0; // aktualna maksymalna suma
                    int curSize = 0; // aktualna dlugosc podtablicy
                    
                    for(int l = 0; l < K; l++){
                        if(tab[j][l] >= 0)
                            maxTab[l] += 3 * tab[j][l]; //uaktualnienie danych podtablicy
                        else maxTab[l] += 2 * tab[j][l];
                        curMax += maxTab[l];
                        curSize += j - i + 1;
                        if(maxS < curMax || (maxS == curMax && curSize < sizeS)){ // znaleziono nowa najwieksza sume
                            maxS = curMax; //uaktualnienie danych podtablicy wynikowej
                            sizeS = curSize;
                            wBeg = i;
                            wEnd = j;
                            kBeg = k;
                            kEnd = l;
                        }
                        if(curMax <= 0){ // podtablica osiagnela ujemna lub zerowa sume
                            curMax = 0; // zerowanie danych podtablicy
                            curSize = 0;
                            k = l + 1;
                        }
                    }
                }
            }
            if(maxS < 0)
                System.out.println("0");
            else{
                System.out.println("max_sum=" +maxS);
                System.out.println("[" +wBeg +".." +wEnd +", " +kBeg +".." +kEnd +"]");
            }
        }
    }
}
