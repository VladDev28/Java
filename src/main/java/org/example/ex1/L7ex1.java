package org.example.ex1;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

record Carte(String titlul,String autorul,int anul){}

public class L7ex1 {
    public static void scriereCarti(Map<Integer,Carte>colectie) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            File file=new File("src/main/resources/carti.json");
            mapper.writeValue(file,colectie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<Integer,Carte> citireCarti() {
        try {
            File file=new File("src/main/resources/carti.json");
            ObjectMapper mapper=new ObjectMapper();
            Map<Integer,Carte> carti = mapper
                    .readValue(file, new TypeReference<Map<Integer,Carte>>(){});
            return carti;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void afisareCarti(Map<Integer,Carte> colectie){
        System.out.println("Colectie de carti: ");
        colectie.forEach((id,carte)-> System.out.println("Id: " + id + "\nTitlu: " + carte.titlul() + "\nAutorul: " + carte.autorul()+ "\nAnul apartitiei: "+ carte.anul()));
    }
    public static void adaugareCarte(Map<Integer,Carte>colectie){
        Carte carteNoua = new Carte("Si ma intunec","Kiersten White",2016);
        colectie.putIfAbsent(7,carteNoua);
    }

    public static void stergereCarte(Map<Integer,Carte>colectie,int id_carte){
        colectie.remove(id_carte);
    }

    public static void filtrareAutor(Map<Integer,Carte>colectie){
        Set<Carte> carteS = colectie.values().stream()
                .filter(carte -> carte.autorul().equals("Yuval Noah Harari"))
                .collect(Collectors.toSet());
        System.out.println("Cartile lui Yuval Noah Harari: ");
        carteS.forEach(System.out::println);
    }

    public static void afisareSort(Map<Integer,Carte>colectie){
        System.out.println("Cartile aranjate dupa titlu: ");
        Set<Carte>carteSort = new HashSet<>(colectie.values());
        carteSort.stream()
                .sorted(Comparator.comparing(Carte::titlul))
                .forEach(System.out::println);

    }
    public static void afisareVechime(Map<Integer,Carte>colectie){
        Optional<Carte>oldestbook = colectie.values().stream()
                .min(Comparator.comparingInt(Carte::anul));
        oldestbook.ifPresent(carte -> System.out.println("\nCea mai veche carte este: " + "\nTitlu: " + carte.titlul() + "\nAutorul: " + carte.autorul()+ "\nAnul apartitiei: "+ carte.anul()));


    }
    public static void main(String[] args) {
        Map<Integer,Carte> colectie = citireCarti();

        //ex1:afisare
        afisareCarti(colectie);
        //ex2:remove
        stergereCarte(colectie,4);
        //ex3:adaugare
        adaugareCarte(colectie);
        //ex4:salvare in fisier
        scriereCarti(colectie);
        System.out.println("\n");
        afisareCarti(colectie);
        //ex5:Filtrare dupa autor
        filtrareAutor(colectie);
        //ex6:Afisarea ordonata
        System.out.println("\n");
        afisareSort(colectie);
        //ex7:Vechime
        afisareVechime(colectie);
    }
}
