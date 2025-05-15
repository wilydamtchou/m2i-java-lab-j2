package com.m2i.dto;

import java.time.LocalDateTime;
import com.m2i.transaction.TypeTransaction;

public record TransactionSummary(
    String id,
    LocalDateTime date,
    double montant,
    TypeTransaction type
) {}
