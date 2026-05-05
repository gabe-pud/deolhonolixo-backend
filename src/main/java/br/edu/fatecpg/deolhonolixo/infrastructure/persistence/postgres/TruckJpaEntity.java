package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "trucks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TruckJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String licensePlate;
    private String status;

    private LocalDateTime routeStart;
    private LocalDateTime routeEnd;
    private String routeId;

}
