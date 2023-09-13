package com.pacoprojects.recovery;

import lombok.Builder;

@Builder
public record RecoveryRequestValidateToken(
        String basicToken
) {
}
