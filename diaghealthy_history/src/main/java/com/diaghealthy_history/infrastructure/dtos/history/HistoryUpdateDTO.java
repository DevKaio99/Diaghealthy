package com.diaghealthy_history.infrastructure.dtos.history;

import com.diaghealthy_history.domain.enuns.HistoryStatus;

public record HistoryUpdateDTO(
        HistoryStatus status,
        String notes
) {
}
