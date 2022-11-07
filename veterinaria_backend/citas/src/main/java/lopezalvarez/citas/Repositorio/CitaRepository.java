package lopezalvarez.citas.Repositorio;

import org.springframework.data.repository.CrudRepository;
import lopezalvarez.citas.Modelo.Cita;

import java.util.List;

public interface CitaRepository extends CrudRepository<Cita, Integer> {
    List<Cita> findAll();
    Cita findByIdCita(int idCita);
    List<Cita>findAllByFecha(String fecha);
    Cita save(Cita cita);
    void delete(Cita cita);
}