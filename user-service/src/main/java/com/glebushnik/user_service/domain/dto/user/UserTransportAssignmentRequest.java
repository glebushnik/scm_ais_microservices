package com.glebushnik.user_service.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Модель для связывания пользователя с транспортом")
public class UserTransportAssignmentRequest {

    @Schema(description = "ID пользователя", example = "c1d0b730-d321-11ec-9d64-0242ac120002")
    private UUID userId;

    @Schema(description = "Список ID транспортов", example = "[\"a2c0b230-d321-11ec-9d64-0242ac120002\", \"b3d0c340-d321-11ec-9d64-0242ac120002\"]")
    private List<UUID> transportIds;
}

