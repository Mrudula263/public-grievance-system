package com.example.demo.config;

import com.example.demo.entity.District;
import com.example.demo.entity.Taluk;
import com.example.demo.repository.DistrictRepository;
import com.example.demo.repository.TalukRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MasterDataSeeder implements CommandLineRunner {

    private final DistrictRepository districtRepo;
    private final TalukRepository talukRepo;

    public MasterDataSeeder(DistrictRepository districtRepo, TalukRepository talukRepo) {
        this.districtRepo = districtRepo;
        this.talukRepo = talukRepo;
    }

    @Override
    public void run(String... args) {
        if (districtRepo.count() > 0 || talukRepo.count() > 0) {
            return; 
        }

        District br = districtRepo.save(new District("Bengaluru Rural"));
        District bu = districtRepo.save(new District("Bengaluru Urban"));
        District mandya = districtRepo.save(new District("Mandya"));
        District mysuru = districtRepo.save(new District("Mysuru"));

        // Create Taluks
        talukRepo.saveAll(List.of(
            // Bengaluru Rural
            new Taluk("Devanahalli", br),
            new Taluk("Doddaballapura", br),
            new Taluk("Hosakote", br),
            new Taluk("Nelamangala", br),

            // Bengaluru Urban
            new Taluk("Anekal", bu),
            new Taluk("Bengaluru", bu),
            new Taluk("Kengeri", bu),
            new Taluk("Krishnarajapura", bu),
            new Taluk("Yelahanka", bu),

            // Mandya
            new Taluk("Krishnarajapete", mandya),
            new Taluk("Madduru", mandya),
            new Taluk("Malavalli", mandya),
            new Taluk("Mandya", mandya),
            new Taluk("Nagamangala", mandya),
            new Taluk("Pandavapura", mandya),
            new Taluk("Shrirangapattana", mandya),

            // Mysuru
            new Taluk("Heggadadevanakote", mysuru),
            new Taluk("Hunasuru", mysuru),
            new Taluk("Krishnarajanagara", mysuru),
            new Taluk("Mysuru", mysuru),
            new Taluk("Nanjanagudu", mysuru),
            new Taluk("Piriyapattana", mysuru),
            new Taluk("Saraguru", mysuru),
            new Taluk("T. Narasipura", mysuru),
            new Taluk("Saligrama", mysuru)
        ));

        System.out.println(" District and Taluk master data seeded.");
    }
}
