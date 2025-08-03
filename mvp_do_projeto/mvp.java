//pacote que permite o uso de delay
package com.journaldev.threads;
import java.util.Scanner;

// import java.io.File;  // Import the File class
// import java.io.IOException;  // Import the IOException class to handle errors

public class mvp {

    public static void main(String[] args) throws InterruptedException{
        
        Scanner sc = new Scanner(System.in);
        int numeroAula;
        int quantidadeSemana;

        System.out.println("===== Checagem de Carga Horaria =====");
        System.out.print("Informe o numero de aulas semanais: ");
        numeroAula = sc.nextInt();
        System.out.print("\nInforme o numero de semanas letivas: ");
        quantidadeSemana = sc.nextInt();

        double cargaHoraria = calculaCargaHoraria(numeroAula, quantidadeSemana);

        System.out.print("\nAguarde um momento");
        for(int i = 0; i<6; i++){
            System.out.print(".");
            Thread.sleep(400);
        }
        Thread.sleep(1500);

        System.out.println("\n");
        //quatro casos e o padrao em DB, preferi utilizar aq tbm
        String quatroCasas = String.format("%.4f", cargaHoraria);
        System.out.println("Carga Horaria: " + quatroCasas + " horas.");
    }

    public static double calculaCargaHoraria(int numeroAula, int quantidadeSemana){
        return numeroAula * 0.8333 * quantidadeSemana;
        }

}
