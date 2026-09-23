package com.app.emvcard.domain.model
import com.app.emvcard.domain.model.EmvError
sealed interface EmvTransactionEvent {
    data class StepChanges(val step: EmvStep): EmvTransactionEvent
    data class Approved(val card: EmvCard,val authCode: String,val arc: String): EmvTransactionEvent
    data class Declined(val reason: String,val arc: String): EmvTransactionEvent
    data class Failed(val cause:EmvError) : EmvTransactionEvent

}