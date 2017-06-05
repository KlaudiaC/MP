// Klaudia C - 13.04.2016

import java.util.Scanner;

public class Source{
    public static Scanner scan = new Scanner(System.in);

    static int priorytet(char c){ // lista priorytetow
        if('a' <= c && c <= 'z') return 11;
        if(c == '~') return 10;
        if(c == '^') return 9;
        if(c == '*' || c == '/' || c == '%') return 8;
        if(c == '+' || c == '-') return 7;
        if(c == '<' || c == '>') return 6;
        if(c == '!') return 5;
        if(c == '&') return 4;
        if(c == '|') return 3;
        if(c == '=') return 2;
        if(c == ')') return 1;
        if(c == '(') return 0;
        return -1; // znak spoza listy
    }

    public static void main(String[] args){
        int Z = scan.nextInt();
        for(int z = 0; z < Z;){
            String line = scan.nextLine();
            if(line.startsWith("<INF>")){ // zamiana INF -> ONP
                z++;
                boolean error = false;
                char[] wej = new char[256]; // stos
                int top = 1; // czubek stosu
                wej[0] = '(';
                int last = 0; // priorytet dna stosu
                line += ")";

                String wyj = "<ONP>"; // wynik
                for(int i = 5; i < line.length(); i++){
                    char c = line.charAt(i); // znak
                    int p = priorytet(c); // priorytet znaku                
                    if(p < 0) continue;
                    if((p == 11 || p == 10 || p == 5 || p == 0) && !(last == 0 || (2 <= last && last <= 10))){
                        error = true;
                        break;
                    }
                    if((((2 <= p && p <= 4) || (6 <= p && p <= 9)) || p == 1) && !(last == 1 || last == 11)){
                        error = true;
                        break;
                    }
                    if((p == 5 || p == 10) && (last == 5 || last == 10)){
                        error = true;
                        break;
                    }

                    last = p;
                    if(p == 11){ // operand
                        wyj += String.valueOf(c); // przepisanie na wynik
                        continue;
                    }
                    if(p == 0){ // (
                        wej[top++] = c; // dodanie na stos
                        continue;
                    }
                    if(p == 1){ // )
                        while((--top >= 0) && wej[top] != '(') // zdjecie ze stosu
                            wyj += String.valueOf(wej[top]);
                        if(top < 0){ // stos sie skonczyl
                            error = true;
                            break;
                        }
                        continue;
                    }
                    
                    while(top > 0 && priorytet(wej[top-1]) >= p) // zdejmowanie elementow o tym samym lub wyzszym priorytecie
                        wyj += String.valueOf(wej[--top]);
                    wej[top++] = c;
                }
                if(error || top != 0 || priorytet(wyj.charAt(5)) != 11) wyj = "<ONP>error";
                System.out.println(wyj);
            }
            if(line.startsWith("<ONP>")){ // zamiana ONP -> INF
                z++;
                boolean error = false;
                int[] P = new int[256]; // stos priorytetow
                String[] O = new String[256]; // stos operandow
                int top = 0;
                String temp;  // zmienna pomocnicza do uzupelniania wyniku
                String res = "<INF>"; // wynik

                for(int i = 5; i < line.length(); i++){
                    char c = line.charAt(i);
                    int p = priorytet(c);
                    if(p <= 1) continue;
                    if(p == 11){ // operand
                        P[top] = 11;
                        O[top] = String.valueOf(c);
                        top++;
                        continue;
                    }
                    if(top == 0){ // skonczyl sie stos
                        error = true;
                        break;
                    }
                    top--;
                    if(P[top] <= p) temp = String.valueOf(c) + "(" + O[top] +")";
                    else temp = String.valueOf(c) + O[top];
                    if(p != 10 && p != 5){ // sprawdzenie operatorow binarnych
                        if(top == 0){
                            error = true;
                            break;
                        }
                        top--;
                        if(P[top] < p) temp = "(" + O[top] + ")"+ temp;
                        else temp = O[top] + temp;
                    }
                    P[top] = p;
                    O[top] = temp;
                    top++;
                }
                if(error || top != 1) res += "error";
                else res += O[0];
                System.out.println(res);
            }
        }
    }
}
