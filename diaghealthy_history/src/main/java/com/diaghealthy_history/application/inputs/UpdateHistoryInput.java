package com.diaghealthy_history.application.inputs;

import com.diaghealthy_history.domain.enuns.HistoryStatus;

public record UpdateHistoryInput(
        HistoryStatus status,
        String notes
) {
}
