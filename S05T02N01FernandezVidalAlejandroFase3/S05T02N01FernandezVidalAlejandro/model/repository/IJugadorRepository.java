package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain.JugadorEntity;

public interface IJugadorRepository extends MongoRepository<JugadorEntity, ObjectId> {

	public boolean existsByNombre(String nombre);
	public Optional<JugadorEntity> findOneByNombre(String nombre);
}
