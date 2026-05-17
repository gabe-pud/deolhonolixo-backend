package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.TruckJpaEntity;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.TruckJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TruckPersistenceAdapterTest {

    @Mock
    TruckJpaRepository repository;

    TruckMapper mapper = Mappers.getMapper(TruckMapper.class);

    @InjectMocks
    TruckPersistenceAdapter adapter;

    @Test
    void save_returnsLicensePlateMap() {
        adapter = new TruckPersistenceAdapter(repository, mapper);

        Truck t = new Truck(null, "ABC-1234", null, null, null, null);

        TruckJpaEntity entity = new TruckJpaEntity();
        entity.setId(10L);
        entity.setLicensePlate("ABC-1234");

        when(repository.save(any(TruckJpaEntity.class))).thenReturn(entity);

        HashMap<String,String> res = adapter.save(t);

        assertEquals("ABC-1234", res.get("licensePlate"));
        verify(repository).save(any(TruckJpaEntity.class));
    }

    @Test
    void findById_mapsEntityToDomain() {
        adapter = new TruckPersistenceAdapter(repository, mapper);

        TruckJpaEntity entity = new TruckJpaEntity();
        entity.setId(5L);
        entity.setLicensePlate("XYZ-9999");

        when(repository.findById(5L)).thenReturn(Optional.of(entity));

        Truck result = adapter.findById(5L);

        assertEquals("XYZ-9999", result.licensePlate());
    }
}
