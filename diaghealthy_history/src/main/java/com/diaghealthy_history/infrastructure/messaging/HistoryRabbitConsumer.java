package com.diaghealthy_history.infrastructure.messaging;

import com.diaghealthy_history.application.inputs.RegisterHistoryInput;
import com.diaghealthy_history.application.usecases.RegisterHistoryUseCase;
import com.diaghealthy_history.infrastructure.dtos.history.HistoryRegisterDTO;
import com.diaghealthy_history.infrastructure.mappers.HistoryMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HistoryRabbitConsumer {

    private final RegisterHistoryUseCase registerHistoryUseCase;
    private final HistoryMapper historyMapper;

    public HistoryRabbitConsumer(
            RegisterHistoryUseCase registerHistoryUseCase,
            HistoryMapper historyMapper
    ) {
        this.registerHistoryUseCase = registerHistoryUseCase;
        this.historyMapper = historyMapper;
    }

    @RabbitListener(queues = "history.queue")
    public void consume(HistoryRegisterDTO dto) {

        RegisterHistoryInput input =
                historyMapper.toRegisterInput(dto);

        registerHistoryUseCase.execute(input);
    }
}