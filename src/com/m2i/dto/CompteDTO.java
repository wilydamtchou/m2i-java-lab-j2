package com.m2i.dto;

import java.time.LocalDate;

public record CompteDTO(
    String numeroCompte,
    double solde,
    LocalDate dateCreation,
    String proprietaireNom
) {
}
