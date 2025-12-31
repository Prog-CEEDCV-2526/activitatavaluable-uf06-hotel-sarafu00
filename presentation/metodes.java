package presentation;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class metodes {

    static void inicialitzarPreus() {

        Main.tipusHab.put("Estàndard", new int[]{50, Main.capEs});
        Main.tipusHab.put("Suite", new int[]{100, Main.capSu});
        Main.tipusHab.put("Deluxe", new int[]{150, Main.capDe});

        Main.serveis.put("Esmorzar", 10);
        Main.serveis.put("Gimnàs", 15);
        Main.serveis.put("Spa", 20);
        Main.serveis.put("Piscina", 25);

        Main.disponibilitat.put("Estàndard", Main.capEs);
        Main.disponibilitat.put("Suite", Main.capSu);
        Main.disponibilitat.put("Deluxe", Main.capDe);
    } 

    static void reservarHabitacio() {
        System.out.println("===== RESERVAR HABITACIÓ =====");
        
        String hab = seleccionarTipusHabitacioDisponible();
        ArrayList<String> serveis = seleccionarServeis();
        float preu = calcularPreuTotal(hab, serveis);
        int codi = generarCodiReserva();

        ArrayList<String> info = new ArrayList<>(); //es crea un altre arrayList per a juntar els serveis amb el tipus d'habitació i el preu
        //afegim cada dada seguint l'ordre establert
        info.add(hab);
        info.add(String.valueOf(preu));
        //utilitzem addAll per a afegir els elements de l'altre arrayList
        info.addAll(serveis);

        Main.reserves.put(codi, info);

        //guardem en una variable la capacitat actual de l'habitació per a restarli uno per a actualizarla una volta fet la reserva
        int capacitat = Main.disponibilitat.get(hab);
        Main.disponibilitat.put(hab, capacitat - 1);
    }

    static String seleccionarTipusHabitacioDisponible() { 
        System.out.println("Tipus d'habitació disponibles:");
        //per a aconseguir el preu fem un get al HashMap tipusHab i cridem a la primera posició del array (la 0)
        System.out.println("1. Estàndard -" + Main.disponibilitat.get("Estàndard") + " disponibles - " + Main.tipusHab.get("Estàndard")[0] + "€"); 
        System.out.println("2. Suite     -" + Main.disponibilitat.get("Suite") + " disponibles - " + Main.tipusHab.get("Suite")[0] + "€");
        System.out.println("3. Deluxe    -" + Main.disponibilitat.get("Deluxe") + " disponibles - " + Main.tipusHab.get("Deluxe")[0] + "€");
        String tipus = seleccionarTipusHabitacio();

        if (Main.disponibilitat.get(tipus) > 0) {
            return tipus;
        } else {
            return null;
        }

    }

    static String seleccionarTipusHabitacio() { 
        while (true) {
            System.out.print("Seleccione tipus d'habitació: ");
            int hab = Main.sc.nextInt();
            switch (hab) {
                case 1:
                    return "Estàndard"; //ho retornem amb un return i així s'ix del switch
                case 2:
                    return "Suite";
                case 3:
                    return "Deluxe";
                default:
                    System.out.println("Opció invàlida. Elegeix un numero del 1 al 3.");
            }
        }
    }

    static ArrayList<String> seleccionarServeis() {
        ArrayList<String> reservesInfo = new ArrayList<>();
        Main.sc.nextLine(); //per a netejar el buffer

        System.out.println(""); //per a separar les parts de tots els mètodes per a fer una reserva
        System.out.println("Serveis addicionals (0-4):");
        System.out.println("0. Finalitzar");
        System.out.println("1. Esmorzar - 10€");
        System.out.println("2. Gimnàs - 15€");
        System.out.println("3. Spa - 20€");
        System.out.println("4. Piscina - 25€");

        System.out.print("Vol afegir un servei? (s/n): ");
        String lletra = Main.sc.nextLine();
        do {
            if (lletra.equals("s")) { //si s'escriu "s" s'imprimeix "seleccione un servei" fins que l'usuari vuiga parar 
                System.out.print("Seleccione servei: ");
                int numServei = Main.sc.nextInt();
                Main.sc.nextLine(); // per a netejar el buffer

                if (numServei == 0) { //per a ixir del bucle si l'usuari apreta 0 
                    break;
                }

                String nomServei = "";
                switch (numServei) {
                    case 1:
                        nomServei = "Esmorzar";
                        break;
                    case 2:
                        nomServei = "Gimnàs";
                        break;
                    case 3:
                        nomServei = "Spa";
                        break;
                    case 4:
                        nomServei = "Piscina";
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opció invàlida");
                        continue;
                }

                if (numServei >= 1 && numServei <= 4 && !reservesInfo.contains(nomServei)) { //si el número està dins del rang correcte i 
                // no està repetit s'afegeix
                    reservesInfo.add(nomServei); 
                    System.out.println("Servei afegit: " + nomServei);
                }

                if (reservesInfo.size() == 4){
                    System.out.println("Ja has seleccionat tots els serveis");
                    break; // per a ixir del bucle 
                }
            } else if (lletra.equals("n")){
                break;
            }else {
                System.out.println("Opció invàlida");
            }
        } while (!lletra.equals("n"));

        return reservesInfo;
    }

    static float calcularPreuTotal(String tipus, ArrayList<String> serveis) {
        System.out.println("");
        System.out.println("Calculem el total...");

        float subtotal = 0;
        switch (tipus.toLowerCase()) {
            case "estàndard": 
                subtotal = 50;
                break;
            case "suite":
                subtotal = 100;
                break;
            case "deluxe":
                subtotal = 150;
                break;
            default:
                System.out.println("Opció invàlida");
        }

        System.out.println("Preu habitació: " + subtotal);

        //si el arrayList que li hem passat com a paràmetre no està buit s'imprimeix cada element amb el seu preu
        if (serveis != null){
            for (String servei : serveis) {
            int costServei = Main.serveis.get(servei);
            System.out.println("Serveis: " + servei + "(" + costServei + "€)");
            subtotal += costServei; //es va acumulant el preu al preu base
        }

        }

        System.out.println("Subtotal: " + subtotal);

        float IVA = subtotal * Main.iva;
        System.out.println("IVA (21%):" + IVA); //es calcula l'IVA del preu que tenim de moment

        float diners = subtotal + IVA;
        System.out.println("TOTAL: " + diners);
        System.out.println("Reserva creada amb èxit!");
        return diners;
    }

    static int generarCodiReserva() {
        int codi;
        Random rand = new Random();
        while (true){ //si el codi està repetit es torna a repetir el bucle
            codi = rand.nextInt(900) + 100; //per a crear un número entre 100 i 999
            if (!Main.reserves.containsKey(codi)){ 
                System.out.println("");
                System.out.println("Codi de reserva: " + codi);
                break;
            }
        }
        return codi;
    }

    static void alliberarHabitacio() {
        System.out.println("===== ALLIBERAR HABITACIÓ =====");

        boolean found = false;
        do {
            System.out.print("Introdueix el codi de reserva: ");
            int codi = Main.sc.nextInt();
            if (Main.reserves.containsKey(codi)) {
                System.out.println("Reserva trobada!");
                String tipusHab = Main.reserves.get(codi).get(0); //guardem en una variable el tipus d'habitació per a actualizar la disponibilitat

                //borrem la reserva
                Main.reserves.remove(codi);

                //creem una nova variable i li sumem uno a la capacitat actual del tipus d'habitació
                int novaCapacitat = Main.disponibilitat.get(tipusHab) + 1;
                Main.disponibilitat.put(tipusHab, novaCapacitat);
                found = true; //es trenca el bucle 
            } else {
                System.out.println("No hi ha ninguna reserva en ixe codi");
            }

        } while (!found);

        System.out.println("Habitació alliberada correctament.");
        System.out.println("Disponibilitat actualitzada.");

    }

    static void consultarDisponibilitat() {
        int lliuresEs = Main.disponibilitat.get("Estàndard");
        int lliuresSu = Main.disponibilitat.get("Suite");
        int lliuresDe = Main.disponibilitat.get("Deluxe");

        int ocupadesEs = Main.capEs - lliuresEs; 
        int ocupadesSu = Main.capSu - lliuresSu;
        int ocupadesDe = Main.capDe - lliuresDe;

        System.out.println("");
        System.out.println("===== DISPONIBILITAT D'HABITACIONS =====");
        System.out.println("Estàndard: " + lliuresEs + " lliures " + ocupadesEs + " ocupades");
        System.out.println("Suite: " + lliuresSu + " lliures " + ocupadesSu + " ocupades");
        System.out.println("Deluxe: " + lliuresDe + " lliures " + ocupadesDe + " ocupades");
    }

    static void mostrarDadesReserva(int codi) {
            System.out.println("Dades de la reserva:");
            System.out.println("- Tipus d'habitació:" + Main.reserves.get(codi).get(0));
            System.out.println("- Cost total: " + Main.reserves.get(codi).get(1));
            //amb subList creem una llista de nomes els servicis que se sap que comencen en el index 2 del arraylist del hashmap de reserves i acabe en 
            //l'ultima posició (calculada amb size())
            List<String> serveis = Main.reserves.get(codi).subList(2, Main.reserves.get(codi).size());
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

    static void obtindreReserva() {
        System.out.println("===== CONSULTAR RESERVA =====");

        while (true) { 
            System.out.print("Introdueix el codi de reserva: ");
            int codi = Main.sc.nextInt();
            if (Main.reserves.containsKey(codi)){
                mostrarDadesReserva(codi);
                break;
            } else {
            System.out.println("No hi ha ninguna reserva en ixe codi");
            }
        }
    }

    static void llistarReservesPerTipus(int[] codis, String tipus) {
        //cas base: si el array de codis es null o està buit
        if(codis == null || codis.length == 0) {
            System.out.println("(No hi ha més reserves d’aquest tipus.)");
            return;
        }

        int codi = codis[0]; //s'agafa el primer codi del array i si el tipus asociat a ixe codi es el que s'ha pasat per parametre es crida a mostrarDades
        if (Main.reserves.get(codi).get(0).equals(tipus)){
            mostrarDadesReserva(codi);
            System.out.println("");
        }

        //es crea un nou array amb un element menys, ja que es borra el que ja hem comprobat
        int newCodis[] = new int[codis.length - 1];
        System.arraycopy(codis, 1, newCodis, 0, newCodis.length); 

        //cas recursiu, es crida la mateixa funció amb un cas més fàciil o xicotet (un array més petit)
        llistarReservesPerTipus(newCodis, tipus);
    }

    static void obtindreReservaPerTipus() {
        System.out.println("===== CONSULTAR RESERVES PER TIPUS =====");

        System.out.println("1. Estàndard"); 
        System.out.println("2. Suite");
        System.out.println("3. Deluxe");
        String tipus = seleccionarTipusHabitacio(); //cridem a la funció per a traure el String del tipus 
        System.out.println("");

        int num = Main.reserves.keySet().size(); //torna el número total de ttots els codis de la reserva
        int codis[] = new int[num]; //es crea un array amb el número que hem tret

        int i = 0;
        for (int codi : Main.reserves.keySet()){
            codis[i] = codi; //cada codi de la llista es guarda en el array 
            i++;
        }

        llistarReservesPerTipus(codis, tipus);
        
    }

}
