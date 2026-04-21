package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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

    private Date routeStart;
    private Date routeEnd;
    private String routeId;

}
