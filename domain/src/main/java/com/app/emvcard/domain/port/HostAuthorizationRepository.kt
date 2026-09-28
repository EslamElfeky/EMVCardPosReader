package com.app.emvcard.domain.port

import com.app.emvcard.domain.model.OnlineAuthRequest
import com.app.emvcard.domain.model.OnlineAuthResponse
import com.app.emvcard.domain.model.ReversalRequest

interface HostAuthorizationRepository {
    suspend fun authorize(request: OnlineAuthRequest): OnlineAuthResponse
    suspend fun reverse(request: ReversalRequest)
}