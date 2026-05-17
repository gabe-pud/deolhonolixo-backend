package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TruckJpaRepositoryTest {

    @Mock
    TruckJpaRepository repository;

    @Test
    void saveAndFindByLicensePlate_contract() {
        var e = new TruckJpaEntity();
        e.setId(22L);
        e.setLicensePlate("ZZZ-0000");

        when(repository.save(any(TruckJpaEntity.class))).thenReturn(e);
        when(repository.findBylicensePlate("ZZZ-0000")).thenReturn(Optional.of(e));

        var saved = repository.save(new TruckJpaEntity());
        assertEquals(22L, saved.getId());

        var found = repository.findBylicensePlate("ZZZ-0000");
        assertTrue(found.isPresent());
        assertEquals("ZZZ-0000", found.get().getLicensePlate());
    }
}
