package com.app.emvcard.domain.port

import com.app.emvcard.domain.model.BeepOutcome
import com.app.emvcard.domain.model.CardPresentation
import com.app.emvcard.domain.model.CvmOption
import com.app.emvcard.domain.model.CvmResult
import com.app.emvcard.domain.model.ReceiptDocument
import com.app.emvcard.domain.model.TerminalStatus

interface HardwareGateway {
    suspend fun pollForCard(timeoutMs: Long): CardPresentation
    suspend fun powerOnIcc()
    suspend fun transceiveApdu(command: ByteArray): ByteArray
    suspend fun requestCvm(cvmList: List<CvmOption>): CvmResult
    suspend fun powerOffIcc()
    suspend fun printReceipt(receipt: ReceiptDocument): Result<Unit>
    fun beep(outcome: BeepOutcome)
    fun terminalStatus(): TerminalStatus
}