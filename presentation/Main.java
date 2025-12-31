package presentation;

import java.util.*;

public class Main{

    //VARIABLES GLOBALS

    //Capacitat inicial d’habitacions: 30 estàndard, 20 suite, 10 deluxe
    public static int capEs = 30;
    public static int capSu = 20;
    public static int capDe = 10;
    
    public static Scanner sc = new Scanner(System.in);
    
    public static final float iva = 0.21f;
    
    //Hashamps de consulta
    public static HashMap<String, int[]> tipusHab = new HashMap<>(); //el valor es un array de dos integers, uno per al preu i l'altre per a la capacitat
    public static HashMap<String, Integer> serveis = new HashMap<>(); 

    //Hashmaps dinàmics
    public static HashMap<String, Integer> disponibilitat = new HashMap<>(); 
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<>();

    public static void main(String[] args){

        metodes.inicialitzarPreus(); //per a inicialitzar els preus per si la primera opció que s'escolleix no es reservar una habitació, si no ixiria 
        //una excepció

        int opcio;

        do{
            mostrarMenu();
            opcio = sc.nextInt();
            sc.nextLine(); //per a netejar el buffer
            gestionarOpcio(opcio);
        }while(opcio != 6);
        sc.close(); //tanquem el scaner una volta s'acabe el bucle de gestionar les opcions
    }

    static void gestionarOpcio(int opcio){
        switch(opcio){
                case 1:
                    System.out.println(""); //per a separar els mètodes 
                    metodes.reservarHabitacio();
                    break;
                case 2:
                    System.out.println("");
                    metodes.alliberarHabitacio();
                    break;
                case 3:
                    System.out.println("");
                    metodes.consultarDisponibilitat();
                    break;
                case 4:
                    System.out.println("");
                    metodes.obtindreReserva();
                    break;
                case 5:
                    System.out.println("");
                    metodes.obtindreReservaPerTipus();
                    break;
                case 6:
                    System.out.println("");
                    System.out.println("Eixint del sistema...");
                    System.out.println("Gràcies per utilitzar el gestor de reserves!");
                    break;
                default:
                    System.out.println("Opció invàlida.");
        }
    }

    public static void mostrarMenu(){
        System.out.println("");
        System.out.println("===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Consultar dades d'una reserva");
        System.out.println("5. Consultar reserves per tipus");
        System.out.println("6. Ixir");
        System.out.print("Seleccione una opció: ");
    }
}
