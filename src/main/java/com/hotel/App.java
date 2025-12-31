package com.hotel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
       switch(opcio){
                case 1:
                    System.out.println(""); //per a separar els mètodes 
                    reservarHabitacio();
                    break;
                case 2:
                    System.out.println("");
                    alliberarHabitacio();
                    break;
                case 3:
                    System.out.println("");
                    consultarDisponibilitat();
                    break;
                case 4:
                    System.out.println("");
                    obtindreReservaPerTipus();
                    break;
                case 5:
                    System.out.println("");
                    obtindreReserva();
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
    

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        String hab = seleccionarTipusHabitacioDisponible();

        if (hab == null) {
            System.out.println("No hi ha habitacions disponibles del tipus seleccionat.");
            return; 
        }

        ArrayList<String> serveis = seleccionarServeis();
        float preu = calcularPreuTotal(hab, serveis);
        int codi = generarCodiReserva();

        ArrayList<String> info = new ArrayList<>(); //es crea un altre arrayList per a juntar els serveis amb el tipus d'habitació i el preu
        //afegim cada dada seguint l'ordre establert
        info.add(hab);
        info.add(String.valueOf(preu));
        //utilitzem addAll per a afegir els elements de l'altre arrayList
        info.addAll(serveis);

        reserves.put(codi, info);

        //guardem en una variable la capacitat actual de l'habitació per a restarli uno per a actualizarla una volta fet la reserva
        int capacitat = disponibilitatHabitacions.get(hab);
        disponibilitatHabitacions.put(hab, capacitat - 1);
        
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        while (true) {
            int hab = llegirEnter("Seleccione tipus d'habitació: ");
            switch (hab) {
                case 1:
                    return TIPUS_ESTANDARD;//ho retornem amb un return i així s'ix del switch
                case 2:
                    return TIPUS_SUITE;
                case 3:
                    return TIPUS_DELUXE;
                default:
                    System.out.println("Opció invàlida. Elegeix un numero del 1 al 3.");
            }
        }
    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nTipus d'habitació disponibles:");
        //per a aconseguir el preu fem un get al HashMap tipusHab i cridem a la primera posició del array (la 0)
        
        mostrarInfoTipus(TIPUS_ESTANDARD);
        mostrarInfoTipus(TIPUS_SUITE);
        mostrarInfoTipus(TIPUS_DELUXE);

        String tipus = seleccionarTipusHabitacio();

        if (disponibilitatHabitacions.get(tipus) > 0) {
            return tipus;
        } else {
            System.out.println("No hi ha habitacions disponibles d'aquest tipus.");
            return null;
        }
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        ArrayList<String> reservesInfo = new ArrayList<>();
        sc.nextLine(); //per a netejar el buffer

        System.out.println(""); //per a separar les parts de tots els mètodes per a fer una reserva
        System.out.println("Serveis addicionals (0-4):");
        System.out.println("0. Finalitzar");
        System.out.println("1. Esmorzar - 10€");
        System.out.println("2. Gimnàs - 15€");
        System.out.println("3. Spa - 20€");
        System.out.println("4. Piscina - 25€");

        System.out.print("Vol afegir un servei? (s/n): ");
        String lletra = sc.nextLine();
        
        while (lletra.equalsIgnoreCase("s")) {
        if (reservesInfo.size() == 4) {
            System.out.println("Ja has seleccionat tots els serveis");
            break;
        }
        
        int numServei = llegirEnter("Seleccione servei (0 per finalitzar): ");

        if (numServei == 0) {
            break;
        }

        String nomServei = "";
        switch (numServei) {
            case 1: 
                nomServei = SERVEI_ESMORZAR; 
                break;
            case 2: 
                nomServei = SERVEI_GIMNAS; 
                break;
            case 3: 
                nomServei = SERVEI_SPA; 
                break;
            case 4: 
                nomServei = SERVEI_PISCINA; 
                break;
            default:
                System.out.println("Opció invàlida");
                continue;
        }

        if (!reservesInfo.contains(nomServei)) {
            reservesInfo.add(nomServei);
            System.out.println("Servei afegit: " + nomServei);
        } else {
            System.out.println("Aquest servei ja està afegit");
        }
        
        sc.nextLine(); // limpiar buffer después de llegirEnter
        System.out.print("Vol afegir un altre servei? (s/n): ");
        lletra = sc.nextLine();
    }

        return reservesInfo;
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {
        System.out.println("");
        System.out.println("Calculem el total...");

        float subtotal = preusHabitacions.get(tipusHabitacio);
        
        System.out.println("Preu habitació: " + subtotal + "€");

        //si el arrayList que li hem passat com a paràmetre no està buit s'imprimeix cada element amb el seu preu
        if (serveisSeleccionats != null && !serveisSeleccionats.isEmpty()){
            for (String servei : serveisSeleccionats) {
            float costServei = preusServeis.get(servei);
            System.out.println("Serveis: " + servei + "(" + costServei + "€)");
            subtotal += costServei; //es va acumulant el preu al preu base
        }

        }

        System.out.println("Subtotal: " + subtotal);

        float iva = subtotal * IVA;
        System.out.println("IVA (21%):" + iva); //es calcula l'IVA del preu que tenim de moment

        float diners = subtotal + iva;
        System.out.println("TOTAL: " + diners);
        System.out.println("Reserva creada amb èxit!");
        return diners;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        int codi;
        Random rand = new Random();
        while (true){ //si el codi està repetit es torna a repetir el bucle
            codi = rand.nextInt(900) + 100; //per a crear un número entre 100 i 999
            if (!reserves.containsKey(codi)){ 
                System.out.println("");
                System.out.println("Codi de reserva: " + codi);
                break;
            }
        }
        return codi;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
         
        while(true){
            int codi = llegirEnter("Introdueix el codi de reserva: ");
            
            if (reserves.containsKey(codi)) {
                System.out.println("Reserva trobada!");
                String tipusHab = reserves.get(codi).get(0); //guardem en una variable el tipus d'habitació per a actualizar la disponibilitat

                //borrem la reserva
                reserves.remove(codi);

                //creem una nova variable i li sumem uno a la capacitat actual del tipus d'habitació
                int novaCapacitat = disponibilitatHabitacions.get(tipusHab) + 1;
                disponibilitatHabitacions.put(tipusHab, novaCapacitat);
                break; //es trenca el bucle 
            } else {
                System.out.println("No hi ha ninguna reserva en ixe codi");
            }

        }

        System.out.println("Habitació alliberada correctament.");
        System.out.println("Disponibilitat actualitzada.");
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        System.out.println("");
        System.out.println("===== DISPONIBILITAT D'HABITACIONS =====");
        mostrarDisponibilitatTipus(TIPUS_ESTANDARD);
        mostrarDisponibilitatTipus(TIPUS_SUITE);
        mostrarDisponibilitatTipus(TIPUS_DELUXE);
    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
         //cas base: si el array de codis es null o està buit
        if(codis == null || codis.length == 0) {
            System.out.println("(No hi ha més reserves d’aquest tipus.)");
            return;
        }

        int codi = codis[0]; //s'agafa el primer codi del array i si el tipus asociat a ixe codi es el que s'ha pasat per parametre es crida a mostrarDades
        if (reserves.get(codi).get(0).equals(tipus)){
            mostrarDadesReserva(codi);
            System.out.println("");
        }

        //es crea un nou array amb un element menys, ja que es borra el que ja hem comprobat
        int newCodis[] = new int[codis.length - 1];
        System.arraycopy(codis, 1, newCodis, 0, newCodis.length); 

        //cas recursiu, es crida la mateixa funció amb un cas més fàciil o xicotet (un array més petit)
        llistarReservesPerTipus(newCodis, tipus);
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        
        while (true) { 
            int codi = llegirEnter("Introdueix el codi de reserva: ");
            
            if (reserves.containsKey(codi)){
                mostrarDadesReserva(codi);
                break;
            } else {
                System.out.println("No hi ha ninguna reserva en ixe codi");
            }
        }
 
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        
        System.out.println("1. " + TIPUS_ESTANDARD); 
        System.out.println("2. " + TIPUS_SUITE);
        System.out.println("3. " + TIPUS_DELUXE);

        String tipus = seleccionarTipusHabitacio(); //cridem a la funció per a traure el String del tipus 
        System.out.println("");

        int num = reserves.keySet().size(); //torna el número total de ttots els codis de la reserva
        int codis[] = new int[num]; //es crea un array amb el número que hem tret

        int i = 0;
        for (int codi : reserves.keySet()){
            codis[i] = codi; //cada codi de la llista es guarda en el array 
            i++;
        }

        llistarReservesPerTipus(codis, tipus);
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
       System.out.println("Dades de la reserva:");
            System.out.println("- Tipus d'habitació:" + reserves.get(codi).get(0));
            System.out.println("- Cost total: " + reserves.get(codi).get(1));
            //amb subList creem una llista de nomes els servicis que se sap que comencen en el index 2 del arraylist del hashmap de reserves i acabe en 
            //l'ultima posició (calculada amb size())
            List<String> serveis = reserves.get(codi).subList(2, reserves.get(codi).size());
            //si la llista no està buida s'imprimixen els serveis
            if (!serveis.isEmpty()) {
                System.out.println("- Serveis addicionals: ");
                for (int i = 0; i < serveis.size(); i++) { 
                    System.out.println("       -" + serveis.get(i));
                }
            } else {
                System.out.println("Sense serveis addicionals");
            }
    }




    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
                System.out.print(missatge);
                valor = sc.nextInt();
                correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}
