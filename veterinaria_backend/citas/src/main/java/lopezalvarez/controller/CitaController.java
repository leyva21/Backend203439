package lopezalvarez.controller;

import lopezalvarez.model.CitaMascota;
import lopezalvarez.model.Mascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lopezalvarez.model.Cita;
import lopezalvarez.repository.CitaRepository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CitaController {

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/MascotaConCita/{idCita}")
    public CitaMascota getMascotaConCita(@PathVariable("idCita") int idCita){
        Cita cita = citaRepository.findByIdCita(idCita);
        CitaMascota citaMascota = null;
        if(cita != null){
            citaMascota = new CitaMascota(cita.getIdCita(),cita.getFecha(), cita.getHora(), cita.getTipoServicio());
            Mascota mascota = restTemplate.getForObject("http://localhost:9998/cita/"+citaMascota.getIdCita(),Mascota.class);
            citaMascota.setCita(cita);
        }
        return citaMascota;
    }

    @GetMapping(value = "/listCita")
    public List<Cita> getListcita(){
        return citaRepository.findAll();
    }

    @GetMapping(value="/cita")
    public Cita getCita(){
        return citaRepository.findByIdCita(1);
    }

    @PostMapping(value = "/cita/fecha")
    public List<Cita> getCitaByCountry(@RequestBody Cita cita){
        return citaRepository.findAllByFecha(cita.getFecha());
    }

    @PostMapping(value = "/cita/add")
    public Cita addCita(@RequestBody Cita cita){
        return citaRepository.save(cita);
    }

    @PostMapping(value = "/cita/update")
    public Cita updateCita(@RequestBody Cita cita){
        Cita d = citaRepository.findByIdCita(cita.getIdCita());
        if(d != null){
            if (d.getFecha() != null)
                d.setFecha(cita.getFecha());
            return citaRepository.save(d);
        }
        return null;
    }

    @PostMapping(value = "/cita/delete")
    public Boolean deleteCita(@RequestBody Cita cita){
        Cita d = citaRepository.findByIdCita(cita.getIdCita());
        if(d != null){
            citaRepository.delete(d);
            return true;
        }
        return null;
    }

}
